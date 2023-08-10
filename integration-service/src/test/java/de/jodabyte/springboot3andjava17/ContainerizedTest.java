package de.jodabyte.springboot3andjava17;

import de.jodabyte.springboot3andjava17.test.AbstractIntegrationTest;
import de.jodabyte.springboot3andjava17.test.container.TestContainerContract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@TestPropertySource(
    properties =
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration")
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
    int port =
        ENVIRONMENT.getServicePort(
            TestContainerContract.MOSQUITTO_NAME, TestContainerContract.MOSQUITTO_PORT);
    registry.add("mqtt.port", () -> port);
    log.info("Updated mqtt.port to {}", port);
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
