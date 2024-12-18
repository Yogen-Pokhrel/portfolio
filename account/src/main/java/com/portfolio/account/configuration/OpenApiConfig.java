package com.portfolio.account.configuration;

import com.portfolio.core.config.BaseOpenApiConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig extends BaseOpenApiConfig {
    OpenApiConfig(){
        super("Portfolio Accounts API",
                "This docs list all the available endpoints in Portfolio account",
                "com.portfolio.account",
                "1.0"
                );
    }
}
