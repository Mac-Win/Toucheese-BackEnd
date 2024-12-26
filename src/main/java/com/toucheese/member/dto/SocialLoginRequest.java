package com.toucheese.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SocialLoginRequest(
	String idToken, // 소셜 로그인 ID 토큰 (카카오, 네이버 등)
	@NotBlank(message = "accessToken은 필수 값입니다.")
	String accessToken, // 소셜 로그인 액세스 토큰
	String platform, // 로그인 플랫폼 (KAKAO, NAVER 등)
	String deviceId // 기기 ID (선택)
) {
}