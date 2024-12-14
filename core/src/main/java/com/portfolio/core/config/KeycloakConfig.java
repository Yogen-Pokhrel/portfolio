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

    public String clientId;
    public String clientSecret;
    public String realm;
    public String serverUrl;
}
