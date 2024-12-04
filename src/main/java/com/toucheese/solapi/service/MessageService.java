package com.toucheese.solapi.service;

import com.toucheese.solapi.dto.MessageRequest;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final DefaultMessageService solapiService;

    public MessageService() {
        this.solapiService = NurigoApp.INSTANCE.initialize(
                "NCSE0CV4QQKN79LE",
                "80GTLY3XHFNNTCTRGPSORX5UZFUM9GJJ",
                "https://api.solapi.com"

        );
    }

    public String sendMessage(MessageRequest request) throws Exception {
        Message message = new Message();
        message.setFrom(request.from());
        message.setTo(request.recipient());
        message.setText(request.text());

        solapiService.send(message);
        return "Message sent successfully.";
    }
}
