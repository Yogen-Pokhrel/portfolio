package com.portfolio.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.auth.AuthDetails;
import com.portfolio.auth.AuthService;
import com.portfolio.auth.util.JWTUtil;
import com.portfolio.common.ApiResponse;
import com.portfolio.common.exception.JWTClaimException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final AuthService authService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public JWTFilter(JWTUtil jwtUtil, AuthService authService) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        log.debug("JWTFilter.extractTokenFromRequest");
        String authHeader = request.getHeader("Authorization");
        if(authHeader== null) return null;
        if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        log.error("No token found, please ensure that the token is valid and appended with 'Bearer ' keyword");
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
            log.error("JWT error: {}", jwtException.getMessage());
            ApiResponse<String> apiResponse = ApiResponse.error(jwtException.getMessage());

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status code
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse)); // Send custom error response
            return;
        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage());
            ApiResponse<String> apiResponse = ApiResponse.error("Invalid or expired token");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status code
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse)); // Send custom error response
            return;
        }

        filterChain.doFilter(request, response);
    }
}
