package com.portfolio.core.security;

import com.portfolio.core.config.KeycloakConfig;
import com.portfolio.core.config.MicroserviceAddressResolver;
import com.portfolio.core.config.Microservices;
import com.portfolio.core.config.PolicyEnforcerConfigProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.net.URI;

/**
 * @deprecated will be removed in next update
 *
 */
@Slf4j
@Deprecated(forRemoval = true)
@Component
public class BaseSecurityConfigBackup {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwkSetUri;

    @Value("${spring.application.skip-auth-converter:false}")
    Boolean skipAuthConverter;

    @Autowired
    KeycloakConfig keycloakConfig;

    @Autowired
    MicroserviceAddressResolver microserviceAddressResolver;

    ServletPolicyEnforcerFilter servletPolicyEnforcerFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
                .sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        if(skipAuthConverter) {
            log.debug("Skipping authentication converter");
            http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        }else{
            log.debug("Authentication converter added for the service");
            http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new AuthenticationConverter())));
        }
        http.addFilterAfter((request, response, chain) -> {
            try{
                if (request instanceof HttpServletRequest httpRequest) {

                    /*
                     * Apply the Policy Enforcer Filter if the request is not coming from api-gateway or if the request type is not OPTIONS
                     */
                    String isValidatedByGateway = httpRequest.getHeader("X-GATEWAY-VALIDATED");
                    String forwardedHost = httpRequest.getHeader("x-forwarded-host");
                    boolean isFromMicroservice = isRequestFromMicroservice(forwardedHost);
                    if (!httpRequest.getMethod().equals(HttpMethod.OPTIONS.name()) && !(isValidatedByGateway != null && isValidatedByGateway.equals("true") && isFromMicroservice)) {
                        log.debug("Enforcing keycloak authentication and authorization");
                        if(servletPolicyEnforcerFilter == null){
                            servletPolicyEnforcerFilter = createPolicyEnforcerFilter();
                        }
                        servletPolicyEnforcerFilter.doFilter(request, response, chain);
                        return;
                    }
                }
            }catch (Exception error){
                log.error("Error while enabling keycloak authentication {}", error.getMessage());
            }
            log.debug("Default filter chain instantiated, keycloak auth skipped");
            chain.doFilter(request, response);
        }, BearerTokenAuthenticationFilter.class);

        return http.build();
    }

    private ServletPolicyEnforcerFilter createPolicyEnforcerFilter() {
        return new ServletPolicyEnforcerFilter(httpRequest -> PolicyEnforcerConfigProvider.getPolicyEnforcerConfig(keycloakConfig));
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    private boolean isRequestFromMicroservice(String remoteHost) {
        try {
            for (Microservices service : Microservices.values()) {
                URI serviceUri = microserviceAddressResolver.getServiceUrl(service);
                String url = serviceUri.getHost() + ":" + serviceUri.getPort();
                if (url.equals(remoteHost)) {
                    log.debug("Request is coming from microservice: {}", service);
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while resolving microservice address: {}", e.getMessage());
        }
        return false;
    }
}