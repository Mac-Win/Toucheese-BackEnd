package com.toucheese.question.dto;

import com.toucheese.question.entity.AnswerStatus;
import com.toucheese.question.entity.Question;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record QuestionDetailResponse (
        Long id,
        String title,
        String content,
        LocalDate createDate,
        AnswerResponse answerResponse,
        AnswerStatus answerStatus
){
    public static QuestionDetailResponse of(Question question) {
        AnswerResponse answerResponse = null;
        if (question.getAnswer() != null && question.getAnswerStatus() == AnswerStatus.답변완료) {
            answerResponse = AnswerResponse.of(question.getAnswer());
        }
        return QuestionDetailResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .createDate(question.getCreateDate())
                .answerResponse(answerResponse)
                .answerStatus(question.getAnswerStatus())
                .build();
    }
}
