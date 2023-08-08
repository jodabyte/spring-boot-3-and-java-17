package de.jodabyte.springboot3andjava17.test.container;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public interface MongoDbContainer {

  @Container @ServiceConnection
  MongoDBContainer MONGODB =
      new MongoDBContainer(
          DockerImageName.parse(TestContainerContract.MONGODB_IMAGE)
              .asCompatibleSubstituteFor("mongo"));
}
