package com.portfolio.apigateway.configuration;

import com.portfolio.core.config.KeycloakConfig;
import com.portfolio.core.helpers.FileUploaderService;
import com.portfolio.core.helpers.ListMapper;
import com.portfolio.core.helpers.LocalFileUploaderServiceImpl;
import com.portfolio.core.helpers.S3FileUploaderServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KeycloakConfig.class)
public class CoreConfig {
}
