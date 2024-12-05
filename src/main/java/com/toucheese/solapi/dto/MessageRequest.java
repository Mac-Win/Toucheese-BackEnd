package com.toucheese.solapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MessageRequest(
        @JsonProperty("to") String recipient,   // 수신자 번호 (직렬화 시 "to"로 변환)
        String from,        // 발신자 번호
        String name,        // 이름 추가
        String text         // 메시지 내용
) {
    public static MessageRequest of(String recipient, String from, String name, String text) {
        return MessageRequest.builder()
                .recipient(recipient)
                .from(from)
                .name(name)
                .text(text)
                .build();
    }
}
