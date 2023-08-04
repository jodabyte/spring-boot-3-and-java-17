package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class Endpoints {

  @JsonIgnore private Map<String, Endpoint> endpoints = new LinkedHashMap<String, Endpoint>();

  @JsonAnyGetter
  public Map<String, Endpoint> getEndpoints() {
    return this.endpoints;
  }

  @JsonAnySetter
  public void addEndpoint(String name, Endpoint value) {
    this.endpoints.put(name, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(Endpoints.class.getName())
        .append('@')
        .append(Integer.toHexString(System.identityHashCode(this)))
        .append('[');
    sb.append("endpoints");
    sb.append('=');
    sb.append(((this.endpoints == null) ? "<null>" : this.endpoints));
    sb.append(',');
    if (sb.charAt((sb.length() - 1)) == ',') {
      sb.setCharAt((sb.length() - 1), ']');
    } else {
      sb.append(']');
    }
    return sb.toString();
  }
}
