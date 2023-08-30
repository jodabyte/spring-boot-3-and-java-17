package de.jodabyte.springboot3andjava17.core.kafka;

import java.util.Map;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class KafkaSerdes {

  public static <T> JsonSerde<T> JSON_TYPE_MAPPING_SERDE(String typeMappings) {
    JsonSerde<T> serde = new JsonSerde<>();
    serde.serializer().configure(Map.of(JsonSerializer.TYPE_MAPPINGS, typeMappings), false);
    serde.deserializer().configure(Map.of(JsonDeserializer.TYPE_MAPPINGS, typeMappings), false);
    return serde;
  }
}
