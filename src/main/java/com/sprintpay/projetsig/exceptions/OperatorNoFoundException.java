package com.sprintpay.projetsig.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OperatorNoFoundException extends RuntimeException{


    public OperatorNoFoundException(String message){
        super(message);
    }
}
