package de.jodabyte.springboot3andjava17.common.observability;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.observability.ContextProviderFactory;
import org.springframework.data.mongodb.observability.MongoObservationCommandListener;

@Configuration
public class ObservabilityConfig {

  @Bean
  MongoClientSettingsBuilderCustomizer mongoMetricsSynchronousContextProvider(
      ObservationRegistry registry) {
    return clientSettingsBuilder ->
        clientSettingsBuilder
            .contextProvider(ContextProviderFactory.create(registry))
            .addCommandListener(new MongoObservationCommandListener(registry));
  }
}
