package com.toucheese.question.util;

import com.toucheese.global.exception.ToucheeseUnAuthorizedException;
import com.toucheese.question.entity.Question;

public class QuestionUtil {
    // 게시글 접근 권한 검증
    public static void validateMemberAccess(Question question, Long memberId) {
        if (!question.getMember().getId().equals(memberId)) {
            throw new ToucheeseUnAuthorizedException("자신의 게시글만 접근 가능합니다.");
        }
    }
}
