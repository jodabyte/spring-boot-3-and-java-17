package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"cluster", "target"})
@Generated("jsonschema2pojo")
public class Binding {

  @JsonProperty("cluster")
  private String cluster;

  @JsonProperty("target")
  private Target target;

  @JsonProperty("cluster")
  public String getCluster() {
    return cluster;
  }

  @JsonProperty("cluster")
  public void setCluster(String cluster) {
    this.cluster = cluster;
  }

  @JsonProperty("target")
  public Target getTarget() {
    return target;
  }

  @JsonProperty("target")
  public void setTarget(Target target) {
    this.target = target;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(Binding.class.getName())
        .append('@')
        .append(Integer.toHexString(System.identityHashCode(this)))
        .append('[');
    sb.append("cluster");
    sb.append('=');
    sb.append(((this.cluster == null) ? "<null>" : this.cluster));
    sb.append(',');
    sb.append("target");
    sb.append('=');
    sb.append(((this.target == null) ? "<null>" : this.target));
    sb.append(',');
    if (sb.charAt((sb.length() - 1)) == ',') {
      sb.setCharAt((sb.length() - 1), ']');
    } else {
      sb.append(']');
    }
    return sb.toString();
  }
}
