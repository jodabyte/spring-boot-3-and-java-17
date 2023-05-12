package de.jodabyte.springboot3andjava17.assetservice.common.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ValidationConfig {

  @Bean
  public MessageSource messageSource() {
    var messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages_validation");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}
