package com.sprintpay.projetsig.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PrincipalStationNoFoundException extends RuntimeException{

    public PrincipalStationNoFoundException(String message){
        super(message);
    }
}
