package de.jodabyte.springboot3andjava17.mqtt;

import java.util.List;
import lombok.Getter;

@Getter
public abstract class AbstractHandler {

  private List<String> topics;

  protected AbstractHandler(List<String> topics) {
    this.topics = topics;
  }

  public abstract void handle(String topic, Object payload);

  public boolean hasTopic(String topic) {
    return this.topics.contains(topic);
  }

  public void addTopic(String topic) {
    this.topics.add(topic);
  }

  public void removeTopic(String topic) {
    this.topics.remove(topic);
  }
}
