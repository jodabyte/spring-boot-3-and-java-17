package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jodabyte.springboot3andjava17.core.asset.Asset;
import de.jodabyte.springboot3andjava17.core.asset.AssetServiceClient;
import de.jodabyte.springboot3andjava17.core.asset.MqttNetworkConfiguration;
import de.jodabyte.springboot3andjava17.core.asset.NetworkConfigurationType;
import de.jodabyte.springboot3andjava17.mqtt.AbstractHandler;
import de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.model.BridgeDevice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
public class Zigbee2MqttHandler extends AbstractHandler {

  public static final String TOPIC_DEVICE_UPDATE = "zigbee2mqtt/bridge/devices";
  private static final String TOPIC_FORMAT = "zigbee2mqtt/%s";

  private final ObjectMapper objectMapper = new ObjectMapper();
  private List<Asset> assetCache = new ArrayList<>();

  @Autowired private AssetServiceClient assetServiceClient;

  @Autowired private MqttPahoMessageDrivenChannelAdapter mqttInboundClient;

  public Zigbee2MqttHandler() {
    super(Arrays.asList(TOPIC_DEVICE_UPDATE));
  }

  @Override
  public void handle(String topic, Object payload) {
    if (topic.equals(TOPIC_DEVICE_UPDATE)) {
      try {
        List<BridgeDevice> devices =
            objectMapper.readValue((String) payload, new TypeReference<>() {});
        handleDeviceUpdate(devices);
      } catch (JsonProcessingException e) {
        log.error("Failed to parse payload for topic={}.", topic, e);
      }
    }
  }

  private void handleDeviceUpdate(List<BridgeDevice> devices) {
    if (devices == null || devices.isEmpty()) {
      log.warn("Device list is empty.");
    }

    updateAssetCache();
    List<BridgeDevice> supportedDevices = getSupportedDevices(devices);
    unsubscribeFromRemovedDevices(supportedDevices);
    createAssetsFromUnknownDevices(supportedDevices);
    updateMqttSubscription();
  }

  private void updateAssetCache() {
    this.assetCache = getAssets();
  }

  private List<Asset> getAssets() {
    List<Asset> assets = assetServiceClient.getAllAssets();
    return ListUtils.emptyIfNull(assets);
  }

  private List<BridgeDevice> getSupportedDevices(List<BridgeDevice> devices) {
    return ListUtils.emptyIfNull(devices).stream().filter(BridgeDevice::isSupported).toList();
  }

  private void unsubscribeFromRemovedDevices(List<BridgeDevice> devices) {
    this.assetCache.stream()
        .filter(
            asset ->
                devices.stream()
                    .noneMatch(device -> device.getFriendlyName().equals(asset.getName())))
        .forEach(this::disableAsset);
  }

  private void disableAsset(Asset asset) {
    ((MqttNetworkConfiguration) asset.getNetworkConfiguration()).setEnabled(false);
    Asset updatedAsset = assetServiceClient.updateAsset(asset);
    this.assetCache.set(this.assetCache.indexOf(asset), updatedAsset);
    log.debug("Disable asset={}", updatedAsset.getName());
  }

  private void createAssetsFromUnknownDevices(List<BridgeDevice> devices) {
    devices.stream()
        .filter(
            device ->
                assetCache.stream()
                    .noneMatch(asset -> asset.getName().equals(device.getFriendlyName())))
        .forEach(this::createAsset);
  }

  private void createAsset(BridgeDevice device) {
    Asset assetDto =
        Asset.of(
            device.getFriendlyName(),
            new MqttNetworkConfiguration(
                String.format(TOPIC_FORMAT, device.getFriendlyName()), true));
    Asset asset = assetServiceClient.createAsset(assetDto);
    this.assetCache.add(asset);
    log.debug("Created asset from device={}", device.getFriendlyName());
  }

  private void updateMqttSubscription() {
    this.assetCache.stream()
        .map(Asset::getNetworkConfiguration)
        .filter(config -> config.getType().equals(NetworkConfigurationType.MQTT))
        .map(config -> ((MqttNetworkConfiguration) config).getTopic())
        .filter(
            topic ->
                Arrays.stream(this.mqttInboundClient.getTopic()).noneMatch(t -> t.equals(topic)))
        .forEach(topic -> this.mqttInboundClient.addTopic(topic));
  }
}
