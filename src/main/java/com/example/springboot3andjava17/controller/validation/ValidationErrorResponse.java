package com.example.springboot3andjava17.controller.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    private final List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return violations;
    }
}
