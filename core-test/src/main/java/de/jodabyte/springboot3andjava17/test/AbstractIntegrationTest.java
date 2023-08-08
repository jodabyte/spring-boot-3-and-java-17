package de.jodabyte.springboot3andjava17.test;

import de.jodabyte.springboot3andjava17.test.config.TestConfiguration;
import org.springframework.context.annotation.Import;

@Import(TestConfiguration.class)
public abstract class AbstractIntegrationTest {}
