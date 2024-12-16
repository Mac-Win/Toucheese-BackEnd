package com.toucheese.member.dto;

import lombok.Builder;

@Builder
public record MemberTokenResponse(
        Long memberId,
        String name,
        TokenDTO tokenDTO
) {

}
