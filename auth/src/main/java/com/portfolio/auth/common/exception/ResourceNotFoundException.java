package com.portfolio.auth.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ResourceNotFoundException extends ClientRequestException{
    public ResourceNotFoundException(String message, Map<String, Object> data){
        super(message, HttpStatus.NOT_FOUND, data);
    }

    public ResourceNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
