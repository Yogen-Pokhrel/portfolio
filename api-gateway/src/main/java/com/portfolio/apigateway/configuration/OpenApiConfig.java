package com.portfolio.apigateway.configuration;

import com.portfolio.core.config.BaseOpenApiConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig extends BaseOpenApiConfig {

    OpenApiConfig(){
        super("Portfolio Gateway API",
                "This docs list all the available endpoints in Portfolio CMS",
                "com.portfolio.cms",
                "1.0"
        );
    }
}