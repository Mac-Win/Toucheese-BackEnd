package com.toucheese.global.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {

	/**
	 * 인증 헤더를 확인하여 AccessToken을 반환하기 위한 메서드
	 * @param request 요청 정보
	 * @return AccessToken
	 */
	public String getTokenFromAuthorizationHeader(HttpServletRequest request) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return "";
	}
}