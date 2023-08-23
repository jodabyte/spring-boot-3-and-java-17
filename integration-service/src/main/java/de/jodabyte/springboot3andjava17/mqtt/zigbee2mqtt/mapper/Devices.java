package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.mapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum Devices {
  VINDSTYRKA("zigbee2mqtt/VINDSTYRKA", toJavaType(Vindstyrka.class));

  private final String topic;
  private final JavaType type;

  Devices(String topic, JavaType type) {
    this.topic = topic;
    this.type = type;
  }

  public static Optional<Devices> findDevice(String topic) {
    return Arrays.stream(values()).filter(device -> device.getTopic().equals(topic)).findFirst();
  }

  private static JavaType toJavaType(Class<?> clazz) {
    return TypeFactory.defaultInstance().constructType(clazz);
  }
}
