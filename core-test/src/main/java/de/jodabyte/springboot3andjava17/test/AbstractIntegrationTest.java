package de.jodabyte.springboot3andjava17.test;

import de.jodabyte.springboot3andjava17.test.config.TestConfiguration;
import de.jodabyte.springboot3andjava17.test.container.TestContainerContract;
import java.io.File;
import java.io.IOException;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

@Import(TestConfiguration.class)
public abstract class AbstractIntegrationTest {

  protected static File getComposeFile() {
    try {
      return new ClassPathResource(TestContainerContract.COMPOSE_FILE).getFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
