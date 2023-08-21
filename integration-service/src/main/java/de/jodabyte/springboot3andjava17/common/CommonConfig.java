package de.jodabyte.springboot3andjava17.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.jodabyte.springboot3andjava17.openapi.asset.ApiClient;
import de.jodabyte.springboot3andjava17.openapi.asset.api.AssetsApi;
import java.text.DateFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class CommonConfig {

  public static AssetsApi getAssetsApi(String assetServiceUrl) {
    log.info("Init ApiClient with basePath: {}", assetServiceUrl);
    DateFormat defaultDateFormat = ApiClient.createDefaultDateFormat();
    ObjectMapper defaultObjectMapper = ApiClient.createDefaultObjectMapper(defaultDateFormat);
    WebClient.Builder builder = ApiClient.buildWebClientBuilder(defaultObjectMapper);
    return new AssetsApi(
        new ApiClient(
                builder.baseUrl(assetServiceUrl).build(), defaultObjectMapper, defaultDateFormat)
            .setBasePath(assetServiceUrl));
  }

  @Bean
  public MessageSource messageSource() {
    var messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages-validation");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}
