package de.jodabyte.springboot3andjava17.mqtt.config;

import de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.Zigbee2MqttHandler;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mqtt")
@Data
public class MqttProperties {
  private String host;
  private int port;
  private String clientId;
  private String username;
  private String password;

  public char[] getPasswordAsCharArray() {
    return this.password.toCharArray();
  }

  public String[] getAllInitialTopics() {
    return new String[] {Zigbee2MqttHandler.TOPIC_BRIDGE_UPDATE};
  }
}
