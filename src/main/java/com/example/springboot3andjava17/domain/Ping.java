package com.example.springboot3andjava17.domain;

import lombok.Value;

@Value
public class Ping {

    private String serviceName;
    private String serviceVersion;
    private String timestamp;
}
