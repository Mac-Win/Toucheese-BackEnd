package com.toucheese.solapi.controller;

import com.toucheese.solapi.dto.MessageRequest;
import com.toucheese.solapi.service.MessageService;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest messageRequest) {
        try {
            String result = messageService.sendMessage(messageRequest);
            return ResponseEntity.ok(result);
        } catch (NurigoMessageNotReceivedException exception) {
            return ResponseEntity.status(500)
                    .body("Failed to send some messages: " + exception.getFailedMessageList());
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Error occurred: "  + exception.getMessage());
        }
    }
}
