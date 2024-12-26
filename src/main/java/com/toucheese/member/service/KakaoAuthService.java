package com.toucheese.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.member.dto.KakaoMember;
import com.toucheese.member.dto.SocalLoginCombinedResponse;
import com.toucheese.member.dto.SocialLoginRequest;
import com.toucheese.member.dto.SocialLoginResponse;
import com.toucheese.member.dto.TokenDTO;
import com.toucheese.member.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoAuthService {

	private final WebClient kakaoApiClient; // 사용자 정보 조회용 WebClient
	private final WebClient kakaoAuthClient; //
	private final MemberService memberService;
	private final TokenService tokenService;

	@Value("${kakao.app-key.rest-api-key}")
	private String restApiKey;

	@Value("${kakao.redirect-uri}")
	private String redirectUri;

	/**
	 * 카카오 로그인 처리
	 * @param socialLoginRequest 클라이언트에서 전달된 카카오 토큰 정보
	 * @return 사용자 정보
	 */
	@Transactional
	public SocalLoginCombinedResponse handleKakaoLogin(SocialLoginRequest socialLoginRequest) {
		KakaoMember kakaoMember = getKakaoMemberInfo(socialLoginRequest.accessToken()).block();

		Member member = memberService.findOrCreateMember(kakaoMember);

		String deviceId = socialLoginRequest.deviceId();
		TokenDTO tokenDTO = tokenService.loginMemberToken(member, deviceId);

		return new SocalLoginCombinedResponse(SocialLoginResponse.from(member, tokenDTO), tokenDTO.accessToken());
	}

	/**
	 * 카카오 사용자 정보 요청
	 * @param accessToken 카카오 Access Token
	 * @return 사용자 정보
	 */
	public Mono<KakaoMember> getKakaoMemberInfo(String accessToken) {
		return kakaoApiClient.get()
			.uri("/v2/user/me")
			.headers(headers -> headers.setBearerAuth(accessToken))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.flatMap(response -> {
				Object propertiesObj = response.get("properties");
				Object kakaoAccountObj = response.get("kakao_account");

				if (propertiesObj instanceof Map<?, ?> properties
					&& kakaoAccountObj instanceof Map<?, ?> kakaoAccount) {
					return Mono.just(new KakaoMember(
						response.get("id").toString(),
						(String)properties.get("nickname"),
						(String)kakaoAccount.get("email")
					));
				}

				return Mono.error(new ToucheeseBadRequestException("응답 형식이 잘못되었습니다."));
			});
	}

	/**
	 * 카카오 Access Token 요청
	 * @param code 인증 코드
	 * @return Access Token, idToken
	 */
	public SocialLoginRequest getAccessTokenFromKakao(String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "authorization_code");
		formData.add("client_id", restApiKey);
		formData.add("redirect_uri", redirectUri);
		formData.add("code", code);

		Map<String, Object> response = kakaoAuthClient.post()
			.uri("/oauth/token")
			.body(BodyInserters.fromFormData(formData))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();

		validateResponse(response);

		String accessToken = (String) response.get("access_token");

		String idToken = (String) response.get("id_token");

		return SocialLoginRequest.builder()
			.accessToken(accessToken)
			.idToken(idToken)
			.build();
	}

	private void validateResponse(Map<String, Object> response) {
		if (response == null || response.isEmpty()) {
			throw new ToucheeseBadRequestException("카카오로부터 응답이 없거나 데이터가 비어 있습니다.");
		}
		if (!response.containsKey("access_token") || !response.containsKey("id_token")) {
			throw new ToucheeseBadRequestException("카카오 응답에 필요한 필드가 포함되지 않았습니다: " + response);
		}
	}
}