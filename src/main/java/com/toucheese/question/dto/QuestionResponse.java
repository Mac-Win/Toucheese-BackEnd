package com.toucheese.question.dto;

import com.toucheese.question.entity.AnswerStatus;
import com.toucheese.question.entity.Question;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record QuestionResponse (
        Long id,
        String title,
        String content,
        LocalDate createDate,
        AnswerStatus answerStatus,
        String authorName,
        List<String> imageUrls
){
    public static QuestionResponse of(Question question, String baseUrl) {
        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .createDate(question.getCreateDate())
                .answerStatus(question.getAnswerStatus())
                .authorName(question.getMember().getName())
                .imageUrls(question.getQuestionImages().stream()
                        .map(questionImage -> baseUrl + questionImage.getResizedPath())
                        .toList()
                )
                .build();
    }
}
