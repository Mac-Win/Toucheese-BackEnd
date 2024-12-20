package com.toucheese.question.service;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.member.repository.MemberRepository;
import com.toucheese.question.entity.Question;
import com.toucheese.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionReadService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    // ID 로 Question 객체 조회
    public Question findQuestionById(Long id, QuestionRepository questionRepository) {
        return questionRepository.findById(id)
                .orElseThrow(()-> new ToucheeseBadRequestException("해당 게시글이 존재하지 않습니다."));
    }

}
