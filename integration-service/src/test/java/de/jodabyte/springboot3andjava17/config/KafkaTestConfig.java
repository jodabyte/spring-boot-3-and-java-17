package de.jodabyte.springboot3andjava17.config;

import de.jodabyte.springboot3andjava17.core.mqtt.Zigbee2MqttDevices;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;

@TestConfiguration
@Slf4j
public class KafkaTestConfig {

  @Bean
  public DefaultKafkaConsumerFactory<String, Object> consumerFactory(
      KafkaProperties kafkaProperties) {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs(kafkaProperties));
  }

  private Map<String, Object> consumerConfigs(KafkaProperties kafkaProperties) {
    Map<String, Object> consumerProperties =
        KafkaTestUtils.consumerProps(
            String.join(",", kafkaProperties.getBootstrapServers()),
            kafkaProperties.getConsumer().getGroupId(),
            "false");
    consumerProperties.putAll(kafkaProperties.buildConsumerProperties());
    consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    consumerProperties.put(JsonDeserializer.TYPE_MAPPINGS, Zigbee2MqttDevices.getTypeMappings());
    log.info("KafkaConsumerProperties: {}", consumerProperties);
    return consumerProperties;
  }
}
