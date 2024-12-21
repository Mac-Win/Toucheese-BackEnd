package com.toucheese.question.util;

import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import com.toucheese.global.util.PrincipalUtils;
import com.toucheese.question.entity.Question;

import java.security.Principal;

public class QuestionUtil {
    // 게시글 접근 권한 검증
    public static void validateMemberAccess(Question question, Principal principal) {
        Long memberId = PrincipalUtils.extractMemberId(principal);
        if (!question.getMember().getId().equals(memberId)) {
            throw new ToucheeseUnAuthorizedException("자신의 게시글만 접근 가능합니다.");
        }
    }
}
