package com.toucheese.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
	@NotBlank(message = "이메일은 필수 항목입니다.")
	@Email(message = "유효한 이메일 형식을 입력해주세요.")
	String email,
	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	String password
) {}