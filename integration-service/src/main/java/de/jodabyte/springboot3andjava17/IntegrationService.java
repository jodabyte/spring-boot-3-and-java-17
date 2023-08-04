package de.jodabyte.springboot3andjava17;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class IntegrationService {
  public static void main(String[] args) {
    new SpringApplicationBuilder(IntegrationService.class).web(WebApplicationType.NONE).run(args);
  }
}
