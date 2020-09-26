# Reactive API Gateway Sample
Reactive API Gateway using spring-cloud-gateway with Spring Boot dependencies in version 2.3.x.

## Configuration
General project configurations such as Circuitbreaker and among many others possible.
- see example: [configuration class](src/main/java/com/dassuncao/reactive/api/gateway/configuration/Resilience4jConfiguration.java)

## Filters
Filters can intercept a request from the gateway allowing you to manage this call before or after being redirected to the destination api. Examples of filters would be, for example, security filters, log and within several others.
- see example: [configuration class](src/main/java/com/dassuncao/reactive/api/gateway/filters/LogFilter.java)

## Route Configuration Pattern
This gateway implementation is configuration-oriented allowing management of all routes with the application running using the config-server.
- see configuration example: [configuration file](src/main/resources/application.yml)