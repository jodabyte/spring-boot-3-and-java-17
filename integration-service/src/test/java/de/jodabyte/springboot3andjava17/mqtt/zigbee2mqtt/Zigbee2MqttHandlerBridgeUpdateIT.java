package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt;

import static org.junit.jupiter.api.Assertions.*;

import de.jodabyte.springboot3andjava17.ContainerizedTest;
import de.jodabyte.springboot3andjava17.openapi.asset.api.AssetsApi;
import java.util.Arrays;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class Zigbee2MqttHandlerBridgeUpdateIT extends ContainerizedTest {

  @Autowired private MqttPahoMessageDrivenChannelAdapter mqttClient;
  @Autowired private AssetsApi assetServiceApi;
  @Autowired private Zigbee2MqttHandler sut;

  @Test
  void Handle_OnlyUnsupportedDevices_TopicListIsEmpty() {
    String payload =
        getResourceContentAsString(String.format("testdata/%s", "unsupported_devices.json"));

    this.sut.handle(Zigbee2MqttHandler.TOPIC_BRIDGE_UPDATE, payload);

    assertTrue(
        Arrays.stream(mqttClient.getTopic())
            .filter(topic -> !sut.getTopics().contains(Zigbee2MqttHandler.TOPIC_BRIDGE_UPDATE))
            .toList()
            .isEmpty());
  }

  @Test
  void Handle_SubscribeToSupportedDevices_TopicListHasCorrectSize() {
    int expectedTopicSize = 1;
    String payload =
        getResourceContentAsString(String.format("testdata/%s", "unknown_devices.json"));

    this.sut.handle(Zigbee2MqttHandler.TOPIC_BRIDGE_UPDATE, payload);

    assertEquals(
        expectedTopicSize,
        Arrays.stream(mqttClient.getTopic())
            .filter(topic -> !sut.getTopics().contains(topic))
            .count());
  }

  @Test
  void Handle_UnsubscribeFromRemovedDevices_TopicListHasCorrectSize() {
    int expectedTopicSizeAfterSubscribeToUnknownDevices = 1;
    int expectedTopicSizeAfterUnsubscribeFromRemovedDevices = 0;

    String payload =
        getResourceContentAsString(String.format("testdata/%s", "unknown_devices.json"));
    this.sut.handle(Zigbee2MqttHandler.TOPIC_BRIDGE_UPDATE, payload);

    assertEquals(
        expectedTopicSizeAfterSubscribeToUnknownDevices,
        Arrays.stream(mqttClient.getTopic())
            .filter(topic -> !sut.getTopics().contains(topic))
            .count());

    payload = getResourceContentAsString(String.format("testdata/%s", "removed_device.json"));
    this.sut.handle(Zigbee2MqttHandler.TOPIC_BRIDGE_UPDATE, payload);

    assertEquals(
        expectedTopicSizeAfterUnsubscribeFromRemovedDevices,
        Arrays.stream(mqttClient.getTopic())
            .filter(topic -> !sut.getTopics().contains(topic))
            .count());
  }

  @AfterEach
  void afterEach() {
    assetServiceApi.all().forEach(asset -> assetServiceApi.delete(asset.getId()));
  }
}
