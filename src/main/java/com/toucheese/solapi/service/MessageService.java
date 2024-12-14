package com.toucheese.solapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.toucheese.member.entity.Member;
import com.toucheese.member.service.MemberService;
import com.toucheese.reservation.event.ReservationMessageEvent;
import com.toucheese.solapi.util.EmailUtil;
import com.toucheese.solapi.util.SolapiUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final SolapiUtil solapiUtil;
    private final EmailUtil emailUtil;
    private final MemberService memberService;

    @Value("${solapi.from-number}")
    private String fromNumber; // 고정 발신 번호

    @Async("customTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservationMessageEvent(ReservationMessageEvent event) {
        Long memberId = event.memberId();
        Member member = memberService.findMemberById(memberId);
        String messageText = solapiUtil.formatMessage(member.getName());

        try {
            sendSms(member.getPhone(), messageText);
        } catch (Exception e) {
            log.error("Failed to send SMS to {}: {}", member.getPhone(), e.getMessage(), e);
        }
        try {
            sendEmail(member.getEmail(), "예약 접수 알림", messageText);
        } catch (Exception e) {
            log.error("Failed to send Email to {}: {}", member.getEmail(), e.getMessage(), e);
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