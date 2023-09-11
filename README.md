# Spring Boot 3 and Java 17
Spring Boot 3 and Java 17 powered things. 
- Back-end
    - Spring-boot-starter
    - MongoDB, Eclipse Mosquitto, Kafka
    - OpenAPI, OpenApi Generator, Swagger
    - Dockerimage generation with spring-boot-maven-plugin
    - Docker compose for dependency management
    - spring-boot-starter-validation for input validation
    - Json polymorphic types
    - Code generation
        - projectlombok
        - mapstruct
    - Maven
      - [Maven CI Friendly Versions](https://maven.apache.org/maven-ci-friendly.html)
      - Profiles to manage build
      - [In Code replacement / Filtering](https://maven.apache.org/plugins/maven-resources-plugin/examples/filter.html)
- Ops
    - Portainer as production environment
    - Monitoring using spring boot actuator
        - Tracing using Micrometer
            - Format: OpenTelemetry
            - Export: Zipkin
    - Profiles:
        - local deployment
        - docker deployment
- QA
    - Unittests, IT
    - Testcontainers for back-end dependencies
    - Datafaker for testdata generation
    - Static code analysis / coding standard
        - [google-java-format](https://github.com/diffplug/spotless) 
        - Checkstyle
        - SonarLint 
        - SonarQube 
# Dependencies
- Java 17
- Maven
- Intellij
- Docker

# Getting Startet
## Set credentials
Create the file credentials.properties in project directory with the following properties:
- mqtt.host
- mqtt.username
- mqtt.password
## Build Docker Images
`mvn spring-boot:build-image`
- asset service
- integration service
- data service
## Deploy locally
### Docker Compose
Create and start services: `docker-compose -f .\docker\docker-compose.yaml up -d`  
Stop services and remove containers, networks: `docker-compose -f .\docker\docker-compose.yaml down`
### Portainer
Use the docker compose file to create a stack in portainer.
# Run OpenAPI Generator
- Run configuration: openapi asset-service
# Tests
## Unit Tests
`mvn test`
## Integration tests
`mvn verify`
## Static code analysis / coding standard
`mvn spotless:apply spotless:check` to format code  
`mvn checkstyle:check` to run checkstyle analysis  
`mvn clean verify sonar:sonar` to run sonarqube analysis  
# Maven
## Checking for new property-linked updates 
`mvn versions:display-property-updates`
## Checking for new dependency updates
`mvn versions:display-dependency-updates`
 
# [API Endpoints](https://learn.microsoft.com/en-us/azure/architecture/best-practices/api-design)
- asset service 
  - OpenAPI: [Swagger UI](http://localhost:8081/swagger-ui.html), [json](http://localhost:8081/v3/api-docs), [yaml](http://localhost:8081/v3/api-docs.yaml)
  - Monitoring: [Spring Actuator](http://localhost:8081/actuator)
  - Tracing: [Zipkin](http://localhost:9411)
- [Portainer](https://localhost:9443)
- [SonarQube](http://localhost:9000)
