package com.toucheese.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken
) {

}
