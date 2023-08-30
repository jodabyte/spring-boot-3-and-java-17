package de.jodabyte.springboot3andjava17.common;

import de.jodabyte.springboot3andjava17.openapi.asset.ApiClient;
import de.jodabyte.springboot3andjava17.openapi.asset.api.AssetsApi;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;

@Configuration
@Slf4j
public class CommonConfig {

  @Bean
  public AssetsApi getAssetsApi(@Value("${application.asset-service.url}") String assetServiceUrl) {
    return new AssetsApi(getApiClient(assetServiceUrl));
  }

  @Bean
  public MessageSource messageSource() {
    var messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages-validation");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
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
