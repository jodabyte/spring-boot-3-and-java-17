package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jodabyte.springboot3andjava17.core.asset.Asset;
import de.jodabyte.springboot3andjava17.core.asset.MqttNetworkConfiguration;
import de.jodabyte.springboot3andjava17.core.asset.NetworkConfigurationType;
import de.jodabyte.springboot3andjava17.mqtt.AbstractHandler;
import de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.mapper.Devices;
import de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.model.BridgeDevice;
import de.jodabyte.springboot3andjava17.openapi.asset.api.AssetsApi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
public class Zigbee2MqttHandler extends AbstractHandler {

  public static final String TOPIC_BRIDGE_UPDATE = "zigbee2mqtt/bridge/devices";
  public static final String DEVICE_UPDATE_TOPIC_FORMAT = "zigbee2mqtt/%s";

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final AssetsApi assetServiceApi;
  private final MqttPahoMessageDrivenChannelAdapter mqttInboundClient;
  private final KafkaTemplate<String, String> kafkaClient;
  private List<Asset> assetCache = new ArrayList<>();

  public Zigbee2MqttHandler(
      AssetsApi assetServiceApi,
      MqttPahoMessageDrivenChannelAdapter mqttInboundClient,
      KafkaTemplate<String, String> kafkaClient) {
    super(Arrays.asList(TOPIC_BRIDGE_UPDATE));
    this.assetServiceApi = assetServiceApi;
    this.mqttInboundClient = mqttInboundClient;
    this.kafkaClient = kafkaClient;
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
      Optional<Devices> optionalDevice = Devices.findDevice(topic);
      if (optionalDevice.isEmpty()) {
        log.error("No mapper found for topic={}.", topic);
      }

      try {
        Object data = objectMapper.readValue((String) payload, optionalDevice.get().getType());
        kafkaClient.send("mqtt", objectMapper.writeValueAsString(data));
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
    List<Asset> all = assetServiceApi.all();
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
                Devices.findDevice(
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
                                    .isEnabled()))
        .forEach(this::enableAsset);
  }

  private void createAsset(BridgeDevice device) {
    Asset assetDto =
        Asset.of(
            device.getFriendlyName(),
            new MqttNetworkConfiguration(
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
            config -> ((MqttNetworkConfiguration) config.getNetworkConfiguration()).isEnabled());
  }

  private Stream<Asset> getOnlyDisabledAssets() {
    return this.assetCache.stream()
        .filter(
            config -> !((MqttNetworkConfiguration) config.getNetworkConfiguration()).isEnabled());
  }
}
