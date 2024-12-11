package com.toucheese.solapi.util;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    private final JavaMailSender mailSender;

    public EmailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public static final String FROM_EMAIL = "toucheeese@gmail.com";

    public void sendEmail(String to, String subject, String body) {
        if (!isValidEmail(to)) {
            throw new ToucheeseBadRequestException("잘못된 이메일 형식입니다: " + to);
        }

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(FROM_EMAIL);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);

        }
    }

    private boolean isValidEmail(String email) {
        // 이메일 형식 검증 (정규 표현식 사용)
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

}
