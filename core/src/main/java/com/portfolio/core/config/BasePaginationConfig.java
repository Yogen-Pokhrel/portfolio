package com.portfolio.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class BasePaginationConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(pageHandlerMethodArgumentResolver());
    }

    @Bean
    public PageableHandlerMethodArgumentResolver pageHandlerMethodArgumentResolver() {
        return customPageableConverter();
    }

    @Bean
    public PageableHandlerMethodArgumentResolver customPageableConverter() {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();

        // Set a fallback page size and default to the first page if no page is provided.
        resolver.setFallbackPageable(PageRequest.of(0, 25)); // Default to page 0, size 20

        // Set a maximum page size limit (optional).
        resolver.setMaxPageSize(500);
        return resolver;
    }
}
