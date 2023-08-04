package de.jodabyte.springboot3andjava17.mqtt;

import de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.Zigbee2MqttHandler;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;

@Slf4j
public class MqttMessageHandler implements MessageHandler {

  private static final String HEADER_TOPIC = "mqtt_receivedTopic";

  private final List<AbstractHandler> handlers;

  public MqttMessageHandler(Zigbee2MqttHandler zigbee2MqttHandler) {
    handlers = Arrays.asList(zigbee2MqttHandler);
  }

  @Override
  public void handleMessage(Message<?> message) throws MessagingException {
    MessageHeaders headers = message.getHeaders();

    String topic = getTopic(headers);
    log.debug("received msg in topic={}", topic);
    Optional<AbstractHandler> handler = getHandler(topic);
    handler.ifPresentOrElse(
        h -> h.handle(topic, message.getPayload()),
        () -> log.info("No Handler exists for header={}", headers));
  }

  private String getTopic(MessageHeaders headers) {
    String topic =
        headers != null && headers.containsKey(HEADER_TOPIC)
            ? headers.get(HEADER_TOPIC, String.class)
            : StringUtils.EMPTY;
    return StringUtils.isNotBlank(topic) ? topic : StringUtils.EMPTY;
  }

  private Optional<AbstractHandler> getHandler(String topic) {
    return this.handlers.stream().filter(h -> h.hasTopic(topic)).findFirst();
  }
}
