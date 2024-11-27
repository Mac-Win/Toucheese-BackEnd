package com.toucheese.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ToucheeseException extends RuntimeException{

    public ToucheeseException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
}

