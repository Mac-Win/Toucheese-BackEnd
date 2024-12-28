package com.toucheese.member.dto;

import com.toucheese.member.entity.Member;
import lombok.Builder;

@Builder
public record MemberResponse(
        Long memberId,
        String name,
        String email,
        String phone
) {

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .build();
    }

}
