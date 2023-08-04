package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "attribute",
  "cluster",
  "maximum_report_interval",
  "minimum_report_interval",
  "reportable_change"
})
@Generated("jsonschema2pojo")
public class ConfiguredReporting {

  @JsonProperty("attribute")
  private String attribute;

  @JsonProperty("cluster")
  private String cluster;

  @JsonProperty("maximum_report_interval")
  private int maximumReportInterval;

  @JsonProperty("minimum_report_interval")
  private int minimumReportInterval;

  @JsonProperty("reportable_change")
  private int reportableChange;

  @JsonProperty("attribute")
  public String getAttribute() {
    return attribute;
  }

  @JsonProperty("attribute")
  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }

  @JsonProperty("cluster")
  public String getCluster() {
    return cluster;
  }

  @JsonProperty("cluster")
  public void setCluster(String cluster) {
    this.cluster = cluster;
  }

  @JsonProperty("maximum_report_interval")
  public int getMaximumReportInterval() {
    return maximumReportInterval;
  }

  @JsonProperty("maximum_report_interval")
  public void setMaximumReportInterval(int maximumReportInterval) {
    this.maximumReportInterval = maximumReportInterval;
  }

  @JsonProperty("minimum_report_interval")
  public int getMinimumReportInterval() {
    return minimumReportInterval;
  }

  @JsonProperty("minimum_report_interval")
  public void setMinimumReportInterval(int minimumReportInterval) {
    this.minimumReportInterval = minimumReportInterval;
  }

  @JsonProperty("reportable_change")
  public int getReportableChange() {
    return reportableChange;
  }

  @JsonProperty("reportable_change")
  public void setReportableChange(int reportableChange) {
    this.reportableChange = reportableChange;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(ConfiguredReporting.class.getName())
        .append('@')
        .append(Integer.toHexString(System.identityHashCode(this)))
        .append('[');
    sb.append("attribute");
    sb.append('=');
    sb.append(((this.attribute == null) ? "<null>" : this.attribute));
    sb.append(',');
    sb.append("cluster");
    sb.append('=');
    sb.append(((this.cluster == null) ? "<null>" : this.cluster));
    sb.append(',');
    sb.append("maximumReportInterval");
    sb.append('=');
    sb.append(this.maximumReportInterval);
    sb.append(',');
    sb.append("minimumReportInterval");
    sb.append('=');
    sb.append(this.minimumReportInterval);
    sb.append(',');
    sb.append("reportableChange");
    sb.append('=');
    sb.append(this.reportableChange);
    sb.append(',');
    if (sb.charAt((sb.length() - 1)) == ',') {
      sb.setCharAt((sb.length() - 1), ']');
    } else {
      sb.append(']');
    }
    return sb.toString();
  }
}
