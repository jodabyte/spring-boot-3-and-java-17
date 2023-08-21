# Spring Boot 3 and Java 17
Spring Boot 3 and Java 17 powered things. 
- Back-end
    - Spring-boot-starter
    - MongoDB, Eclipse Mosquitto
    - OpenAPI, OpenApi Generator, Swagger
    - Dockerimage generation with spring-boot-maven-plugin
    - Docker compose for dependency management
    - spring-boot-starter-validation for input validation
    - Code generation
        - projectlombok
    - Maven
      - [Maven CI Friendly Versions](https://maven.apache.org/maven-ci-friendly.html)
      - Profiles to manage build
      - [In Code replacement / Filtering](https://maven.apache.org/plugins/maven-resources-plugin/examples/filter.html)
- Ops
    - Portainer as production environment
    - Monitoring using spring boot actuator
        - Tracing using Micrometer
            - Format: OpenTelemetry
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
- mqtt.username
- mqtt.password
## Run OpenAPI Generator
- Run configuration: openapi asset-service
## Build Docker Image
`spring-boot:build-image`
## Deploy locally
### Docker Compose
Create and start services: `docker compose -f .\docker\docker-compose.yaml up`  
Stop services and remove containers, networks: `docker compose -f .\docker\docker-compose.yaml down`
### Portainer
Use the docker compose file to create a stack in portainer.
## Tests
### Unit Tests
`mvn test`
### Integration tests
`mvn verify`
### Static code analysis / coding standard
`mvn spotless:apply spotless:check` to format code  
`mvn checkstyle:check` to run checkstyle analysis  
`mvn clean verify sonar:sonar` to run sonarqube analysis  
## Maven
### Checking for new property-linked updates 
`mvn versions:display-property-updates`
### Checking for new dependency updates
`mvn versions:display-dependency-updates`
 
# [API Endpoints](https://learn.microsoft.com/en-us/azure/architecture/best-practices/api-design)
- OpenAPI [json](http://localhost:8080/v3/api-docs) [yaml](http://localhost:8080/v3/api-docs.yaml)
- [Swagger UI](http://localhost:8080/swagger-ui.html)
- [Spring Actuator](http://localhost:8080/actuator)
- [Portainer](https://localhost:9443)
- [SonarQube](http://localhost:9000)
