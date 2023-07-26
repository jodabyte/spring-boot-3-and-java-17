package de.jodabyte.springboot3andjava17.asset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(
      value = MqttNetworkConfiguration.class,
      name = NetworkConfigurationType.Constants.MQTT)
})
public interface NetworkConfiguration {

  @JsonIgnore
  NetworkConfigurationType getType();
}
