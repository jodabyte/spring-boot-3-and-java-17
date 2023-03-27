package com.example.springboot3andjava17.common.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

  private final List<Violation> violations = new ArrayList<>();

  public void addViolations(List<Violation> violations) {
    this.violations.addAll(violations);
  }

  public List<Violation> getViolations() {
    return List.copyOf(violations);
  }
}
