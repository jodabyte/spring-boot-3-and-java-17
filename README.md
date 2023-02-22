# Spring Boot 3 and Java 17
Spring Boot 3 and Java 17 powered things. 

## Dependencies
- Java 17
- Maven
- Docker

## Getting Startet
### Build Docker Image
`spring-boot:build-image`
### Deploy locally
#### Create and start services
`docker compose -f .\docker\docker-compose.yaml up`
#### Stop services and remove containers, networks
`docker compose -f .\docker\docker-compose.yaml down`

## Features
- Uses spring-boot-starter dependencies for developing.
- [API Endpoints](https://learn.microsoft.com/en-us/azure/architecture/best-practices/api-design):
    - OpenAPI [json](http://localhost:8080/v3/api-docs) [yaml](http://localhost:8080/v3/api-docs.yaml)
    - [Swagger UI](http://localhost:8080/swagger-ui.html)
    - [Spring Actuator](http://localhost:8080/actuator)
- Asset management 