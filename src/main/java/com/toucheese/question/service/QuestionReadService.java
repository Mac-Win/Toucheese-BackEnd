package com.toucheese.question.service;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import com.toucheese.global.util.PrincipalUtils;
import com.toucheese.member.entity.Member;
import com.toucheese.member.repository.MemberRepository;
import com.toucheese.member.service.MemberService;
import com.toucheese.question.entity.Question;
import com.toucheese.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class QuestionReadService {

    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    // ID 로 Question 객체 조회
    public Question findQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(()-> new ToucheeseBadRequestException("해당 게시글이 존재하지 않습니다."));
    }

}
