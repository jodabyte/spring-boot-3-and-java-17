package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jodabyte.springboot3andjava17.core.asset.Asset;
import de.jodabyte.springboot3andjava17.core.asset.MqttNetworkConfiguration;
import de.jodabyte.springboot3andjava17.core.asset.NetworkConfigurationType;
import de.jodabyte.springboot3andjava17.core.kafka.KafkaContract;
import de.jodabyte.springboot3andjava17.core.mqtt.Zigbee2MqttDevices;
import de.jodabyte.springboot3andjava17.mqtt.AbstractHandler;
import de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.model.BridgeDevice;
import de.jodabyte.springboot3andjava17.openapi.asset.api.AssetsApi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Zigbee2MqttHandler extends AbstractHandler {

  public static final String TOPIC_BRIDGE_UPDATE = "zigbee2mqtt/bridge/devices";
  public static final String DEVICE_UPDATE_TOPIC_FORMAT = "zigbee2mqtt/%s";

  private RetryTemplate retryTemplate;
  private AssetsApi assetServiceApi;
  private MqttPahoMessageDrivenChannelAdapter mqttInboundClient;
  private KafkaTemplate<String, Object> kafkaClient;

  private final ObjectMapper objectMapper = new ObjectMapper();
  private List<Asset> assetCache = new ArrayList<>();

  public Zigbee2MqttHandler() {
    super(Arrays.asList(TOPIC_BRIDGE_UPDATE));
  }

  @Override
  public void handle(String topic, Object payload) {
    if (topic.equals(TOPIC_BRIDGE_UPDATE)) {
      try {
        List<BridgeDevice> devices =
            objectMapper.readValue((String) payload, new TypeReference<>() {});
        handleDeviceUpdate(devices);
      } catch (JsonProcessingException e) {
        log.error("Failed to parse payload for topic={}.", topic, e);
      }
    } else if (hasAssetWithTopic(topic)) {
      Optional<Zigbee2MqttDevices> deviceContainer = Zigbee2MqttDevices.findDeviceByTopic(topic);
      if (deviceContainer.isEmpty()) {
        log.error("No mapper found for topic={}.", topic);
        return;
      }

      Optional<Asset> assetContainer = getAssetByTopic(topic);
      if (assetContainer.isEmpty()) {
        log.error("Could not find asset for topic={}.", topic);
        return;
      }

      Asset asset = assetContainer.get();
      try {
        Object data = objectMapper.readValue((String) payload, deviceContainer.get().getType());
        CompletableFuture<SendResult<String, Object>> future =
            kafkaClient.send(
                MessageBuilder.withPayload(asset.getName())
                    .setHeader(KafkaHeaders.KEY, asset.getName())
                    .setHeader(KafkaHeaders.TOPIC, KafkaContract.TOPIC_MQTT)
                    .build());
        future.whenComplete(
            (sendResult, throwable) -> {
              if (throwable != null) {
                log.error("Failed to send message to kafka: {}", throwable.getLocalizedMessage());
              }
            });
      } catch (JsonProcessingException e) {
        log.error("Failed to parse payload for topic={}.", topic, e);
      }
    }
  }

  @Override
  public boolean hasTopic(String topic) {
    boolean hasTopic = super.hasTopic(topic);
    return hasTopic || hasAssetWithTopic(topic);
  }

  private void handleDeviceUpdate(List<BridgeDevice> devices) {
    if (devices == null || devices.isEmpty()) {
      log.warn("Device list is empty.");
    }

    updateAssetCache();
    List<BridgeDevice> supportedDevices = getSupportedDevices(devices);
    disableAssetsOfRemovedDevices(supportedDevices);
    createOrEnableAssets(supportedDevices);
    updateMqttSubscription();
  }

  private void updateAssetCache() {
    this.assetCache = getAssets();
  }

  private List<Asset> getAssets() {
    List<Asset> all = this.retryTemplate.execute(retryContext -> assetServiceApi.all());
    return ListUtils.defaultIfNull(assetServiceApi.all(), new ArrayList<>()).stream()
        .filter(
            asset ->
                asset.getNetworkConfiguration().getType().equals(NetworkConfigurationType.MQTT))
        .collect(Collectors.toList());
  }

  private List<BridgeDevice> getSupportedDevices(List<BridgeDevice> devices) {
    return ListUtils.emptyIfNull(devices).stream()
        .filter(BridgeDevice::isSupported)
        .filter(
            device ->
                Zigbee2MqttDevices.findDeviceByTopic(
                        String.format(DEVICE_UPDATE_TOPIC_FORMAT, device.getFriendlyName()))
                    .isPresent())
        .toList();
  }

  private void disableAssetsOfRemovedDevices(List<BridgeDevice> devices) {
    this.assetCache.stream()
        .filter(
            asset ->
                devices.stream()
                    .noneMatch(device -> device.getFriendlyName().equals(asset.getName())))
        .forEach(this::disableAsset);
  }

  private void disableAsset(Asset asset) {
    ((MqttNetworkConfiguration) asset.getNetworkConfiguration()).setEnabled(false);
    Asset updatedAsset = assetServiceApi.update(asset);
    this.assetCache.set(this.assetCache.indexOf(asset), updatedAsset);
    log.info("disable asset={}", updatedAsset.getName());
  }

  private void createOrEnableAssets(List<BridgeDevice> devices) {
    devices.stream()
        .filter(
            device ->
                this.assetCache.stream()
                    .noneMatch(asset -> asset.getName().equals(device.getFriendlyName())))
        .forEach(this::createAsset);

    this.assetCache.stream()
        .filter(
            asset ->
                devices.stream()
                    .anyMatch(
                        device ->
                            device.getFriendlyName().equals(asset.getName())
                                && !((MqttNetworkConfiguration) asset.getNetworkConfiguration())
                                    .getEnabled()))
        .forEach(this::enableAsset);
  }

  private void createAsset(BridgeDevice device) {
    Asset assetDto =
        Asset.of(
            null,
            device.getFriendlyName(),
            MqttNetworkConfiguration.of(
                String.format(DEVICE_UPDATE_TOPIC_FORMAT, device.getFriendlyName()), true));
    Asset asset = assetServiceApi.create(assetDto);
    this.assetCache.add(asset);
    log.info("created asset from device={}", device.getFriendlyName());
  }

  private void enableAsset(Asset asset) {
    ((MqttNetworkConfiguration) asset.getNetworkConfiguration()).setEnabled(true);
    Asset updatedAsset = assetServiceApi.update(asset);
    this.assetCache.set(this.assetCache.indexOf(asset), updatedAsset);
    log.info("enabled asset={}", updatedAsset.getName());
  }

  private void updateMqttSubscription() {
    getOnlyDisabledAssets()
        .map(Asset::getNetworkConfiguration)
        .map(config -> ((MqttNetworkConfiguration) config).getTopic())
        .forEach(
            topic -> {
              if (Arrays.asList(this.mqttInboundClient.getTopic()).contains(topic)) {
                this.mqttInboundClient.removeTopic(topic);
                log.info("remove topic={}", topic);
              }
            });
    getOnlyEnabledAssets()
        .map(Asset::getNetworkConfiguration)
        .map(config -> ((MqttNetworkConfiguration) config).getTopic())
        .filter(
            topic ->
                Arrays.stream(this.mqttInboundClient.getTopic()).noneMatch(t -> t.equals(topic)))
        .forEach(
            topic -> {
              this.mqttInboundClient.addTopic(topic);
              log.info("add topic={}", topic);
            });
  }

  private boolean hasAssetWithTopic(String topic) {
    return getOnlyEnabledAssets()
        .anyMatch(
            config ->
                ((MqttNetworkConfiguration) config.getNetworkConfiguration())
                    .getTopic()
                    .equals(topic));
  }

  private Stream<Asset> getOnlyEnabledAssets() {
    return this.assetCache.stream()
        .filter(
            config -> ((MqttNetworkConfiguration) config.getNetworkConfiguration()).getEnabled());
  }

  private Stream<Asset> getOnlyDisabledAssets() {
    return this.assetCache.stream()
        .filter(
            config -> !((MqttNetworkConfiguration) config.getNetworkConfiguration()).getEnabled());
  }

  private Optional<Asset> getAssetByTopic(String topic) {
    return getOnlyEnabledAssets()
        .filter(
            asset ->
                ((MqttNetworkConfiguration) asset.getNetworkConfiguration())
                    .getTopic()
                    .equals(topic))
        .findFirst();
  }

  @Autowired
  public void setRetryTemplate(RetryTemplate retryTemplate) {
    this.retryTemplate = retryTemplate;
  }

  @Autowired
  public void setAssetServiceApi(AssetsApi assetServiceApi) {
    this.assetServiceApi = assetServiceApi;
  }

  @Autowired
  public void setMqttInboundClient(MqttPahoMessageDrivenChannelAdapter mqttInboundClient) {
    this.mqttInboundClient = mqttInboundClient;
  }

  @Autowired
  public void setKafkaClient(KafkaTemplate<String, Object> kafkaClient) {
    this.kafkaClient = kafkaClient;
  }
}
