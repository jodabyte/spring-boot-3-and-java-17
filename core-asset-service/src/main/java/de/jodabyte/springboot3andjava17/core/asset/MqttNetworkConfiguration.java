package de.jodabyte.springboot3andjava17.core.asset;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MqttNetworkConfiguration implements NetworkConfiguration {

  @NotNull(message = "{validation.asset.networkConfiguration.mqtt.topic}")
  private String topic;

  @NotNull(message = "{validation.asset.networkConfiguration.mqtt.enabled}")
  private Boolean enabled;

  @Override
  public NetworkConfigurationType getType() {
    return NetworkConfigurationType.MQTT;
  }
}
