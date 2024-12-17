package com.toucheese.member.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
        Long memberId,
        String name,
        String refreshToken,
        String deviceId
) {

    public static LoginResponse of(MemberTokenResponse memberTokenResponse) {
        return LoginResponse.builder()
                .memberId(memberTokenResponse.memberId())
                .name(memberTokenResponse.name())
                .refreshToken(memberTokenResponse.tokenDTO().refreshToken())
                .deviceId(memberTokenResponse.tokenDTO().deviceId())
                .build();
    }

}
