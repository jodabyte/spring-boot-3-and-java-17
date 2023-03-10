package com.example.springboot3andjava17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "SpringBoot3AndJava17 API", version = "1.0"))
@SpringBootApplication
public class SpringBoot3AndJava17Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot3AndJava17Application.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:messages_validation");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
