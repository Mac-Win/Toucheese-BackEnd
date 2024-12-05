package com.toucheese.global.exception;

import org.springframework.http.HttpStatus;

public class ToucheeseInternalServerErrorException extends ToucheeseException {

    private static final String DEFAULT_MESSAGE = "서버 내부 오류가 발생했습니다.";

    public ToucheeseInternalServerErrorException() {
        super(DEFAULT_MESSAGE);
    }

    public ToucheeseInternalServerErrorException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
