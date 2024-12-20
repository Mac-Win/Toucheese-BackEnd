package com.toucheese.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder
			.baseUrl("https://kapi.kakao.com") // 기본 URL 설정
			.defaultHeader("Content-Type", "application/json") // 기본 헤더 설정
			.build();
	}
}
