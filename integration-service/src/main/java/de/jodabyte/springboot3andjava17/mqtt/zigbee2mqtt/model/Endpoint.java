package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"bindings", "clusters", "configured_reportings", "scenes"})
@Generated("jsonschema2pojo")
public class Endpoint {

  @JsonProperty("bindings")
  private List<Binding> bindings = new ArrayList<Binding>();

  @JsonProperty("clusters")
  private Clusters clusters;

  @JsonProperty("configured_reportings")
  private List<ConfiguredReporting> configuredReportings = new ArrayList<ConfiguredReporting>();

  @JsonProperty("scenes")
  private List<Object> scenes = new ArrayList<Object>();

  @JsonProperty("bindings")
  public List<Binding> getBindings() {
    return bindings;
  }

  @JsonProperty("bindings")
  public void setBindings(List<Binding> bindings) {
    this.bindings = bindings;
  }

  @JsonProperty("clusters")
  public Clusters getClusters() {
    return clusters;
  }

  @JsonProperty("clusters")
  public void setClusters(Clusters clusters) {
    this.clusters = clusters;
  }

  @JsonProperty("configured_reportings")
  public List<ConfiguredReporting> getConfiguredReportings() {
    return configuredReportings;
  }

  @JsonProperty("configured_reportings")
  public void setConfiguredReportings(List<ConfiguredReporting> configuredReportings) {
    this.configuredReportings = configuredReportings;
  }

  @JsonProperty("scenes")
  public List<Object> getScenes() {
    return scenes;
  }

  @JsonProperty("scenes")
  public void setScenes(List<Object> scenes) {
    this.scenes = scenes;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(Endpoint.class.getName())
        .append('@')
        .append(Integer.toHexString(System.identityHashCode(this)))
        .append('[');
    sb.append("bindings");
    sb.append('=');
    sb.append(((this.bindings == null) ? "<null>" : this.bindings));
    sb.append(',');
    sb.append("clusters");
    sb.append('=');
    sb.append(((this.clusters == null) ? "<null>" : this.clusters));
    sb.append(',');
    sb.append("configuredReportings");
    sb.append('=');
    sb.append(((this.configuredReportings == null) ? "<null>" : this.configuredReportings));
    sb.append(',');
    sb.append("scenes");
    sb.append('=');
    sb.append(((this.scenes == null) ? "<null>" : this.scenes));
    sb.append(',');
    if (sb.charAt((sb.length() - 1)) == ',') {
      sb.setCharAt((sb.length() - 1), ']');
    } else {
      sb.append(']');
    }
    return sb.toString();
  }
}
