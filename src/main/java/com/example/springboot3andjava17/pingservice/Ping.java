package com.example.springboot3andjava17.pingservice;

public class Ping {

    private final String serviceName;
    private final String serviceVersion;
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
