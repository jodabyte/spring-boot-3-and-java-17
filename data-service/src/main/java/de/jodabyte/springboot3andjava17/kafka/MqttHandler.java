package de.jodabyte.springboot3andjava17.kafka;

import de.jodabyte.springboot3andjava17.core.kafka.KafkaContract;
import de.jodabyte.springboot3andjava17.core.kafka.KafkaSerdes;
import de.jodabyte.springboot3andjava17.core.mqtt.Zigbee2MqttDevices;
import java.time.Duration;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
public class MqttHandler {

  private static final Consumed<String, JsonSerde<?>> INPUT_PARAMETERS =
      Consumed.with(
          Serdes.String(),
          KafkaSerdes.JSON_TYPE_MAPPING_SERDE(Zigbee2MqttDevices.getTypeMappings()));
  private static final Produced<String, String> OUTPUT_PARAMETERS =
      Produced.with(Serdes.String(), Serdes.String());

  @Autowired
  void countValues(StreamsBuilder kStreamBuilder) {
    KStream<String, ?> stream =
        kStreamBuilder.stream(KafkaContract.TOPIC_MQTT, INPUT_PARAMETERS)
            .repartition(Repartitioned.as("Count-Values"));

    stream
        .groupByKey()
        .windowedBy(
            TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(1)).advanceBy(Duration.ofMinutes(1)))
        .count()
        .toStream()
        .map((window, count) -> new KeyValue<>(window.toString(), String.valueOf(count)))
        .to(KafkaContract.TOPIC_MQTT_COUNT, OUTPUT_PARAMETERS);
  }
}
