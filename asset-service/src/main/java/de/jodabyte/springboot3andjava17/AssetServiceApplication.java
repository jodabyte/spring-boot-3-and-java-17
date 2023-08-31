package de.jodabyte.springboot3andjava17;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(title = "Asset Service API", version = "1.0"),
    servers =
        @Server(
            url = "http://{host}:{port}",
            variables = {
              @ServerVariable(
                  name = "host",
                  defaultValue = "localhost",
                  allowableValues = {"localhost", "asset-service"}),
              @ServerVariable(name = "port", defaultValue = "8081")
            }))
@SpringBootApplication
public class AssetServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AssetServiceApplication.class, args);
  }
}
