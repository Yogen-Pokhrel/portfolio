package com.portfolio.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;

public class BaseOpenApiConfig {
    static {
        SpringDocUtils.getConfig().replaceWithClass(Page.class, PagedModel.class);
    }
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
    public OpenAPI customOpenAPI(  @Value("${server.port:8080}") String serverPort,
                                   @Value("${server.servlet.context-path:/}") String contextPath,
                                   @Value("${server.address:localhost}") String serverAddress,
                                   @Value("${microservice.portfolio-gateway-url:none}") String gatewayUrl
                                   ) {
        HashMap<String, Object> extension = new HashMap<>();
        extension.put("microservice", microserviceName);

        String defaultHost = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(serverAddress)
                .port(serverPort)
                .path(contextPath)
                .toUriString();

        OpenAPI openAPI = new OpenAPI()
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
        if (gatewayUrl != null && !gatewayUrl.isEmpty() && !gatewayUrl.equals("none")) {
            openAPI.addServersItem(new Server().url(gatewayUrl));
        }
        openAPI.addServersItem(new Server().url(defaultHost));
        return openAPI;
    }
}
