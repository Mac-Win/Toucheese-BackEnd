package com.toucheese.solapi.controller;

import com.toucheese.solapi.dto.MessageRequest;
import com.toucheese.solapi.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "메세지 API", description = "문자 메세지 발송 관련 솔라피 API")
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    @Operation(summary = "문자 메세지 예약 접수 발송", description = "이름, 전화번호를 포함한 요청을 받아 예약 접수 메세지 발송 ")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest messageRequest) {
        String result = messageService.sendMessage(messageRequest);
        return ResponseEntity.ok(result);
    }
}
