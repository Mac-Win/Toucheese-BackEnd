package com.toucheese.question.dto;

import com.toucheese.question.entity.AnswerStatus;
import com.toucheese.question.entity.Question;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record QuestionRequest (
        String title,
        String content
){
    public static QuestionRequest of(Question question) {
        return QuestionRequest.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .build();
    }
}
