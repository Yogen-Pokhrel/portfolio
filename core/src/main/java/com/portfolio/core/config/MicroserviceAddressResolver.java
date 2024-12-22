package com.portfolio.core.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * This service is used to resolve microservice address
 */
@Component
@Getter
@Slf4j
public class MicroserviceAddressResolver {

    @Value("${microservice.portfolio-account-url:none}")
    private URI accountUrl;

    @Value("${microservice.portfolio-cms-url:none}")
    private URI cmsUrl;

    @Value("${microservice.portfolio-gateway-url:none}")
    private URI gatewayUrl;

    public URI getServiceUrl(Microservices serviceName) {
        URI serviceUrl = switch (serviceName) {
            case account -> accountUrl;
            case cms -> cmsUrl;
            default -> gatewayUrl;
        };
        if (serviceUrl.toString().equals("none")) {
            log.error("No service url found for {}, no such field found in application.properties or application.yml file", serviceName);
            //Not sure at this point if error should be thrown if URL not provided
            //throw new RuntimeException("No service url found for " + serviceName);
        }
        return serviceUrl;
    }
}
