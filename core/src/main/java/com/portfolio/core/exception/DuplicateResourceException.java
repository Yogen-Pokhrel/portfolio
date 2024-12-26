package com.portfolio.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class DuplicateResourceException extends ClientRequestException {
     public DuplicateResourceException(String message, Map<String, Object> data){
        super(message, HttpStatus.CONFLICT, data);
    }

    public DuplicateResourceException(String message){
        super(message, HttpStatus.CONFLICT);
    }
}
