package com.example.springboot3andjava17.common.validation;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path.Node;

/**
 * Handles validation exceptions and maps them into
 * {@link ValidationErrorResponse}.
 * 
 * How to extract datails from ConstraintViolationException:
 * https://stackoverflow.com/questions/50232456/how-to-find-out-whether-a-constraintviolation-is-from-a-json-property-or-from-a
 */
@ControllerAdvice
public class ValidationExceptionHandler {

    /**
     * A constraint violation during validation occured.
     * 
     * @param e contain constraint validation errors
     * @return A response with the failed validation details.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();

        List<Violation> violations = e.getConstraintViolations().stream().map(violation -> {
            Spliterator<Node> spliterator = violation.getPropertyPath().spliterator();
            Node leafNode = StreamSupport.stream(spliterator, false).reduce((a, b) -> b).get();
            return new Violation(leafNode.toString(), violation.getMessage());
        }).toList();

        error.getViolations().addAll(violations);
        return error;
    }

    /**
     * A request property validation failed.
     * 
     * @param e contain request property validation errors
     * @return A response with the failed validation details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();

        List<Violation> violations = e.getBindingResult().getFieldErrors().stream().map(fieldError -> {
            return new Violation(fieldError.getField(), fieldError.getDefaultMessage());
        }).toList();

        error.getViolations().addAll(violations);
        return error;
    }
}
