package com.toucheese.solapi.controller;

import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import com.toucheese.solapi.dto.MessageRequest;
import com.toucheese.solapi.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/messages")
@RequiredArgsConstructor
@Tag(name = "메세지 API", description = "문자 메세지 발송 관련 솔라피 API")
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "메세지 예약 접수 발송", description = "로그인한 유저의 예약 접수 메세지 발송 ( 문자 , 이메일 ) ")
    public ResponseEntity<String> sendMessage(Principal principal) {

        Long memberId = getMemberIdFromPrincipal(principal);
        messageService.sendMessageForLoggedInUser(memberId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Message sent successfully");
    }

    /**
     * Principal 객체에서 memberId를 추출하는 메서드
     */
    private Long getMemberIdFromPrincipal(Principal principal) {
        try {
            return Long.parseLong(principal.getName());
        } catch (NumberFormatException e) {
            throw new ToucheeseUnAuthorizedException("Invalid memberId format");
        }
    }
}
