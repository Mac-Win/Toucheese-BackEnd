package com.toucheese.global.data;

import com.toucheese.member.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> extends ResponseEntity<T> {

    public ApiResponse(HttpStatus status) {
        super(status);
    }

    public static ResponseEntity<Object> accessTokenResponse(LoginResponse loginResponse, String accessToken) {
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessToken)
                .body(loginResponse);
    }
}
