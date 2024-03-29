package de.jodabyte.springboot3andjava17.test.container;

public final class TestContainerContract {
  public static final String MONGODB_IMAGE = "mongodb/mongodb-community-server:6.0.8-ubi8";
  public static final String MONGODB_NAME = "mongo";
  public static final String MOSQUITTO_NAME = "mosquitto";
  public static final int MOSQUITTO_PORT = 1883;
  public static final String ASSET_SERVICE_NAME = "asset-service";
  public static final int ASSET_SERVICE_PORT = 8081;
  public static final String COMPOSE_FILE = "docker-compose.yml";
}
