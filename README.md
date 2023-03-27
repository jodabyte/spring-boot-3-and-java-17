# Spring Boot 3 and Java 17
Spring Boot 3 and Java 17 powered things. 
- Back-end
    - Spring-boot-starter-web
    - MongoDB
    - OpenAPI
    - Dockerimage generation with spring-boot-maven-plugin
    - spring-boot-starter-validation for input validation
    - Code generation
        - projectlombok
        - mapstruct
- Ops
    - Portainer as production environment
    - Monitoring using spring boot actuator
- QA
    - Unittests, IT
    - Testcontainers for back-end dependencies
    - Datafaker for testdata generation
    - Static code analysis / coding standard
        - google-java-format
        - Checkstyle
        - SonarLint 
        - SonarQube 
# Dependencies
- Java 17
- Maven
- VSCode
    - [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
    - [Spring Boot Extension Pack](https://marketplace.visualstudio.com/items?itemName=vmware.vscode-boot-dev-pack)
    - [Docker](https://marketplace.visualstudio.com/items?itemName=ms-azuretools.vscode-docker)
    - [Dependency Analytics](https://marketplace.visualstudio.com/items?itemName=redhat.fabric8-analytics)
    - [SonarLint](https://marketplace.visualstudio.com/items?itemName=SonarSource.sonarlint-vscode)
- Docker

# Getting Startet
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
`mvn checkstyle:check` or `mvn checkstyle:checkstyle` to run checkstyle analysis  
`mvn clean verify sonar:sonar` to run sonarqube analysis  
 
# [API Endpoints](https://learn.microsoft.com/en-us/azure/architecture/best-practices/api-design)
- OpenAPI [json](http://localhost:8080/v3/api-docs) [yaml](http://localhost:8080/v3/api-docs.yaml)
- [Swagger UI](http://localhost:8080/swagger-ui.html)
- [Spring Actuator](http://localhost:8080/actuator)
