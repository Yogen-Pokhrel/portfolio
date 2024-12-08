package com.portfolio.auth.common.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class JWTClaimException extends ClientRequestException{
    public JWTClaimException(String message, Map<String, Object> data) {super(message, data);}
    public JWTClaimException(String message){
        super(message);
    }

}
