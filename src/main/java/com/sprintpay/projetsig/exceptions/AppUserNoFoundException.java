package com.sprintpay.projetsig.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AppUserNoFoundException extends RuntimeException{
    public AppUserNoFoundException(String message){
        super(message);
    }
}
