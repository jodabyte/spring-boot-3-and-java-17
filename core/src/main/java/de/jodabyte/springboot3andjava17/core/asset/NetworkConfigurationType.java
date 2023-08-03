package de.jodabyte.springboot3andjava17.core.asset;

import lombok.Getter;

@Getter
public enum NetworkConfigurationType {
  MQTT(Constants.MQTT);

  private final String type;

  NetworkConfigurationType(String type) {
    this.type = type;
  }

  public static final class Constants {
    public static final String MQTT = "mqtt";

    private Constants() {}
  }
}
