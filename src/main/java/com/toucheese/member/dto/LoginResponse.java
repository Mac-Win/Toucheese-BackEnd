package com.toucheese.member.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
        Long memberId,
        String name
) {

    public static LoginResponse of(LoginMemberResponse loginMemberResponse) {
        return LoginResponse.builder()
                .memberId(loginMemberResponse.memberId())
                .name(loginMemberResponse.name())
                .build();
    }

}
