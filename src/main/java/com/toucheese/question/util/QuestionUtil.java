package com.toucheese.question.util;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import com.toucheese.global.util.PrincipalUtils;
import com.toucheese.member.entity.Member;
import com.toucheese.member.repository.MemberRepository;
import com.toucheese.question.entity.Question;
import com.toucheese.question.repository.QuestionRepository;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class QuestionUtil {

    // Principal 로부터 Member 객체 가져오기
    public static Member getMemberByPrincipal(Principal principal, MemberRepository memberRepository) {
        Long memberId = PrincipalUtils.extractMemberId(principal);
        return memberRepository.findById(memberId)
                .orElseThrow(()-> new ToucheeseBadRequestException("회원이 존재하지 않습니다."));
    }

    // ID 로 Question 객체 조회
    public static Question findQuestionById(Long id, QuestionRepository questionRepository) {
        return questionRepository.findById(id)
                .orElseThrow(()-> new ToucheeseBadRequestException("해당 게시글이 존재하지 않습니다."));
    }

    // 게시글 접근 권한 검증
    public static void validateMemberAccess(Question question, Principal principal) {
        Long memberId = PrincipalUtils.extractMemberId(principal);
        if(!question.getMember().getId().equals(memberId)) {
            throw new ToucheeseUnAuthorizedException("자신의 게시글만 접근 가능합니다.");
        }
    }

}
