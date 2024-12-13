package com.toucheese.solapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.toucheese.member.entity.Member;
import com.toucheese.member.service.MemberService;
import com.toucheese.solapi.util.EmailUtil;
import com.toucheese.solapi.util.SolapiUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final SolapiUtil solapiUtil;
    private final EmailUtil emailUtil;
    private final MemberService memberService;

    @Value("${solapi.from-number}")
    private String fromNumber; // 고정 발신 번호

    @Async("customTaskExecutor")
    public void sendMessageForLoggedInUser(Long memberId) {

        Member member = memberService.findMemberById(memberId);
        String messageText = solapiUtil.formatMessage(member.getName());

        try {
            sendSms(member.getPhone(), messageText);
        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
        }
        try {
            sendEmail(member.getEmail(), "예약 접수 알림", messageText);
        } catch (Exception e) {
            System.err.println("Failed to send Email: " + e.getMessage());
        }
    }

    @Async("customTaskExecutor")
    public void sendSms(String phone, String messageText) {
        solapiUtil.send(fromNumber, phone, messageText);
    }

    @Async("customTaskExecutor")
    public void sendEmail(String email, String subject, String body) {
        emailUtil.sendEmail(email, subject, body);
    }
}