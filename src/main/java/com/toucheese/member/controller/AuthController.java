package com.toucheese.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toucheese.global.data.ApiResponse;
import com.toucheese.member.dto.KakaoMemberRequest;
import com.toucheese.member.dto.KakaoMemberResponse;
import com.toucheese.member.service.KakaoAuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Tag(name = "인증 API")
public class AuthController {
	private final KakaoAuthService kakaoAuthService;

	/**
	 * 카카오 로그인 요청 처리
	 * @param kakaoMemberRequest 클라이언트에서 전달된 카카오 토큰 정보
	 * @return 사용자 정보 및 JWT
	 */
	@PostMapping("/kakao")
	public ResponseEntity<KakaoMemberResponse> kakaoLogin(@Valid @RequestBody KakaoMemberRequest kakaoMemberRequest) {
		StringBuilder jwtTokenContainer = new StringBuilder();
		KakaoMemberResponse kakaoMemberResponse = kakaoAuthService.handleKakaoLogin(kakaoMemberRequest, jwtTokenContainer);
		return ApiResponse.accessTokenResponse(kakaoMemberResponse, jwtTokenContainer.toString());
	}

	@GetMapping("/kakao/callback")
	public ResponseEntity<KakaoMemberResponse> kakaoCallback(@RequestParam String code) {
		// 1. 카카오 Access Token 요청
		String accessToken = kakaoAuthService.getAccessTokenFromKakao(code);

		// 2. 사용자 정보 및 JWT 생성
		KakaoMemberRequest kakaoMemberRequest = KakaoMemberRequest.builder()
			.accessToken(accessToken)
			.build();

		// 3. 서비스 계층에서 JWT 생성
		StringBuilder jwtTokenContainer = new StringBuilder();
		KakaoMemberResponse kakaoMemberResponse = kakaoAuthService.handleKakaoLogin(kakaoMemberRequest, jwtTokenContainer);

		// 4. JWT를 헤더에만 포함
		return ApiResponse.accessTokenResponse(kakaoMemberResponse, jwtTokenContainer.toString());
	}

}
