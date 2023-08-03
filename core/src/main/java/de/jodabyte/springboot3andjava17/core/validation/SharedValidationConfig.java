package de.jodabyte.springboot3andjava17.core.validation;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class SharedValidationConfig {

  @Autowired ReloadableResourceBundleMessageSource messageSource;

  @PostConstruct
  private void init() {
    messageSource.addBasenames("classpath:messages-validation");
  }
}
