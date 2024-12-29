package com.toucheese.member.dto;

import jakarta.validation.constraints.NotBlank;

public record FindEmailRequest(
	@NotBlank(message = "이름은 필수입니다.")
	String name,
	@NotBlank(message = "전화번호는 필수입니다.")
	String phone
) {
}
