package com.toucheese.solapi.service;

import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import com.toucheese.member.entity.Member;
import com.toucheese.member.entity.Token;
import com.toucheese.member.repository.MemberRepository;
import com.toucheese.member.repository.TokenRepository;
import com.toucheese.member.service.MemberService;
import com.toucheese.solapi.util.EmailUtil;
import com.toucheese.solapi.util.SolapiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final SolapiUtil solapiUtil;
    private final EmailUtil emailUtil;
    private final MemberService memberService;

    @Value("${solapi.from-number}")
    private String fromNumber; // 고정 발신 번호

    public void sendMessageForLoggedInUser(Long memberId) {

        Member member = memberService.findMemberById(memberId);
        String messageText = solapiUtil.formatMessage(member.getName());

        sendSms(member.getPhone(), messageText);
        sendEmail(member.getEmail(), "예약 접수 알림" ,messageText);
    }

    private void sendSms(String phone, String messageText) {
        solapiUtil.send(fromNumber, phone, messageText);
    }

    private void sendEmail(String email, String subject, String body) {
        emailUtil.sendEmail(email, subject, body);
    }
}