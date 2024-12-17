package com.toucheese.member.service;

import com.toucheese.global.data.JwtValidateStatus;
import com.toucheese.member.dto.MemberTokenResponse;
import com.toucheese.member.dto.ReissueRequest;
import com.toucheese.member.dto.TokenDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import com.toucheese.global.util.JwtTokenProvider;
import com.toucheese.member.entity.Member;
import com.toucheese.member.entity.Token;
import com.toucheese.member.repository.TokenRepository;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    /**
     * 토큰 재발급을 위한 메서드
     * @param accessToken 접근 토큰
     * @param reissueRequest 재발급을 위한 검증 정보
     * @return 재발급 된 AccessToken 및 로그인 정보
     */
    @Transactional
    public MemberTokenResponse reissueAccessToken(String accessToken, ReissueRequest reissueRequest) {
        String memberId = jwtTokenProvider.getClaims(accessToken).getSubject();
        String deviceId = reissueRequest.deviceId();
        Token token = findTokenByMemberIdAndDeviceId(Long.parseLong(memberId), deviceId);

        String refreshToken = token.getRefreshToken();
        checkRefreshToken(refreshToken, reissueRequest.refreshToken()); // refreshToken 검증

        // DENIED, EXPIRED 되지 않았다면 AccessToken 재발급
        Member member = token.getMember();
        String newAccessToken = jwtTokenProvider.createAccessToken(memberId, member.getRole());

        return MemberTokenResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .tokenDTO(
                        TokenDTO.builder()
                                .refreshToken(refreshToken)
                                .accessToken(newAccessToken)
                                .deviceId(deviceId)
                                .build()
                )
                .build();
    }

    /**
     * 회원 정보와 기기 정보를 통해 해당되는 토큰을 검색
     * @param memberId 회원 아이디
     * @param deviceId 기기 아이디
     * @return 토큰 정보
     */
    @Transactional(readOnly = true)
    public Token findTokenByMemberIdAndDeviceId(Long memberId, String deviceId) {
        return tokenRepository.findByMemberIdAndDeviceId(memberId, deviceId)
                .orElseThrow(ToucheeseUnAuthorizedException::new);
    }

    /**
     * 로그인 시 회원 토큰 처리 메서드
     * @param member 회원 정보
     * @param deviceId 기기 아이디
     * @return 기록된 토큰 정보
     */
    @Transactional
    public TokenDTO loginMemberToken(Member member, String deviceId) {
        TokenDTO tokenDTO = generateTokens(member);

        if (validDeviceId(deviceId)) {
            deviceId = UUID.randomUUID().toString();
            saveToken(member, deviceId, tokenDTO);
        } else {
            Token token = findTokenByMemberIdAndDeviceId(member.getId(), deviceId);
            token.updateRefreshToken(tokenDTO.refreshToken());
        }

        return TokenDTO.of(tokenDTO.accessToken(), tokenDTO.refreshToken(), deviceId);
    }

    /**
     * 기기 아이디 검증을 위한 메서드
     * @param deviceId 기기 아이디
     * @return true / false
     */
    private boolean validDeviceId(String deviceId) {
        return deviceId == null || deviceId.isEmpty();
    }

    /**
     * 새 로그인 시 토큰 저장을 위한 메서드
     * @param member 회원 정보
     * @param deviceId 기기 아이디
     * @param tokenDTO 생성된 토큰 정보
     */
    @Transactional
    public void saveToken(Member member, String deviceId, TokenDTO tokenDTO) {
        Token token = Token.builder()
                .member(member)
                .refreshToken(tokenDTO.refreshToken())
                .deviceId(deviceId)
                .build();

        tokenRepository.save(token);
    }

    /**
     * AccessToken, RefreshToken 생성 메서드
     * @param member 회원 정보
     * @return 생성된 토큰
     */
    public TokenDTO generateTokens(Member member) {
        String memberId = member.getId().toString();
        String refreshToken = jwtTokenProvider.createRefreshToken(memberId);
        String accessToken = jwtTokenProvider.createAccessToken(memberId, member.getRole());

        return TokenDTO.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

    /**
     * RefreshToken 을 검증하기 위한 토큰
     * @param refreshToken 갱신 토큰
     * @throws ToucheeseUnAuthorizedException 갱신 토큰 또한 만료되었을 경우 재로그인 요청
     */
    public void checkRefreshToken(String refreshToken, String requestRefreshToken) {
        JwtValidateStatus jwtValidateStatus = jwtTokenProvider.validateToken(refreshToken);

        if (jwtValidateStatus == JwtValidateStatus.EXPIRED ||
                jwtValidateStatus == JwtValidateStatus.DENIED ||
                !refreshToken.equals(requestRefreshToken)
        ) {
            throw new ToucheeseUnAuthorizedException("재로그인이 필요합니다.");
        }
    }

}
