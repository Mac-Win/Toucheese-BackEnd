package com.toucheese.admin.service;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.global.util.PageUtils;
import com.toucheese.question.dto.AnswerResponse;
import com.toucheese.question.dto.QuestionResponse;
import com.toucheese.question.entity.Answer;
import com.toucheese.question.entity.AnswerStatus;
import com.toucheese.question.entity.Question;
import com.toucheese.question.repository.AnswerRepository;
import com.toucheese.question.repository.QuestionRepository;
import com.toucheese.question.service.QuestionReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionReadService questionReadService;

    private Answer findAnswerByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId)
                .orElseThrow(() -> new ToucheeseBadRequestException("해당 답변이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public Page<QuestionResponse> getAllQuestions(int page) {
        Pageable pageable = PageUtils.createPageable(page);
        Page<Question> questions = questionRepository.findAll(pageable);
        return questions.map(QuestionResponse::of);
    }

    @Transactional(readOnly = true)
    public QuestionResponse getQuestionById(Long questionId) {
        Question question = questionReadService.findQuestionById(questionId);
        return QuestionResponse.of(question);
    }

    @Transactional
    public void addAnswer(Long questionId, String content) {

        Question question = questionReadService.findQuestionById(questionId);

        Answer answer = answerRepository.save(
                Answer.builder()
                .question(question)
                .content(content)
                .createDate(LocalDate.now())
                .build()
        );

        question.completeAnswer(answer);
    }

    // 답변 수정
    @Transactional
    public void updateAnswer(Long questionId, String content) {
        Answer answer = findAnswerByQuestionId(questionId);
        answer.updateAnswer(content);
    }

    // 답변 삭제
    @Transactional
    public void deleteAnswer(Long questionId) {
        Answer answer = findAnswerByQuestionId(questionId);

        answerRepository.delete(answer);  // 답변 삭제
    }
}
