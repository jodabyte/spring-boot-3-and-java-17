package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.*;

import de.jodabyte.springboot3andjava17.ContainerizedTest;
import de.jodabyte.springboot3andjava17.core.asset.AssetServiceClient;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class Zigbee2MqttHandlerTest extends ContainerizedTest {

  @Autowired private MqttPahoMessageDrivenChannelAdapter mqttClient;
  @Autowired private AssetServiceClient assetServiceClient;
  @Autowired private Zigbee2MqttHandler sut;

  @Test
  void Handle_OnlyUnsupportedDevices_TopicListIsEmpty() throws IOException {
    String payload = getFileContentAsString("unsupported_devices.json");

    this.sut.handle(Zigbee2MqttHandler.TOPIC_DEVICE_UPDATE, payload);

    assertTrue(
        Arrays.stream(mqttClient.getTopic())
            .filter(topic -> !sut.getTopics().contains(Zigbee2MqttHandler.TOPIC_DEVICE_UPDATE))
            .toList()
            .isEmpty());
  }

  @Test
  void Handle_SubscribeToUnknownDevices_TopicListHasCorrectSize() throws IOException {
    int expectedTopicSize = 2;
    String payload = getFileContentAsString("unknown_devices.json");

    this.sut.handle(Zigbee2MqttHandler.TOPIC_DEVICE_UPDATE, payload);

    assertEquals(
        expectedTopicSize,
        Arrays.stream(mqttClient.getTopic())
            .filter(topic -> !sut.getTopics().contains(topic))
            .toList()
            .size());
  }

  @Test
  void Handle_UnsubscribeFromRemovedDevices_TopicListHasCorrectSize()
      throws IOException, InterruptedException {
    int expectedTopicSizeAfterSubscribeToUnknownDevices = 2;
    int expectedTopicSizeAfterUnsubscribeFromRemovedDevices = 1;

    String payload = getFileContentAsString("unknown_devices.json");
    this.sut.handle(Zigbee2MqttHandler.TOPIC_DEVICE_UPDATE, payload);

    assertEquals(
        expectedTopicSizeAfterSubscribeToUnknownDevices,
        Arrays.stream(mqttClient.getTopic())
            .filter(topic -> !sut.getTopics().contains(topic))
            .toList()
            .size());

    payload = getFileContentAsString("one_removed_device.json");
    this.sut.handle(Zigbee2MqttHandler.TOPIC_DEVICE_UPDATE, payload);

    assertEquals(
        expectedTopicSizeAfterUnsubscribeFromRemovedDevices,
        Arrays.stream(mqttClient.getTopic())
            .filter(topic -> !sut.getTopics().contains(topic))
            .count());
  }

  @BeforeEach
  @AfterEach
  void afterEach() {
    assetServiceClient.getAllAssets().forEach(asset -> assetServiceClient.deleteAsset(asset));
  }

  private String getFileContentAsString(String fileName) throws IOException {
    return new ClassPathResource(String.format("testdata/%s", fileName))
        .getContentAsString(StandardCharsets.UTF_8);
  }
}
