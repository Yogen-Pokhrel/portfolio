package com.portfolio.account.security;

import com.portfolio.core.security.BaseSecurityConfigNoAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends BaseSecurityConfigNoAuth {
}