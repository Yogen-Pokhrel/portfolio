package com.portfolio.auth.authModule;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ToString
@Getter
@Slf4j
public class AuthDetails extends JwtAuthenticationToken {
    private final UUID userId;
    private final String email;
    private final List<String> roles;
    private final String firstName;
    private final String lastName;

    @SuppressWarnings("unchecked")
    public AuthDetails(Jwt jwt) {
        super(jwt, extractAuthorities(jwt)); // Call parent constructor with authorities
        this.userId = UUID.fromString(jwt.getSubject());
        this.email = jwt.getClaimAsString("email");
        this.firstName = jwt.getClaimAsString("given_name");
        this.lastName = jwt.getClaimAsString("family_name");
        this.roles = jwt.getClaimAsMap("realm_access") != null
                ? (List<String>) jwt.getClaimAsMap("realm_access").getOrDefault("roles", new ArrayList<>())
                : new ArrayList<>();
        log.info("Auth details injected");
    }

    public static Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        return jwt.getClaimAsStringList("realm_access").stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

