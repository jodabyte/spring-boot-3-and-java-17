package de.jodabyte.springboot3andjava17.common;

import de.jodabyte.springboot3andjava17.openapi.asset.ApiClient;
import de.jodabyte.springboot3andjava17.openapi.asset.api.AssetsApi;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;

@Configuration
public class CommonConfig {

  @Bean
  public AssetsApi getAssetsApi(@Value("${application.asset-service.url}") String assetServiceUrl) {
    return new AssetsApi(getApiClient(assetServiceUrl));
  }

  @Bean
  public RetryTemplate retryTemplate() {
    RetryTemplateBuilder builder = new RetryTemplateBuilder();
    builder.maxAttempts(3);
    builder.exponentialBackoff(Duration.ofSeconds(2), 2, Duration.ofSeconds(10));
    return builder.build();
  }

  private ApiClient getApiClient(String assetServiceUrl) {
    return new ApiClient().setBasePath(assetServiceUrl);
  }
}
