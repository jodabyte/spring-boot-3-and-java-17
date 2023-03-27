package com.example.springboot3andjava17.common.validation;

import lombok.Value;

@Value
public class Violation {

  private String name;
  private String message;
}
