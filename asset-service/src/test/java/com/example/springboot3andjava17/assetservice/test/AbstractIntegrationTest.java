package de.jodabyte.springboot3andjava17.assetservice.test;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractIntegrationTest {

  protected static final GenericContainer<MongoDBContainer> MONGODB;

  static {
    MONGODB =
        new GenericContainer<MongoDBContainer>(
                DockerImageName.parse("mongodb/mongodb-community-server:6.0-ubi8"))
            .withExposedPorts(27017);
    MONGODB.start();
  }

  @DynamicPropertySource
  static void registerMongoProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.port", MONGODB::getFirstMappedPort);
  }
}
