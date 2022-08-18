package com.tinqin.academy.userstore.domain.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserCreationFailureException extends Exception{
    public UserCreationFailureException(String message) {
        super(message);
    }
}
