package de.jodabyte.springboot3andjava17;

import de.jodabyte.springboot3andjava17.config.KafkaTestConfig;
import de.jodabyte.springboot3andjava17.core.kafka.KafkaContract;
import de.jodabyte.springboot3andjava17.test.AbstractIntegrationTest;
import de.jodabyte.springboot3andjava17.test.container.TestContainerContract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@TestPropertySource(
    properties =
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration")
@EmbeddedKafka(
    partitions = 1,
    topics = {KafkaContract.TOPIC_MQTT})
@DirtiesContext
@Import({KafkaTestConfig.class})
@Testcontainers
public abstract class ContainerizedTest extends AbstractIntegrationTest {

  static DockerComposeContainer ENVIRONMENT =
      new DockerComposeContainer(getComposeFile())
          .withExposedService(
              TestContainerContract.MOSQUITTO_NAME, TestContainerContract.MOSQUITTO_PORT)
          .withExposedService(
              TestContainerContract.ASSET_SERVICE_NAME, TestContainerContract.ASSET_SERVICE_PORT);

  static {
    ENVIRONMENT.start();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    int mqttPort =
        ENVIRONMENT.getServicePort(
            TestContainerContract.MOSQUITTO_NAME, TestContainerContract.MOSQUITTO_PORT);
    registry.add("mqtt.port", () -> mqttPort);
    log.info("Updated mqtt.port to {}", mqttPort);
    String url =
        String.format(
            "http://localhost:%d",
            ENVIRONMENT.getServicePort(
                TestContainerContract.ASSET_SERVICE_NAME,
                TestContainerContract.ASSET_SERVICE_PORT));
    registry.add("application.asset-service.url", () -> url);
    log.info("Updated application.asset-service.url to {}", url);
  }
}
