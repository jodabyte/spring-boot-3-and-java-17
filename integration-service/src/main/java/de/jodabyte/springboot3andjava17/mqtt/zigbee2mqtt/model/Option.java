package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "access",
  "description",
  "label",
  "name",
  "property",
  "type",
  "value_max",
  "value_min"
})
@Generated("jsonschema2pojo")
public class Option {

  @JsonProperty("access")
  private int access;

  @JsonProperty("description")
  private String description;

  @JsonProperty("label")
  private String label;

  @JsonProperty("name")
  private String name;

  @JsonProperty("property")
  private String property;

  @JsonProperty("type")
  private String type;

  @JsonProperty("value_max")
  private int valueMax;

  @JsonProperty("value_min")
  private int valueMin;

  @JsonProperty("access")
  public int getAccess() {
    return access;
  }

  @JsonProperty("access")
  public void setAccess(int access) {
    this.access = access;
  }

  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  @JsonProperty("description")
  public void setDescription(String description) {
    this.description = description;
  }

  @JsonProperty("label")
  public String getLabel() {
    return label;
  }

  @JsonProperty("label")
  public void setLabel(String label) {
    this.label = label;
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("property")
  public String getProperty() {
    return property;
  }

  @JsonProperty("property")
  public void setProperty(String property) {
    this.property = property;
  }

  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }

  @JsonProperty("value_max")
  public int getValueMax() {
    return valueMax;
  }

  @JsonProperty("value_max")
  public void setValueMax(int valueMax) {
    this.valueMax = valueMax;
  }

  @JsonProperty("value_min")
  public int getValueMin() {
    return valueMin;
  }

  @JsonProperty("value_min")
  public void setValueMin(int valueMin) {
    this.valueMin = valueMin;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(Option.class.getName())
        .append('@')
        .append(Integer.toHexString(System.identityHashCode(this)))
        .append('[');
    sb.append("access");
    sb.append('=');
    sb.append(this.access);
    sb.append(',');
    sb.append("description");
    sb.append('=');
    sb.append(((this.description == null) ? "<null>" : this.description));
    sb.append(',');
    sb.append("label");
    sb.append('=');
    sb.append(((this.label == null) ? "<null>" : this.label));
    sb.append(',');
    sb.append("name");
    sb.append('=');
    sb.append(((this.name == null) ? "<null>" : this.name));
    sb.append(',');
    sb.append("property");
    sb.append('=');
    sb.append(((this.property == null) ? "<null>" : this.property));
    sb.append(',');
    sb.append("type");
    sb.append('=');
    sb.append(((this.type == null) ? "<null>" : this.type));
    sb.append(',');
    sb.append("valueMax");
    sb.append('=');
    sb.append(this.valueMax);
    sb.append(',');
    sb.append("valueMin");
    sb.append('=');
    sb.append(this.valueMin);
    sb.append(',');
    if (sb.charAt((sb.length() - 1)) == ',') {
      sb.setCharAt((sb.length() - 1), ']');
    } else {
      sb.append(']');
    }
    return sb.toString();
  }
}
