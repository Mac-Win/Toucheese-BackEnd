package com.toucheese.solapi.service;

import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import com.toucheese.member.entity.Member;
import com.toucheese.member.entity.Token;
import com.toucheese.member.repository.MemberRepository;
import com.toucheese.member.repository.TokenRepository;
import com.toucheese.solapi.util.SolapiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final SolapiUtil solapiUtil;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;

    @Value("${solapi.from-number}")
    private String fromNumber; // 고정 발신 번호

    /**
     * 로그인된 사용자를 위한 메세지 전송
     * @param accessToken 사용자의 액세스 토큰
     * @return 전송 결과 메세지
     */
    public String sendMessageForLoggedInUser(String accessToken) {

        Member member = getMemberByAccessToken(accessToken);
        String messageText = solapiUtil.formatMessage(member.getName());
        sendMessage(member, messageText);
        return successMessage(member.getName());
    }

    /**
     * accessToken으로 Member 조회
     * @param accessToken 액세스 토큰
     * @return 조회된 Member 객체
     */

    private Member getMemberByAccessToken(String accessToken) {
        return tokenRepository.findByAccessToken(accessToken)
                .map(Token::getMember)
                .orElseThrow(()-> new ToucheeseUnAuthorizedException("Invalid access token"));
    }

    /**
     * Solapi 를 이용해 메세지 전송
     * @param member        수신자 정보
     * @param messageText   전송할 메세지 내용
     */
    private void sendMessage(Member member, String messageText) {
        solapiUtil.send(fromNumber, member.getPhone(), messageText);
    }

    /**
     * 성공 메세지 생성
     * @param recipientName 수신자 이름
     * @return 성공 메세지 문자열
     */
    private String successMessage(String recipientName) {
        return "Message sent successfully to " + recipientName;
    }
}
