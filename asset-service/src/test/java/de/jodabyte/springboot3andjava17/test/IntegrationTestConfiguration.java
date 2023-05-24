package de.jodabyte.springboot3andjava17.test;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class IntegrationTestConfiguration {

  @Bean
  public DataFactory getDataFactory() {
    return new DataFactory();
  }
}
