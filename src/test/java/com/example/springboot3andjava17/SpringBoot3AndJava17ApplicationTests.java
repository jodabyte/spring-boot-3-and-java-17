package com.example.springboot3andjava17;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springboot3andjava17.test.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringBoot3AndJava17ApplicationTests extends AbstractIntegrationTest {

  @Test
  void ApplicationContext_LoadContext_ContextIsNotNull(ApplicationContext context) {
    assertThat(context).isNotNull();
  }
}
