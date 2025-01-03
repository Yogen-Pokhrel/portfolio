package com.portfolio.apigateway.routes;


import com.portfolio.core.config.MicroserviceAddressResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {

    @Autowired
    MicroserviceAddressResolver addressResolver;

    @Bean
    public RouterFunction<ServerResponse> accountServiceRoute() {
        return route("account_service")
                .route(RequestPredicates
                        .path("/account/*")
                        .or(RequestPredicates.path("/users/*"))
                        .or(RequestPredicates.path("/roles/*"))
                        ,HandlerFunctions.http(addressResolver.getAccountUrl()))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("productServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(addRequestHeader("X-GATEWAY-VALIDATED", "true"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> accountServiceSwaggerRoute() {
        return route("account_service_swagger")
                .route(RequestPredicates.path("/openapi/account-service/v3/api-docs"), HandlerFunctions.http(addressResolver.getAccountUrl()))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("productServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> cmsServiceRoute() {
        return route("cms_service")
                .route(RequestPredicates
                                .path("/cms/*")
                                .or(RequestPredicates.path("/v1/education*"))
                                .or(RequestPredicates.path("/v1/experience*"))
                        ,HandlerFunctions.http(addressResolver.getCmsUrl()))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("productServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> cmsServiceSwaggerRoute() {
        return route("cms_service_swagger")
                .route(RequestPredicates.path("/openapi/cms-service/v3/api-docs"), HandlerFunctions.http(addressResolver.getCmsUrl()))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("productServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route("fallbackRoute")
                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Service Unavailable, please try again later"))
                .build();
    }
}
