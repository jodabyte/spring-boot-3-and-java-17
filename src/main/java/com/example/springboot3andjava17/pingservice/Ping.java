package com.example.springboot3andjava17.pingservice;

import io.swagger.v3.oas.annotations.media.Schema;

public class Ping {

    @Schema(description = "Maven project.name of the service.", example = "My App Name")
    private final String serviceName;
    @Schema(description = "Maven project.version of the service.", example = "1.2.3-SNAPSHOT")
    private final String serviceVersion;
    @Schema(description = "Current UTC date time.", example = "25. Januar 2023 um 16:11:21 Z")
    private final String timestamp;

    public Ping(String serviceName, String serviceVersion, String timestamp) {
        this.serviceName = serviceName;
        this.serviceVersion = serviceVersion;
        this.timestamp = timestamp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
