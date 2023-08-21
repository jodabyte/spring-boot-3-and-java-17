package de.jodabyte.springboot3andjava17.core.asset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(
      value = MqttNetworkConfiguration.class,
      name = NetworkConfigurationType.Constants.MQTT)
})
@Schema(anyOf = {MqttNetworkConfiguration.class})
public interface NetworkConfiguration {

  @JsonIgnore
  NetworkConfigurationType getType();
}
