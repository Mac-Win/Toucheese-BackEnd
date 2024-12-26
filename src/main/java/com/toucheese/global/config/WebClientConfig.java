package com.toucheese.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient kakaoApiClient(WebClient.Builder builder) {
		return builder
			.baseUrl("https://kapi.kakao.com") // 사용자 정보 조회용 기본 URL
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}

	@Bean
	public WebClient kakaoAuthClient(WebClient.Builder builder) {
		return builder
			.baseUrl("https://kauth.kakao.com") // 인증 토큰 발급용 기본 URL
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.build();
	}
}