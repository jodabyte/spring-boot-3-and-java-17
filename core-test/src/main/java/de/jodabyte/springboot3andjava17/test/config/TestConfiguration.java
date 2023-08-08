package de.jodabyte.springboot3andjava17.test.config;

import de.jodabyte.springboot3andjava17.test.data.DataFactory;
import org.springframework.context.annotation.Bean;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {

  @Bean
  public DataFactory getDataFactory() {
    return new DataFactory();
  }
}
