package com.portfolio.apigateway.helpers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ServiceResolver {

    @Value("${microservice.portfolio-account-url}")
    private String portfolioAccountUrl;

    @Value("${microservice.portfolio-cms-url}")
    private String portfolioCmsUrl;

    @Value("${microservice.portfolio-gateway-url}")
    private String portfolioGatewayUrl;

    public String getServiceUrl(String serviceName) {
        return switch (serviceName) {
            case "account-service" -> portfolioAccountUrl;
            case "cms-service" -> portfolioCmsUrl;
            case "gateway-service" -> portfolioGatewayUrl;
            default -> portfolioGatewayUrl;
        };
    }
}
