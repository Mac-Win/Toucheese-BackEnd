package com.toucheese.question.service;

import com.toucheese.global.config.ImageConfig;
import com.toucheese.global.util.PageUtils;
import com.toucheese.image.entity.ImageType;
import com.toucheese.image.service.ImageService;
import com.toucheese.member.entity.Member;
import com.toucheese.member.service.MemberService;
import com.toucheese.question.dto.QuestionDetailResponse;
import com.toucheese.question.dto.QuestionRequest;
import com.toucheese.question.dto.QuestionResponse;
import com.toucheese.question.entity.AnswerStatus;
import com.toucheese.question.entity.Question;
import com.toucheese.question.repository.QuestionRepository;
import com.toucheese.question.util.QuestionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final ImageConfig imageConfig;
    private final ImageService imageService;
    private final MemberService memberService;
    private final QuestionRepository questionRepository;
    private final QuestionReadService questionReadService;

    @Transactional
    public void createQuestion(QuestionRequest questionRequest, Long memberId) {
        Member member = memberService.findMemberById(memberId);
        Question question = Question.builder()
                .title(questionRequest.title())
                .content(questionRequest.content())
                .member(member)
                .answerStatus(AnswerStatus.답변대기)
                .build();

        question = questionRepository.save(question);

        if (questionRequest.uploadFiles() != null && !questionRequest.uploadFiles().isEmpty()) {
            imageService.uploadImageWithDetails(questionRequest.uploadFiles(), question.getId(), ImageType.QUESTION);
        }
    }

    @Transactional(readOnly = true)
    public QuestionDetailResponse findQuestionDetailById(Long id) {
        Question question = questionReadService.findQuestionById(id);
        return QuestionDetailResponse.of(question, imageConfig.getResizedImageBaseUrl());
    }

    @Transactional(readOnly = true)
    public Page<QuestionResponse> findQuestions(int page, Long memberId) {
        Member member = memberService.findMemberById(memberId);
        Pageable pageable = PageUtils.createPageable(page);

        Page<Question> questions = questionRepository.findAllByMemberId(member.getId(), pageable);
        return questions.map(question ->
                QuestionResponse.of(question, imageConfig.getResizedImageBaseUrl())
        );
    }

    @Transactional
    public void updateQuestion(Long questionId, QuestionRequest questionRequest, Long memberId) {
        Question question = questionReadService.findQuestionById(questionId);

        QuestionUtil.validateMemberAccess(question, memberId);
        question.update(questionRequest.title(), questionRequest.content());
    }

    @Transactional
    public void deleteQuestion(Long questionId, Long memberId) {
        Question question = questionReadService.findQuestionById(questionId);
        QuestionUtil.validateMemberAccess(question, memberId);
        questionRepository.delete(question);
    }
}
