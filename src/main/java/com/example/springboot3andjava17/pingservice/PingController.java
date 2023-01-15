package com.example.springboot3andjava17.pingservice;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @Value("${application.name}")
    private String serviceName;
    @Value("${application.version}")
    private String serviceVersion;

    @GetMapping("/ping")
    public Ping ping() {
        return new Ping(serviceName, serviceVersion,
                ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)));
    }
}
