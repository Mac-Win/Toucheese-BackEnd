package com.toucheese.question.dto;

import com.toucheese.question.entity.Question;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record QuestionResponse (
        Long id,
        String title,
        String content,
        LocalDate createDate,
        String answerStatus
){
    public static QuestionResponse of(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .createDate(question.getCreateDate())
                .answerStatus(question.getAnswerStatus().name()) // Enum -> String
                .build();
    }
}
