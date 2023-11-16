package com.sprintpay.projetsig.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EquipementNoFoundException extends RuntimeException{

    public EquipementNoFoundException(String message){
        super(message);
    }
}
