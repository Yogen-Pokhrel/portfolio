package com.portfolio.auth.configuration;

import com.portfolio.core.common.ApiResponse;
import com.portfolio.core.exception.BaseExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        log.debug("Authentication failed: {}" ,ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication failed: " + ex.getMessage()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthorizationDeniedException ex) {
        log.debug("Authorization failed: {}" ,ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error("Forbidden resource: " + ex.getMessage()));
    }
}
