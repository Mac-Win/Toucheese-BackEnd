package com.toucheese.question.dto;

import com.toucheese.question.entity.Answer;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AnswerResponse(
        Long id,
        String title,
        String content,
        LocalDate createDate
) {
    public static AnswerResponse of(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .title(answer.getTitle())
                .content(answer.getContent())
                .createDate(answer.getCreateDate())
                .build();
    }
}
