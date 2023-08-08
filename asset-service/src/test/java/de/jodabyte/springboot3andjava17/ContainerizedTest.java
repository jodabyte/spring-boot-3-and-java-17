package de.jodabyte.springboot3andjava17;

import de.jodabyte.springboot3andjava17.test.AbstractIntegrationTest;
import de.jodabyte.springboot3andjava17.test.container.MongoDbContainer;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

@ImportTestcontainers(MongoDbContainer.class)
public abstract class ContainerizedTest extends AbstractIntegrationTest {}
