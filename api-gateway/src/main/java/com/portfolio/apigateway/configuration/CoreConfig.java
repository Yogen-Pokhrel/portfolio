package com.portfolio.apigateway.configuration;

import com.portfolio.core.config.KeycloakConfig;
import com.portfolio.core.config.MicroserviceAddressResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(KeycloakConfig.class)
@Import(MicroserviceAddressResolver.class)
public class CoreConfig {
}
