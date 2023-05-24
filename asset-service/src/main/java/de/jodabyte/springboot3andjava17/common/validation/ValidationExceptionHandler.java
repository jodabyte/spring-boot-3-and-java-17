package de.jodabyte.springboot3andjava17.common.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path.Node;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handles validation exceptions and maps them into {@link ValidationErrorResponse}.
 *
 * <p>How to extract datails from ConstraintViolationException:
 *
 * @see <a href= "https://stackoverflow.com/q/50232456">
 *     how-to-find-out-whether-a-constraintviolation-is-from-a-json-property-or-from-a</a>
 */
@Slf4j
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
    var validationErrorResponse = new ValidationErrorResponse();
    List<Violation> violations =
        e.getConstraintViolations().stream().map(this::mapToViolation).toList();
    validationErrorResponse.addViolations(violations);
    log.info("Constraint violation parsed={}", validationErrorResponse.getViolations());
    return validationErrorResponse;
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
    var validationErrorResponse = new ValidationErrorResponse();
    List<Violation> violations =
        e.getBindingResult().getFieldErrors().stream().map(this::mapToViolation).toList();
    validationErrorResponse.addViolations(violations);
    log.info("Method argument violation parsed={}", validationErrorResponse.getViolations());
    return validationErrorResponse;
  }

  private Violation mapToViolation(ConstraintViolation<?> violation) {
    Spliterator<Node> spliterator = violation.getPropertyPath().spliterator();
    Node leafNode = StreamSupport.stream(spliterator, false).reduce((a, b) -> b).orElseThrow();
    return new Violation(leafNode.toString(), violation.getMessage());
  }

  private Violation mapToViolation(FieldError fieldError) {
    return new Violation(fieldError.getField(), fieldError.getDefaultMessage());
  }
}
