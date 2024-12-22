package com.portfolio.core.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.portfolio.core.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.portfolio.core.util.Utils.extractSimpleClassName;

@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication failed: {}" ,ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication failed: " + ex.getMessage()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthorizationDeniedException ex) {
        log.error("Authorization failed: {}" ,ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error("Forbidden resource: " + ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException: {}" ,ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Internal server error, please contact admin team to resolve any issues, message: " + ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation failed: {}" ,ex.getMessage());
        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    if (errors.containsKey(error.getField())) {
                        errors.get(error.getField()).add(error.getDefaultMessage());
                    } else {
                        errors.put(error.getField(), new ArrayList<String>() {{
                            add(error.getDefaultMessage());
                        }});
                    }
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("Bad Request", errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleJsonParseException(HttpMessageNotReadableException ex) {
        log.error("JSON Parsing error: {}", ex.getMessage());

        Map<String, List<String>> errors = new HashMap<>();
        if (ex.getCause() instanceof JsonMappingException jsonMappingException) {
            String simplifiedMessage = extractSimpleClassName(jsonMappingException.getOriginalMessage());
            for(JsonMappingException.Reference ref : jsonMappingException.getPath()) {
                List<String> errorMessages = errors.getOrDefault(ref.getFieldName(), new ArrayList<>());
                errorMessages.add(simplifiedMessage);
                errors.put(ref.getFieldName(), errorMessages);
            }
        } else {
            errors.put("general", new ArrayList<>() {{
                add(ex.getMessage());
            }});
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("Bad Request", errors));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<String>> handleDatabaseException(DataAccessException ex) {
        log.error("DataAccessException: {}" ,ex.getMessage());
        String errorMessage = "Database error occurred: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        log.error("Exception, class: {}, message: {}" ,ex.getClass().getSimpleName(), ex.getMessage());
        if(ex instanceof ClientRequestException clientRequestException){
            return ResponseEntity.status(clientRequestException.getStatusCode()).body(ApiResponse.error(clientRequestException.getMessage(), clientRequestException.getData()));
        }
        String errorMessage = "Error: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(errorMessage));

    }
}
