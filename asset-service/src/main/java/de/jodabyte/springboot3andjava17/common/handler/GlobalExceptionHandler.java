package de.jodabyte.springboot3andjava17.common.handler;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {NoSuchElementException.class})
  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not Found")
  public void noSuchElementException() {
    // The error response is generated through the @ResponseStatus annotation.
  }
}
