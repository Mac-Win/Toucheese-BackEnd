package com.toucheese.solapi.service;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.global.exception.ToucheeseInternalServerErrorException;
import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import com.toucheese.member.entity.Member;
import com.toucheese.member.entity.Token;
import com.toucheese.member.repository.MemberRepository;
import com.toucheese.member.repository.TokenRepository;
import com.toucheese.solapi.config.SolapiUtil;
import com.toucheese.solapi.dto.MessageRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final SolapiUtil solapiUtil;
    private final MemberRepository memberRepository; // Member 조회용
    private final TokenRepository tokenRepository;

    @Value("${solapi.from-number}")
    private String fromNumber; // 고정 발신 번호

    public String sendMessageForLoggedInUser(String accessToken) {

        Token token = tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(()-> new ToucheeseUnAuthorizedException("Invalid access token"));

        Member member = token.getMember();

        String messageText = solapiUtil.formatMessage(member.getName());

        solapiUtil.send(fromNumber, member.getPhone(), messageText);

        return "Message sent successfully to " + member.getName();
    }
}
