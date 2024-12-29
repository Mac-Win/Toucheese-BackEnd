package com.toucheese.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record SignupRequest(
	@NotBlank(message = "이메일은 필수 항목입니다.")
	@Email(message = "유효한 이메일 형식을 입력해주세요.")
	String email,

	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	String password,

	@NotBlank(message = "이름은 필수 항목입니다.")
	String name,

	@NotBlank(message = "전화번호는 필수 항목입니다.")
	@Pattern(
		regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$",
		message = "유효한 전화번호 형식을 입력해주세요. 예: 010-1234-5678 또는 01012345678"
	)
	String phone
) {
}
