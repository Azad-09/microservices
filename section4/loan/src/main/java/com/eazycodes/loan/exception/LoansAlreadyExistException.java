package com.eazycodes.loan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoansAlreadyExistException extends RuntimeException{

    public LoansAlreadyExistException(String message){
        super(message);
    }
}
