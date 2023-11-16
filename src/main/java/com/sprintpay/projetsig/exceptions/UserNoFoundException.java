package com.sprintpay.projetsig.exceptions;

public class UserNoFoundException extends RuntimeException{
    public UserNoFoundException(String message){
        super(message);
    }
}
