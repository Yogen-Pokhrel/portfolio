package com.portfolio.account.configuration;

import com.portfolio.core.config.BasePaginationConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class PaginationConfig extends BasePaginationConfig {
}
