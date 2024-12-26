package com.toucheese.admin.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.global.config.ImageConfig;
import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.global.util.PageUtils;
import com.toucheese.question.dto.AnswerRequest;
import com.toucheese.question.dto.QuestionDetailResponse;
import com.toucheese.question.dto.QuestionResponse;
import com.toucheese.question.entity.Answer;
import com.toucheese.question.entity.Question;
import com.toucheese.question.repository.AnswerRepository;
import com.toucheese.question.repository.QuestionRepository;
import com.toucheese.question.service.QuestionReadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminAnswerService {
    private final ImageConfig imageConfig;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionReadService questionReadService;

    private Answer findAnswerByAnswerId(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new ToucheeseBadRequestException("해당 답변이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public Page<QuestionResponse> getAllQuestions(int page) {
        Pageable pageable = PageUtils.createPageable(page);
        Page<Question> questions = questionRepository.findAll(pageable);
        return questions.map(question ->
                QuestionResponse.of(question, imageConfig.getResizedImageBaseUrl())
        );
    }

    @Transactional(readOnly = true)
    public QuestionDetailResponse findQuestionDetail(Long questionId) {
        Question question = questionReadService.findQuestionById(questionId);
        return QuestionDetailResponse.of(question, imageConfig.getResizedImageBaseUrl());
    }

    @Transactional
    public void addAnswer(Long questionId, String title, String content) {

        Question question = questionReadService.findQuestionById(questionId);

        Answer answer = answerRepository.save(
                Answer.builder()
                .title(title)
                .content(content)
                .createDate(LocalDate.now())
                .build()
        );

        question.completeAnswer(answer);
    }

    // 답변 수정
    @Transactional
    public void updateAnswer(Long answerId, AnswerRequest answerRequest) {
        Answer answer = findAnswerByAnswerId(answerId);
        answer.updateAnswer(answerRequest.title(), answerRequest.content());
    }

    // 답변 삭제
    @Transactional
    public void deleteAnswer(Long answerId) {
        Answer answer = findAnswerByAnswerId(answerId);

        Question question = questionRepository.findByAnswerId(answerId);
        question.resetAnswer();

        answerRepository.delete(answer);  // 답변 삭제
    }
}
