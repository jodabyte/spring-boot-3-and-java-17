package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"endpoint", "ieee_address", "type"})
@Generated("jsonschema2pojo")
public class Target {

  @JsonProperty("endpoint")
  private int endpoint;

  @JsonProperty("ieee_address")
  private String ieeeAddress;

  @JsonProperty("type")
  private String type;

  @JsonProperty("endpoint")
  public int getEndpoint() {
    return endpoint;
  }

  @JsonProperty("endpoint")
  public void setEndpoint(int endpoint) {
    this.endpoint = endpoint;
  }

  @JsonProperty("ieee_address")
  public String getIeeeAddress() {
    return ieeeAddress;
  }

  @JsonProperty("ieee_address")
  public void setIeeeAddress(String ieeeAddress) {
    this.ieeeAddress = ieeeAddress;
  }

  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(Target.class.getName())
        .append('@')
        .append(Integer.toHexString(System.identityHashCode(this)))
        .append('[');
    sb.append("endpoint");
    sb.append('=');
    sb.append(this.endpoint);
    sb.append(',');
    sb.append("ieeeAddress");
    sb.append('=');
    sb.append(((this.ieeeAddress == null) ? "<null>" : this.ieeeAddress));
    sb.append(',');
    sb.append("type");
    sb.append('=');
    sb.append(((this.type == null) ? "<null>" : this.type));
    sb.append(',');
    if (sb.charAt((sb.length() - 1)) == ',') {
      sb.setCharAt((sb.length() - 1), ']');
    } else {
      sb.append(']');
    }
    return sb.toString();
  }
}
