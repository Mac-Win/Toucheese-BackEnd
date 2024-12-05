package com.toucheese.solapi.service;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.global.exception.ToucheeseInternalServerErrorException;
import com.toucheese.solapi.config.SolapiUtil;
import com.toucheese.solapi.dto.MessageRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final SolapiUtil solapiUtil;

    @Value("${solapi.from-number}")
    private String fromNumber; // 고정 발신 번호

    public String sendMessage(MessageRequest request) {
        try {
            solapiUtil.send(fromNumber, request.recipient(), request.name());

            return "Message sent successfully to " + request.name();
        } catch (Exception exception) {
            throw new ToucheeseInternalServerErrorException("An unexpected error occurred:" + exception.getMessage());
        }

    }
}
