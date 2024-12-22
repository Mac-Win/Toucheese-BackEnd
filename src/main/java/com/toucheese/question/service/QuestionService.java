package com.toucheese.question.service;

import com.toucheese.global.config.ImageConfig;
import com.toucheese.global.util.PageUtils;
import com.toucheese.member.entity.Member;
import com.toucheese.question.dto.QuestionDetailResponse;
import com.toucheese.question.dto.QuestionRequest;
import com.toucheese.question.dto.QuestionResponse;
import com.toucheese.question.entity.AnswerStatus;
import com.toucheese.question.entity.Question;
import com.toucheese.question.repository.QuestionRepository;
import com.toucheese.question.util.QuestionUtil;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final ImageConfig imageConfig;
    private final QuestionRepository questionRepository;
    private final QuestionReadService questionReadService;

    @Transactional
    public Question createQuestion(QuestionRequest questionRequest, Principal principal) {
        Member member = questionReadService.findMemberByPrincipal(principal);
        Question question = Question.builder()
                .title(questionRequest.title())
                .content(questionRequest.content())
                .member(member)
                .answerStatus(AnswerStatus.답변대기)
                .build();

        return questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public QuestionDetailResponse findQuestionDetailById(Long id) {
        Question question = questionReadService.findQuestionById(id);
        return QuestionDetailResponse.of(question, imageConfig.getResizedImageBaseUrl());
    }

    @Transactional(readOnly = true)
    public Page<QuestionResponse> findQuestions(Principal principal, int page) {
        Member member = questionReadService.findMemberByPrincipal(principal);
        Pageable pageable = PageUtils.createPageable(page);

        Page<Question> questions = questionRepository.findAllByMemberId(member.getId(), pageable);
        return questions.map(question ->
                QuestionResponse.of(question, imageConfig.getResizedImageBaseUrl())
        );
    }

    @Transactional
    public void updateQuestion(Long id, QuestionRequest questionRequest, Principal principal) {
        Question question = questionReadService.findQuestionById(id);
        QuestionUtil.validateMemberAccess(question, principal);

        question.update(questionRequest.title(), questionRequest.content());

        questionRepository.save(question);
    }

    @Transactional
    public void deleteQuestion(Long id, Principal principal) {
        Question question = questionReadService.findQuestionById(id);
        QuestionUtil.validateMemberAccess(question, principal);
        questionRepository.delete(question);
    }
}
