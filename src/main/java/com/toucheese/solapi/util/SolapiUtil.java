package com.toucheese.solapi.util;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.global.exception.ToucheeseInternalServerErrorException;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SolapiUtil {
    private final DefaultMessageService solapiService;

    public static final String MESSAGE_TEMPLATE = "안녕하세요, %s 님 ! 예약 접수되었습니다.";
    public SolapiUtil(DefaultMessageService solapiService) {
        this.solapiService = solapiService;
    }

    public DefaultMessageService getSolapiService() {
        return solapiService;
    }

    public String formatMessage(String name) {
        return String.format(MESSAGE_TEMPLATE, name);
    }

    public void send(String from, String to, String messageText) {
        Message message = new Message();
        message.setFrom(from);
        message.setTo(to);
        message.setText(messageText);

        try {
            solapiService.send(message);
        } catch (NurigoMessageNotReceivedException exception) {
            throw new ToucheeseBadRequestException("Faild messages: " + exception.getFailedMessageList());
        } catch (Exception exception) {
            throw new ToucheeseInternalServerErrorException("An unexpected error occurred:" + exception.getMessage());
        }
    }
}
