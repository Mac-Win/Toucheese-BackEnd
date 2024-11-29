package com.toucheese.global.exception;

import org.springframework.http.HttpStatus;

public class ToucheeseBadRequestException extends ToucheeseException {

    private static final String DEFAULT_MESSAGE = "잘못된 요청입니다.";

    public ToucheeseBadRequestException() {
        super(DEFAULT_MESSAGE);
    }

    public ToucheeseBadRequestException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
