package com.toucheese.question.dto;

import com.toucheese.question.entity.AnswerStatus;
import com.toucheese.question.entity.Question;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record QuestionResponse (
        Long id,
        String title,
        String content,
        LocalDate createDate,
        String answerContent,
        LocalDate answerCreateDate,
        AnswerStatus answerStatus
){
    public static QuestionResponse of(Question question) {
        String answerContent = null;
        LocalDate answerCreateDate = null;
        if (question.getAnswer() != null && question.getAnswerStatus() == AnswerStatus.답변완료) {
            answerContent = question.getAnswer().getContent();
            answerCreateDate = question.getAnswer().getCreateDate();
        }
        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .createDate(question.getCreateDate())
                .answerContent(answerContent)
                .answerCreateDate(answerCreateDate)
                .answerStatus(question.getAnswerStatus())
                .build();
    }
}
