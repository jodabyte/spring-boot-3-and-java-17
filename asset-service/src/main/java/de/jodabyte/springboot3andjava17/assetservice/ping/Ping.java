package de.jodabyte.springboot3andjava17.assetservice.ping;

import lombok.Value;

@Value
public class Ping {

  private String serviceName;
  private String serviceVersion;
  private String timestamp;
}
