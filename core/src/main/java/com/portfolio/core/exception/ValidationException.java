package com.portfolio.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ValidationException extends ClientRequestException {
    public ValidationException(String message, Map<String, List<String>> errors){
        super(message, HttpStatus.BAD_REQUEST, new HashMap<>(errors));
    }

    public ValidationException(String message){
        super(message, HttpStatus.BAD_REQUEST);
    }
}
