package com.toucheese.question.dto;

import com.toucheese.question.entity.Answer;
import lombok.Builder;

@Builder
public record AnswerRequest(
        String title,
        String content
) {
    public static AnswerRequest of(Answer answer) {
        return AnswerRequest.builder()
                .title(answer.getTitle())
                .content(answer.getContent())
                .build();
    }
}
