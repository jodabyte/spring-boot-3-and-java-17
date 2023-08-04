package de.jodabyte.springboot3andjava17.mqtt;

import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class AbstractHandler {

  private final List<String> topics;

  protected AbstractHandler(List<String> topics) {
    this.topics = Collections.unmodifiableList(topics);
  }

  public abstract void handle(String topic, Object payload);

  public boolean hasTopic(String topic) {
    return this.topics.contains(topic);
  }
}
