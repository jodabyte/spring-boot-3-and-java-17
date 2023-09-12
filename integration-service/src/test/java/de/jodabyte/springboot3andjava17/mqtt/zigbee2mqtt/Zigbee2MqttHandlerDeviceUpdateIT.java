package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt;

import static org.junit.jupiter.api.Assertions.*;

import de.jodabyte.springboot3andjava17.ContainerizedTest;
import de.jodabyte.springboot3andjava17.core.kafka.KafkaContract;
import java.time.Duration;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class Zigbee2MqttHandlerDeviceUpdateIT extends ContainerizedTest {

  @Autowired private EmbeddedKafkaBroker kafkaBroker;
  @Autowired private DefaultKafkaConsumerFactory<String, Object> consumerFactory;
  private Zigbee2MqttHandler sut;

  @BeforeEach
  void beforeEach(ApplicationContext context) {
    this.sut = context.getBean(Zigbee2MqttHandler.class);
  }

  @Test
  void Handle_ReceivedDataIsForwardedToKafka_KafkaConsumerReceivesData() {
    int expectedRecordCount = 1;

    Consumer<String, Object> consumer = consumerFactory.createConsumer("integration-test");
    this.kafkaBroker.consumeFromAnEmbeddedTopic(consumer, KafkaContract.TOPIC_MQTT);

    String payload =
        getResourceContentAsString(String.format("testdata/%s", "unknown_devices.json"));
    this.sut.handle(Zigbee2MqttHandler.TOPIC_BRIDGE_UPDATE, payload);

    String device = "vindstyrka";
    payload = getResourceContentAsString(String.format("testdata/%s.json", device));
    this.sut.handle(
        String.format(Zigbee2MqttHandler.DEVICE_UPDATE_TOPIC_FORMAT, device.toUpperCase()),
        payload);

    ConsumerRecords<String, Object> records =
        KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(5));
    assertEquals(expectedRecordCount, records.count());
    assertEquals(KafkaContract.TOPIC_MQTT, records.iterator().next().topic());
  }

  @Test
  void Handle_CouldNotMapPayloadToJson_LogMessage() {
    String payload =
        getResourceContentAsString(String.format("testdata/%s", "unknown_devices.json"));
    this.sut.handle(Zigbee2MqttHandler.TOPIC_BRIDGE_UPDATE, payload);

    this.sut.setAssetServiceApi(Mockito.spy(this.sut.getAssetServiceApi()));
    String device = "vindstyrka";
    payload = StringUtils.EMPTY;
    this.sut.handle(
        String.format(Zigbee2MqttHandler.DEVICE_UPDATE_TOPIC_FORMAT, device.toUpperCase()),
        payload);

    Mockito.verify(this.sut.getAssetServiceApi(), Mockito.never()).all();
  }
}
