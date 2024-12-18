package com.toucheese.global.data;

import com.toucheese.member.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> extends ResponseEntity<T> {

    public ApiResponse(HttpStatus status) {
        super(status);
    }

    public static <LoginResponse> ResponseEntity<LoginResponse> accessTokenResponse(LoginResponse loginResponse, String accessToken) {
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessToken)
                .body(loginResponse);
    }

    public static ResponseEntity<?> createdSuccess(String message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    public static ResponseEntity<?> deletedSuccess(String message) {
        return ResponseEntity.ok(message);
    }

    public static ResponseEntity<?> updatedSuccess(String message) {
        return ResponseEntity.ok(message);
    }

    public static <T> ResponseEntity<T> getObjectSuccess(T object) {
        return ResponseEntity.ok(object);
    }

}
