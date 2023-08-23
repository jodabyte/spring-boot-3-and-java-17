package de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.mapper;

import com.fasterxml.jackson.annotation.*;
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
