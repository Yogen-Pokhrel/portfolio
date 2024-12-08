package com.portfolio.auth.security;

import com.portfolio.auth.authModule.AuthDetails;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

@Slf4j
public class AuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt)
    {
        log.debug("Injecting AuthDetails");
        return new AuthDetails(jwt);
    }
}