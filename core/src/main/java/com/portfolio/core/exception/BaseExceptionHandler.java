package com.portfolio.core.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.portfolio.core.common.ApiResponse;
import jakarta.validation.ConstraintViolation;
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

import static com.portfolio.core.util.ErrorMessageUtil.getCustomErrorMessage;
import static com.portfolio.core.util.ErrorMessageUtil.simplifyMessage;

@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication failed: {}" ,ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Authentication failed: " + simplifyMessage(ex.getMessage())));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthorizationDeniedException ex) {
        log.error("Authorization failed: {}" ,ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error("Forbidden resource: " + simplifyMessage(ex.getMessage())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException: {}" ,ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Internal server error, please contact admin team to resolve any issues, message: " + simplifyMessage(ex.getMessage())));
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

        ex.getBindingResult()
                .getGlobalErrors()
                .forEach(error -> {
                    // Constraint validators which are implemented on class level i.e. ElementType.TYPE will be provided a target such that it will be easier to map error field.
                    String targetField = error.unwrap(ConstraintViolation.class)
                            .getConstraintDescriptor()
                            .getAttributes()
                            .get("target")
                            .toString();
                    if(targetField == null) {
                        targetField = "general";
                    }
                    String defaultMessage = error.getDefaultMessage();
                    errors.computeIfAbsent(targetField, k -> new ArrayList<>()).add(defaultMessage);
                });


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("Bad Request", errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleJsonParseException(HttpMessageNotReadableException ex) {
        log.error("JSON Parsing error: {}", ex.getMessage());

        Map<String, List<String>> errors = new HashMap<>();
        if (ex.getCause() instanceof JsonMappingException jsonMappingException) {
            for(JsonMappingException.Reference ref : jsonMappingException.getPath()) {
                String message = getCustomErrorMessage(ref);
                List<String> errorMessages = errors.getOrDefault(ref.getFieldName(), new ArrayList<>());
                errorMessages.add(message);
                errors.put(ref.getFieldName(), errorMessages);
            }
        } else {
            errors.put("general", new ArrayList<>() {{
                add(simplifyMessage(ex.getMessage()));
            }});
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("Bad Request", errors));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<String>> handleDatabaseException(DataAccessException ex) {
        log.error("DataAccessException: {}" ,ex.getMessage());
        String errorMessage = "Database error occurred: " + simplifyMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        log.error("Exception, class: {}, message: {}" ,ex.getClass().getSimpleName(), ex.getMessage());
        if(ex instanceof ClientRequestException clientRequestException){
            return ResponseEntity.status(clientRequestException.getStatusCode()).body(ApiResponse.error(clientRequestException.getMessage(), clientRequestException.getData()));
        }
        String errorMessage = "Error: " + simplifyMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(errorMessage));

    }
}
