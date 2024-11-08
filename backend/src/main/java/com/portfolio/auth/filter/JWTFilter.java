package com.portfolio.auth.filter;

import com.portfolio.auth.AuthDetails;
import com.portfolio.auth.AuthService;
import com.portfolio.auth.util.JWTUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    @Autowired
    public JWTFilter(JWTUtil jwtUtil, AuthService authService) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader== null) return null;
        if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new JwtException("No token found, please ensure that the token is valid and appended with 'Bearer ' keyword");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromRequest(request);
        try {
            if (token != null && jwtUtil.isTokenValid(token)) {
                AuthDetails authenticatedUser =jwtUtil.extractDetails(token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException jwtException) {
            logger.error("JWT error: {}", jwtException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return;
        } catch (Exception e) {
            logger.error("Authentication error: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request, response);
    }
}
