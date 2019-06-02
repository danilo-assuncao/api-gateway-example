package com.dassuncao.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/inactive")
                        .filters(it -> it.secureHeaders()
                                .addRequestHeader("X-RequestOrigin", "account-card")
                                .addResponseHeader("X-RequestOrigin", "account-card"))
                        .uri("http://httpbin.org:80"))
                .build();
    }
}