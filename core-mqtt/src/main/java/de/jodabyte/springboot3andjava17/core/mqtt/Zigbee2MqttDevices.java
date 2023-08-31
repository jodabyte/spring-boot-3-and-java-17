package de.jodabyte.springboot3andjava17.core.mqtt;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum Zigbee2MqttDevices {
  VINDSTYRKA("zigbee2mqtt/VINDSTYRKA", toJavaType(Vindstyrka.class));

  private final String topic;
  private final JavaType type;

  Zigbee2MqttDevices(String topic, JavaType type) {
    this.topic = topic;
    this.type = type;
  }

  public static Optional<Zigbee2MqttDevices> findDeviceByTopic(String topic) {
    return Arrays.stream(values()).filter(device -> device.getTopic().equals(topic)).findFirst();
  }

  public static String getTypeMappings() {
    return Arrays.stream(values())
        .map(device -> device.getType().getRawClass())
        .map(clazz -> String.join(":", clazz.getSimpleName(), clazz.getName()))
        .collect(Collectors.joining(", "));
  }

  private static JavaType toJavaType(Class<?> clazz) {
    return TypeFactory.defaultInstance().constructType(clazz);
  }
}
