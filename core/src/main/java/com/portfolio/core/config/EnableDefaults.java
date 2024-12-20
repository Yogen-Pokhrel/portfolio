package com.portfolio.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/***
 * Use Defaults only if you want to enable default beans of ModelMapper, Pagination and Keycloak Configuration
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ModelMapperConfig.class, BasePaginationConfig.class, FileServiceConfig.class})
@EnableConfigurationProperties(KeycloakConfig.class)
public @interface EnableDefaults {
}