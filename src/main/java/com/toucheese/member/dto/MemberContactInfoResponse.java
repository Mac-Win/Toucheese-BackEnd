package com.toucheese.member.dto;

import com.toucheese.member.entity.Member;

import lombok.Builder;

@Builder
public record MemberContactInfoResponse(
	String email,
	String name,
	String phone
) {
	public static MemberContactInfoResponse of(Member member) {
		return MemberContactInfoResponse.builder()
			.email(member.getEmail())
			.name(member.getName())
			.phone(member.getPhone())
			.build();
	}
}
