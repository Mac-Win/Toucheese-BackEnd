package com.toucheese.question.dto;

import com.toucheese.question.entity.AnswerStatus;
import com.toucheese.question.entity.Question;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record QuestionDetailResponse (
        Long id,
        String title,
        String content,
        LocalDate createDate,
        AnswerResponse answerResponse,
        AnswerStatus answerStatus,
        String authorName,
        List<String> imageUrls
){
    public static QuestionDetailResponse of(Question question, String baseUrl) {
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
                .authorName(question.getMember().getName())
                .imageUrls(question.getQuestionImages().stream()
                        .map(questionImage -> baseUrl + questionImage.getResizedPath())
                        .toList()
                )
                .build();
    }
}
