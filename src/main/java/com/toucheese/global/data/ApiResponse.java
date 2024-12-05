package com.toucheese.global.data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> extends ResponseEntity<T> {

    public ApiResponse(HttpStatus status) {
        super(status);
    }

    public static ResponseEntity<Object> accessTokenResponse(String accessToken) {
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessToken)
                .build();
    }
}
