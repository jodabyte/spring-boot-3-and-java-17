package de.jodabyte.springboot3andjava17.kafka;

import de.jodabyte.springboot3andjava17.core.mqtt.Zigbee2MqttDevices;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Slf4j
@Configuration
public class KafkaConfig {

  public static final String TOPIC_MQTT = "mqtt";

  @Bean
  public DefaultKafkaProducerFactory<String, Object> producerFactory(
      KafkaProperties kafkaProperties) {
    Map<String, Object> properties = kafkaProperties.buildProducerProperties();
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    properties.put(JsonSerializer.TYPE_MAPPINGS, Zigbee2MqttDevices.getTypeMappings());
    log.info("KafkaProducerProperties: {}", properties);
    return new DefaultKafkaProducerFactory<>(properties);
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate(
      DefaultKafkaProducerFactory<String, Object> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }
}
