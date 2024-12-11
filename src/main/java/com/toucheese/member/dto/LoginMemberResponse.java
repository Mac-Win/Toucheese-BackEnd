package com.toucheese.member.dto;

import lombok.Builder;

@Builder
public record LoginMemberResponse(
        Long memberId,
        String name,
        String accessToken
) {

}
