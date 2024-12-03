package com.toucheese.global.exception;

import org.springframework.http.HttpStatus;

public class ToucheeseTokenInvalidException extends ToucheeseException {

    private static final String DEFAULT_MESSAGE = "인증이 만료되었습니다.";

    public ToucheeseTokenInvalidException() {
        super(DEFAULT_MESSAGE);
    }

    public ToucheeseTokenInvalidException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
