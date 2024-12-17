package com.toucheese.member.dto;

import lombok.Builder;

@Builder
public record TokenDTO(
        String accessToken,
        String refreshToken,
        String deviceId
) {

    public static TokenDTO of(String accessToken, String refreshToken, String deviceId) {
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .deviceId(deviceId)
                .build();
    }
}
