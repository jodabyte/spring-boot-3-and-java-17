package com.example.springboot3andjava17.ping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Ping", description = "Service availability and info")
public class PingController {

  private String serviceName;

  private String serviceVersion;

  public PingController(
      @Value("${application.name:}") String serviceName,
      @Value("${application.version:}") String serviceVersion) {
    this.serviceName = serviceName;
    this.serviceVersion = serviceVersion;
  }

  @GetMapping("/ping")
  @Operation(
      summary = "Provides current service state",
      responses =
          @ApiResponse(
              responseCode = "200",
              content =
                  @Content(
                      mediaType = MediaType.APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = Ping.class))))
  public Ping ping() {
    return new Ping(
        serviceName,
        serviceVersion,
        ZonedDateTime.now(ZoneOffset.UTC)
            .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)));
  }
}
