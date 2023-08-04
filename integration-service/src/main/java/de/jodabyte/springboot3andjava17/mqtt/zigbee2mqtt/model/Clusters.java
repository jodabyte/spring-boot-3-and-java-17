package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"input", "output"})
@Generated("jsonschema2pojo")
public class Clusters {

  @JsonProperty("input")
  private List<String> input = new ArrayList<String>();

  @JsonProperty("output")
  private List<String> output = new ArrayList<String>();

  @JsonProperty("input")
  public List<String> getInput() {
    return input;
  }

  @JsonProperty("input")
  public void setInput(List<String> input) {
    this.input = input;
  }

  @JsonProperty("output")
  public List<String> getOutput() {
    return output;
  }

  @JsonProperty("output")
  public void setOutput(List<String> output) {
    this.output = output;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(Clusters.class.getName())
        .append('@')
        .append(Integer.toHexString(System.identityHashCode(this)))
        .append('[');
    sb.append("input");
    sb.append('=');
    sb.append(((this.input == null) ? "<null>" : this.input));
    sb.append(',');
    sb.append("output");
    sb.append('=');
    sb.append(((this.output == null) ? "<null>" : this.output));
    sb.append(',');
    if (sb.charAt((sb.length() - 1)) == ',') {
      sb.setCharAt((sb.length() - 1), ']');
    } else {
      sb.append(']');
    }
    return sb.toString();
  }
}
