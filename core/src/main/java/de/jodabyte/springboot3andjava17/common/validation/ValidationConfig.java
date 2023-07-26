package de.jodabyte.springboot3andjava17.common.validation;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ValidationConfig {

  @Autowired ReloadableResourceBundleMessageSource messageSource;

  @PostConstruct
  private void init() {
    messageSource.addBasenames("classpath:messages_validation");
  }
}
