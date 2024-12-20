package com.portfolio.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

public class BaseOpenApiConfig {

    private final String apiTitle;
    private final String apiDescription;
    private final String microserviceName;
    private final String apiVersion;

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    public BaseOpenApiConfig() {
        this.apiTitle = "Portfolio API";
        this.apiDescription = "This API is used to create a portfolio for multiple users";
        this.microserviceName = "com.portfolio";
        this.apiVersion = "1.0";
    }

    public BaseOpenApiConfig(String apiTitle, String apiDescription, String microserviceName, String apiVersion) {
        this.apiTitle = apiTitle;
        this.apiDescription = apiDescription;
        this.microserviceName = microserviceName;
        this.apiVersion = apiVersion;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        HashMap<String, Object> extension = new HashMap<>();
        extension.put("microservice", microserviceName);
        return new OpenAPI()
                .info(new Info().title(apiTitle)
                                .description(apiDescription)
                                .version(apiVersion)
                                .extensions(extension)
                )
                .addSecurityItem(new SecurityRequirement().addList("Keycloak"))
                .components(new Components()
                        .addSecuritySchemes("Keycloak", new SecurityScheme()
                            .type(SecurityScheme.Type.OPENIDCONNECT).openIdConnectUrl(keycloakServerUrl+"/realms/Portfolio/.well-known/openid-configuration")
                            .scheme("bearer")
                            .in(SecurityScheme.In.HEADER)));
    }
}
