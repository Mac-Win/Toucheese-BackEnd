package com.toucheese.solapi.service;

import com.toucheese.solapi.dto.MessageRequest;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final DefaultMessageService solapiService;
    private static final String fromNumber = "고정 발신 번호"; // 고정 발신 번호

    public MessageService() {
        this.solapiService = NurigoApp.INSTANCE.initialize(
                "APIKEY",
                "ApiSecretKey",
                "https://api.solapi.com"

        );
    }

    public String sendMessage(MessageRequest request) throws Exception {

        String messageText = String.format("안녕하세요 , %s 님 ! 예약 접수되었습니다.", request.name());

        Message message = new Message();
        message.setFrom(fromNumber);
        message.setTo(request.recipient());
        message.setText(messageText);

        solapiService.send(message);
        return "Message sent successfully." + request.name();
    }
}
