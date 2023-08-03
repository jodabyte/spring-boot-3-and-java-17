package de.jodabyte.springboot3andjava17.core.asset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MqttNetworkConfiguration implements NetworkConfiguration {

  private String topic;
  private boolean enabled;

  @Override
  public NetworkConfigurationType getType() {
    return NetworkConfigurationType.MQTT;
  }
}
