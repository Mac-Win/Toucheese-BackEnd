package com.toucheese.member.dto;

public record SocalLoginCombinedResponse (
	SocialLoginResponse socialLoginResponse,
	String accessToken
) {
}
