package com.toucheese.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record KakaoMemberRequest(
	@NotBlank(message = "idToken은 필수 값입니다.")
	String idToken,
	@NotBlank(message = "accessToken은 필수 값입니다.")
	String accessToken
) {
}