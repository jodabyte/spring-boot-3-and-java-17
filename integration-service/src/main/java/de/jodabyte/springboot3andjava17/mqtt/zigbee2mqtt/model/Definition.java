package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"description", "exposes", "icon", "model", "options", "supports_ota", "vendor"})
@Generated("jsonschema2pojo")
public class Definition {

  @JsonProperty("description")
  private String description;

  @JsonProperty("exposes")
  private List<Expose> exposes = new ArrayList<Expose>();

  @JsonProperty("icon")
  private String icon;

  @JsonProperty("model")
  private String model;

  @JsonProperty("options")
  private List<Option> options = new ArrayList<Option>();

  @JsonProperty("supports_ota")
  private boolean supportsOta;

  @JsonProperty("vendor")
  private String vendor;

  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  @JsonProperty("description")
  public void setDescription(String description) {
    this.description = description;
  }

  @JsonProperty("exposes")
  public List<Expose> getExposes() {
    return exposes;
  }

  @JsonProperty("exposes")
  public void setExposes(List<Expose> exposes) {
    this.exposes = exposes;
  }

  @JsonProperty("icon")
  public String getIcon() {
    return icon;
  }

  @JsonProperty("icon")
  public void setIcon(String icon) {
    this.icon = icon;
  }

  @JsonProperty("model")
  public String getModel() {
    return model;
  }

  @JsonProperty("model")
  public void setModel(String model) {
    this.model = model;
  }

  @JsonProperty("options")
  public List<Option> getOptions() {
    return options;
  }

  @JsonProperty("options")
  public void setOptions(List<Option> options) {
    this.options = options;
  }

  @JsonProperty("supports_ota")
  public boolean isSupportsOta() {
    return supportsOta;
  }

  @JsonProperty("supports_ota")
  public void setSupportsOta(boolean supportsOta) {
    this.supportsOta = supportsOta;
  }

  @JsonProperty("vendor")
  public String getVendor() {
    return vendor;
  }

  @JsonProperty("vendor")
  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(Definition.class.getName())
        .append('@')
        .append(Integer.toHexString(System.identityHashCode(this)))
        .append('[');
    sb.append("description");
    sb.append('=');
    sb.append(((this.description == null) ? "<null>" : this.description));
    sb.append(',');
    sb.append("exposes");
    sb.append('=');
    sb.append(((this.exposes == null) ? "<null>" : this.exposes));
    sb.append(',');
    sb.append("icon");
    sb.append('=');
    sb.append(((this.icon == null) ? "<null>" : this.icon));
    sb.append(',');
    sb.append("model");
    sb.append('=');
    sb.append(((this.model == null) ? "<null>" : this.model));
    sb.append(',');
    sb.append("options");
    sb.append('=');
    sb.append(((this.options == null) ? "<null>" : this.options));
    sb.append(',');
    sb.append("supportsOta");
    sb.append('=');
    sb.append(this.supportsOta);
    sb.append(',');
    sb.append("vendor");
    sb.append('=');
    sb.append(((this.vendor == null) ? "<null>" : this.vendor));
    sb.append(',');
    if (sb.charAt((sb.length() - 1)) == ',') {
      sb.setCharAt((sb.length() - 1), ']');
    } else {
      sb.append(']');
    }
    return sb.toString();
  }
}
