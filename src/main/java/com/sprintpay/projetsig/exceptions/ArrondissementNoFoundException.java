package com.sprintpay.projetsig.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ArrondissementNoFoundException extends RuntimeException{

    public ArrondissementNoFoundException(String message){
        super(message);
    }
}
