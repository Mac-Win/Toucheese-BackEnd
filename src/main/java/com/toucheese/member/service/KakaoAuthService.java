package com.toucheese.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.global.util.JwtTokenProvider;
import com.toucheese.member.dto.KakaoMember;
import com.toucheese.member.dto.SocialLoginRequest;
import com.toucheese.member.dto.SocialLoginResponse;
import com.toucheese.member.dto.TokenDTO;
import com.toucheese.member.entity.Member;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

	private final WebClient webClient;
	private final MemberService memberService;
	private final TokenService tokenService;
	private final JwtTokenProvider jwtTokenProvider;

	@Value("${kakao.app-key.rest-api-key}")
	private String restApiKey;

	@Value("${kakao.redirect-uri}")
	private String redirectUri;

	/**
	 * 카카오 로그인 처리
	 * @param socialLoginRequest 클라이언트에서 전달된 카카오 토큰 정보
	 * @return 사용자 정보
	 */
	public SocialLoginResponse handleKakaoLogin(SocialLoginRequest socialLoginRequest) {
		// 1. 카카오 사용자 정보 요청
		KakaoMember kakaoMember = getKakaoMemberInfo(socialLoginRequest.accessToken()).block();

		// 2. 회원 조회 또는 생성
		Member member = memberService.findOrCreateMember(kakaoMember);

		// 3. JWT 생성
		String deviceId = socialLoginRequest.deviceId();
		TokenDTO tokenDTO = tokenService.loginMemberToken(member, deviceId);

		// 4. 첫 로그인 여부 확인 및 사용자 정보 반환
		return SocialLoginResponse.from(member, tokenDTO);
	}

	/**
	 * 카카오 사용자 정보 요청
	 * @param accessToken 카카오 Access Token
	 * @return 사용자 정보
	 */
	public Mono<KakaoMember> getKakaoMemberInfo(String accessToken) {
		return webClient.get()
			.uri("/v2/user/me")
			.headers(headers -> headers.setBearerAuth(accessToken))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
			.flatMap(response -> {
				// 1. 타입 검증 후 안전하게 캐스팅
				Object propertiesObj = response.get("properties");
				Object kakaoAccountObj = response.get("kakao_account");

				if (propertiesObj instanceof Map<?, ?> properties && kakaoAccountObj instanceof Map<?, ?> kakaoAccount) {
					return Mono.just(new KakaoMember(
						response.get("id").toString(),
						(String) properties.get("nickname"),
						(String) kakaoAccount.get("email")
					));
				}

				// 2. 데이터 형식이 잘못된 경우 Mono.error로 예외 처리
				return Mono.error(new ToucheeseBadRequestException("응답 형식이 잘못되었습니다: 'properties' 또는 'kakao_account' 없음"));
			});
	}

	/**
	 * 카카오 Access Token 요청
	 * @param code 인증 코드
	 * @return Access Token
	 */
	public String getAccessTokenFromKakao(String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "authorization_code");
		formData.add("client_id", restApiKey);
		formData.add("redirect_uri", redirectUri);
		formData.add("code", code);

		// 1. WebClient 응답 처리
		Map<String, Object> response = webClient.post()
			.uri("https://kauth.kakao.com/oauth/token")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(BodyInserters.fromFormData(formData))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
			.block();

		// 2. 응답 객체 및 "access_token" 키 검증
		if (response == null || !response.containsKey("access_token")) {
			throw new IllegalStateException("카카오로부터 유효한 액세스 토큰을 받지 못했습니다.");
		}

		return (String) response.get("access_token");
	}
}