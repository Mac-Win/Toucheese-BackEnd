package com.toucheese.solapi.service;

import com.toucheese.global.exception.ToucheeseBadRequestException;
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
    private DefaultMessageService solapiService;
    @Value("${solapi.api-key}")
    private String apiKey;
    @Value("${solapi.api-secret-key}")
    private String apiSecretKey;
    @Value("${solapi.base-url}")
    private String baseUrl;
    private static final String fromNumber = "고정발신번호(유진님전화번호)"; // 고정 발신 번호

    @PostConstruct
    public void init() {
        this.solapiService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, baseUrl);
    }

    public String sendMessage(MessageRequest request) {
        try {
            String messageText = String.format("안녕하세요 , %s 님 ! 예약 접수되었습니다.", request.name());

            Message message = new Message();
            message.setFrom(fromNumber);
            message.setTo(request.recipient());
            message.setText(messageText);

            solapiService.send(message);
            return "Message sent successfully." + request.name();
        } catch (NurigoMessageNotReceivedException exception) {
            throw new ToucheeseBadRequestException("Faild messages: " + exception.getFailedMessageList());
        } catch (Exception exception) {
            throw new ToucheeseBadRequestException("Error" + exception.getMessage());
        }


    }
}
