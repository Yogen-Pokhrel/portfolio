package com.portfolio.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileUploadFailedException extends ClientRequestException {

    public FileUploadFailedException(String message, Map<String, Object> data){
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, data);
    }

    public FileUploadFailedException(String message){
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}