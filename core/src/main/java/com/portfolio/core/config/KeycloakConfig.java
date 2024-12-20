package com.portfolio.core.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakConfig {

    private String clientId;
    private String clientSecret;
    private String realm;
    private String serverUrl;
}
