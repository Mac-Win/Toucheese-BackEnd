package com.toucheese.member.dto;

import lombok.Builder;

@Builder
public record KakaoMemberResponse(
	String nickname,
	boolean isFirstLogin
) {
}
