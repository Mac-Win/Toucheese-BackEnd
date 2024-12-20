package com.toucheese.question.service;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import com.toucheese.global.util.PrincipalUtils;
import com.toucheese.member.entity.Member;
import com.toucheese.member.repository.MemberRepository;
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


    // Principal 로부터 Member 객체 가져오기
    @Transactional(readOnly = true)
    public Member findMemberByPrincipal(Principal principal, MemberRepository memberRepository) {
        Long memberId = PrincipalUtils.extractMemberId(principal);
        return memberRepository.findById(memberId)
                .orElseThrow(()-> new ToucheeseBadRequestException("회원이 존재하지 않습니다."));
    }

    // 게시글 접근 권한 검증
    @Transactional(readOnly = true)
    public void validateMemberAccess(Question question, Principal principal) {
        Long memberId = PrincipalUtils.extractMemberId(principal);
        if(!question.getMember().getId().equals(memberId)) {
            throw new ToucheeseUnAuthorizedException("자신의 게시글만 접근 가능합니다.");
        }
    }

}
