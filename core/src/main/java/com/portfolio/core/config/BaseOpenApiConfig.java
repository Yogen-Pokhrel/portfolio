package com.portfolio.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;

public class OpenApiConfigCommon {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Portfolio API")
                                .description("This API is used to create a portfolio for multiple users")
                                .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("Keycloak"))
                .components(new Components()
                        .addSecuritySchemes("Keycloak", new SecurityScheme()
                            .type(SecurityScheme.Type.OPENIDCONNECT).openIdConnectUrl("http://127.0.0.1:8081/realms/Portfolio/.well-known/openid-configuration")
                            .scheme("bearer")
                            .in(SecurityScheme.In.HEADER)));
    }
}
