package com.portfolio.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class InternalServerException extends ClientRequestException {
    public InternalServerException(String message, Map<String, Object> errors){
        super(message, HttpStatus.BAD_REQUEST, errors);
    }

    public InternalServerException(String message){
        super(message, HttpStatus.BAD_REQUEST);
    }
}
