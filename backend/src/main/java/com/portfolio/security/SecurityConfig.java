package com.portfolio.security;

import com.portfolio.auth.filter.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    @Autowired
    private JWTFilter jwtFilter;

    public SecurityConfig() {
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("**", "swagger-ui/**").permitAll()
                        .requestMatchers("**", "**").permitAll()
                                .requestMatchers("/", "/**").permitAll()
//                        .requestMatchers("/", "sendEmail").permitAll()
//                        .requestMatchers("/", "api/v1/auth/**").permitAll()
//                        // Allow only specific public endpoints, not all GET requests
//                        .requestMatchers(HttpMethod.GET, "/api/v1/public/**").permitAll()
//                        // Secure other API endpoints, including those with @PreAuthorize
//                        .requestMatchers("/api/v1/**").hasRole("CUSTOMER")
//                        .requestMatchers("/api/v1/**").authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable); // Disable CSRF protection

        // Ensure JWTFilter is added before the authentication filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(PermissionEvaluator permissionEvaluator) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }

}