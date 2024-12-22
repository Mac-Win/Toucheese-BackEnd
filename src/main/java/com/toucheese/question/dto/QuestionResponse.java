package com.toucheese.question.dto;

import com.toucheese.question.entity.AnswerStatus;
import com.toucheese.question.entity.Question;
import java.util.List;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record QuestionResponse (
        Long id,
        String title,
        String content,
        LocalDate createDate,
        AnswerStatus answerStatus,
        List<String> imageUrls
){
    public static QuestionResponse of(Question question, String baseUrl) {
        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .createDate(question.getCreateDate())
                .answerStatus(question.getAnswerStatus())
                .imageUrls(question.getQuestionImages().stream()
                        .map(questionImage -> baseUrl + questionImage.getResizedPath())
                        .toList()
                )
                .build();
    }
}
