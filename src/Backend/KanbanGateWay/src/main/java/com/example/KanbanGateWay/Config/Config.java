package com.example.KanbanGateWay.Config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/users/**")
                        .uri("lb://user-login-service"))

                .route(p -> p
                        .path("/apii/users/**")
                        .uri("lb://user-kanban-service"))

                .build();
    }
}
