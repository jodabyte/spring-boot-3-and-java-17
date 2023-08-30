package de.jodabyte.springboot3andjava17.core.mqtt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"humidity", "linkquality", "pm25", "temperature", "voc_index"})
@Generated("jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Vindstyrka {

  @JsonProperty("humidity")
  public int humidity;

  @JsonProperty("linkquality")
  public int linkquality;

  @JsonProperty("pm25")
  public int pm25;

  @JsonProperty("temperature")
  public int temperature;

  @JsonProperty("voc_index")
  public int vocIndex;
}
