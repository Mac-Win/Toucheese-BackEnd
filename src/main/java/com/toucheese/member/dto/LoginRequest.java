package com.toucheese.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequest(
        @NotBlank(message = "아이디는 필수 값입니다.")
        String email,
        @NotBlank(message = "비밀번호는 필수 값입니다.")
        String password
) {

}
