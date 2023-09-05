package de.jodabyte.springboot3andjava17.mqtt;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import de.jodabyte.springboot3andjava17.ContainerizedTest;
import de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.Zigbee2MqttHandler;
import de.jodabyte.springboot3andjava17.test.data.DataFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(OutputCaptureExtension.class)
@MockBean(Zigbee2MqttHandler.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MqttMessageHandlerIT extends ContainerizedTest {

  @Autowired private MqttMessageHandler sut;
  @Autowired private Zigbee2MqttHandler handlerMock;
  @Autowired private DataFactory dataFactory;
  private String topic;
  private Message<?> message;

  @BeforeAll
  void beforeAll() {
    this.topic = this.dataFactory.createMqttTopic();
    this.message =
        MessageBuilder.withPayload(StringUtils.EMPTY)
            .setHeader(MqttHeaders.RECEIVED_TOPIC, this.topic)
            .build();
  }

  @Test
  void HandleMessage_HandlerPresent_Successful() {
    given(this.handlerMock.hasTopic(this.topic)).willReturn(true);

    this.sut.handleMessage(this.message);

    verify(this.handlerMock).handle(this.topic, this.message.getPayload());
  }

  @Test
  void HandleMessage_HandlerNotPresent_LogMessage(CapturedOutput actualOutput) {
    String expectedLogMessage = "No Handler exists for header";
    given(this.handlerMock.hasTopic(this.topic)).willReturn(false);

    this.sut.handleMessage(this.message);

    verify(this.handlerMock, never()).handle(this.topic, this.message.getPayload());
    assertThat(actualOutput.getOut()).contains(expectedLogMessage);
  }
}
