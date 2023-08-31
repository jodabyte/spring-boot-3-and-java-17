package de.jodabyte.springboot3andjava17.asset.model;

import de.jodabyte.springboot3andjava17.core.asset.NetworkConfiguration;
import de.jodabyte.springboot3andjava17.core.asset.NetworkConfigurationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MqttNetworkConfiguration implements NetworkConfiguration {

  @NotNull(message = "{validation.asset.networkConfiguration.mqtt.topic}")
  private String topic;

  @NotNull(message = "{validation.asset.networkConfiguration.mqtt.enabled}")
  private boolean enabled;

  @Override
  public NetworkConfigurationType getType() {
    return NetworkConfigurationType.MQTT;
  }
}
