package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "definition",
  "disabled",
  "endpoints",
  "friendly_name",
  "ieee_address",
  "interview_completed",
  "interviewing",
  "network_address",
  "supported",
  "type",
  "date_code",
  "manufacturer",
  "model_id",
  "power_source",
  "software_build_id"
})
@Generated("jsonschema2pojo")
public class BridgeDevice {

  @JsonProperty("definition")
  private Definition definition;

  @JsonProperty("disabled")
  private boolean disabled;

  @JsonProperty("endpoints")
  private Endpoints endpoints;

  @JsonProperty("friendly_name")
  private String friendlyName;

  @JsonProperty("ieee_address")
  private String ieeeAddress;

  @JsonProperty("interview_completed")
  private boolean interviewCompleted;

  @JsonProperty("interviewing")
  private boolean interviewing;

  @JsonProperty("network_address")
  private int networkAddress;

  @JsonProperty("supported")
  private boolean supported;

  @JsonProperty("type")
  private String type;

  @JsonProperty("date_code")
  private String dateCode;

  @JsonProperty("manufacturer")
  private String manufacturer;

  @JsonProperty("model_id")
  private String modelId;

  @JsonProperty("power_source")
  private String powerSource;

  @JsonProperty("software_build_id")
  private String softwareBuildId;

  @JsonProperty("definition")
  public Definition getDefinition() {
    return definition;
  }

  @JsonProperty("definition")
  public void setDefinition(Definition definition) {
    this.definition = definition;
  }

  @JsonProperty("disabled")
  public boolean isDisabled() {
    return disabled;
  }

  @JsonProperty("disabled")
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  @JsonProperty("endpoints")
  public Endpoints getEndpoints() {
    return endpoints;
  }

  @JsonProperty("endpoints")
  public void setEndpoints(Endpoints endpoints) {
    this.endpoints = endpoints;
  }

  @JsonProperty("friendly_name")
  public String getFriendlyName() {
    return friendlyName;
  }

  @JsonProperty("friendly_name")
  public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  @JsonProperty("ieee_address")
  public String getIeeeAddress() {
    return ieeeAddress;
  }

  @JsonProperty("ieee_address")
  public void setIeeeAddress(String ieeeAddress) {
    this.ieeeAddress = ieeeAddress;
  }

  @JsonProperty("interview_completed")
  public boolean isInterviewCompleted() {
    return interviewCompleted;
  }

  @JsonProperty("interview_completed")
  public void setInterviewCompleted(boolean interviewCompleted) {
    this.interviewCompleted = interviewCompleted;
  }

  @JsonProperty("interviewing")
  public boolean isInterviewing() {
    return interviewing;
  }

  @JsonProperty("interviewing")
  public void setInterviewing(boolean interviewing) {
    this.interviewing = interviewing;
  }

  @JsonProperty("network_address")
  public int getNetworkAddress() {
    return networkAddress;
  }

  @JsonProperty("network_address")
  public void setNetworkAddress(int networkAddress) {
    this.networkAddress = networkAddress;
  }

  @JsonProperty("supported")
  public boolean isSupported() {
    return supported;
  }

  @JsonProperty("supported")
  public void setSupported(boolean supported) {
    this.supported = supported;
  }

  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }

  @JsonProperty("date_code")
  public String getDateCode() {
    return dateCode;
  }

  @JsonProperty("date_code")
  public void setDateCode(String dateCode) {
    this.dateCode = dateCode;
  }

  @JsonProperty("manufacturer")
  public String getManufacturer() {
    return manufacturer;
  }

  @JsonProperty("manufacturer")
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  @JsonProperty("model_id")
  public String getModelId() {
    return modelId;
  }

  @JsonProperty("model_id")
  public void setModelId(String modelId) {
    this.modelId = modelId;
  }

  @JsonProperty("power_source")
  public String getPowerSource() {
    return powerSource;
  }

  @JsonProperty("power_source")
  public void setPowerSource(String powerSource) {
    this.powerSource = powerSource;
  }

  @JsonProperty("software_build_id")
  public String getSoftwareBuildId() {
    return softwareBuildId;
  }

  @JsonProperty("software_build_id")
  public void setSoftwareBuildId(String softwareBuildId) {
    this.softwareBuildId = softwareBuildId;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(BridgeDevice.class.getName())
        .append('@')
        .append(Integer.toHexString(System.identityHashCode(this)))
        .append('[');
    sb.append("definition");
    sb.append('=');
    sb.append(((this.definition == null) ? "<null>" : this.definition));
    sb.append(',');
    sb.append("disabled");
    sb.append('=');
    sb.append(this.disabled);
    sb.append(',');
    sb.append("endpoints");
    sb.append('=');
    sb.append(((this.endpoints == null) ? "<null>" : this.endpoints));
    sb.append(',');
    sb.append("friendlyName");
    sb.append('=');
    sb.append(((this.friendlyName == null) ? "<null>" : this.friendlyName));
    sb.append(',');
    sb.append("ieeeAddress");
    sb.append('=');
    sb.append(((this.ieeeAddress == null) ? "<null>" : this.ieeeAddress));
    sb.append(',');
    sb.append("interviewCompleted");
    sb.append('=');
    sb.append(this.interviewCompleted);
    sb.append(',');
    sb.append("interviewing");
    sb.append('=');
    sb.append(this.interviewing);
    sb.append(',');
    sb.append("networkAddress");
    sb.append('=');
    sb.append(this.networkAddress);
    sb.append(',');
    sb.append("supported");
    sb.append('=');
    sb.append(this.supported);
    sb.append(',');
    sb.append("type");
    sb.append('=');
    sb.append(((this.type == null) ? "<null>" : this.type));
    sb.append(',');
    sb.append("dateCode");
    sb.append('=');
    sb.append(((this.dateCode == null) ? "<null>" : this.dateCode));
    sb.append(',');
    sb.append("manufacturer");
    sb.append('=');
    sb.append(((this.manufacturer == null) ? "<null>" : this.manufacturer));
    sb.append(',');
    sb.append("modelId");
    sb.append('=');
    sb.append(((this.modelId == null) ? "<null>" : this.modelId));
    sb.append(',');
    sb.append("powerSource");
    sb.append('=');
    sb.append(((this.powerSource == null) ? "<null>" : this.powerSource));
    sb.append(',');
    sb.append("softwareBuildId");
    sb.append('=');
    sb.append(((this.softwareBuildId == null) ? "<null>" : this.softwareBuildId));
    sb.append(',');
    if (sb.charAt((sb.length() - 1)) == ',') {
      sb.setCharAt((sb.length() - 1), ']');
    } else {
      sb.append(']');
    }
    return sb.toString();
  }
}
