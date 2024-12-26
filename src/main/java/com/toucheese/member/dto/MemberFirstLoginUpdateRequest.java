package com.toucheese.member.dto;

import lombok.Builder;

@Builder
public record MemberFirstLoginUpdateRequest(
	String name,
	String phone
) {
}
