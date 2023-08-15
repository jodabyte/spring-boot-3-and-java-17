package de.jodabyte.springboot3andjava17;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class IntegrationServiceApplicationTests extends ContainerizedTest {

  @Test
  void ApplicationContext_LoadContext_ContextIsNotNull(ApplicationContext context) {
    assertThat(context).isNotNull();
  }
}
