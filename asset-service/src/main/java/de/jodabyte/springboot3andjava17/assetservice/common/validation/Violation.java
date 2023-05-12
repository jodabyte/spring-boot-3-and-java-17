package de.jodabyte.springboot3andjava17.assetservice.common.validation;

import lombok.Value;

@Value
public class Violation {

  private String name;
  private String message;
}
