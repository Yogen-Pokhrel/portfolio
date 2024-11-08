package com.portfolio.auth.util;

import com.portfolio.auth.AuthDetails;
import com.portfolio.auth.AuthService;
import com.portfolio.auth.JwtClaim;
import com.portfolio.common.exception.JWTClaimException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JWTUtil {

    @Autowired
    private AuthService authService;

    @Value("${jwt.secretKey}")
    private String secret;

    @Value("${jwt.refreshKey}")
    private String refresh_token_secret;

    @Value("${jwt.expiry_time}")
    private long expiration;

    @Value("${jwt.refresh_expiry_time}")
    private long refExpiration;

    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    public String generateToken(AuthDetails details) {
        Map<String, Object> claims = new HashMap<>();
        String loggedRole = details.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList().getFirst();
        claims.put(JwtClaim.ROLES.getKey(), loggedRole);
        claims.put(JwtClaim.USER_ID.getKey(), details.getId());
        claims.put(JwtClaim.EMAIL.getKey(), details.getEmail());

        return this.generateAccessTokens(claims, details.getUsername());
    }

    private String generateAccessTokens(Map<String, Object> claims, String subject) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String generateRefreshToken(String subject) {
        return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refExpiration))
                .signWith(SignatureAlgorithm.HS512, refresh_token_secret).compact();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) throws Exception{
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            throw new JWTClaimException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public AuthDetails extractDetails(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        // Extract user information from claims
        Integer userId = claims.get(JwtClaim.USER_ID.getKey(), Integer.class);
        String firstName = claims.get(JwtClaim.FIRST_NAME.getKey(), String.class);
        String lastName = claims.get(JwtClaim.LAST_NAME.getKey(), String.class);
        String email = claims.getSubject();
        List<String> roles;

        try{
            roles = claims.get(JwtClaim.ROLES.getKey(), List.class);
        }catch (Exception e){
            logger.error(e.getMessage());
            roles = new ArrayList<>();
        }

        return AuthDetails.builder()
                .id(userId)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .roles(roles)
                .build();
    }
}

