package com.toucheese.member.controller;

import com.toucheese.global.data.ApiResponse;
import com.toucheese.global.util.TokenUtils;
import com.toucheese.member.dto.MemberTokenResponse;
import com.toucheese.member.dto.LoginResponse;
import com.toucheese.member.dto.ReissueRequest;
import com.toucheese.member.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/tokens")
@Tag(name = "토큰 API")
public class TokenController {

    private final TokenService tokenService;
    private final TokenUtils tokenUtils;

    /**
     * 토큰 재발급을 처리하는 API
     * @param request AccessToken 추출을 위한 요청 객체
     * @param reissueRequest 로그인 검증을 위한 RefreshToken, DeviceId 정보
     * @return 재발급 된 AccessToken 및 로그인 정보
     */
    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "만료된 AccessToken을 재발급 합니다.")
    public ResponseEntity<?> reissueToken(
            HttpServletRequest request,
            @RequestBody @Valid ReissueRequest reissueRequest
    ) {
        String accessToken = tokenUtils.getTokenFromAuthorizationHeader(request);
        MemberTokenResponse memberTokenResponse = tokenService.reissueAccessToken(accessToken, reissueRequest);
        return ApiResponse.accessTokenResponse(
                LoginResponse.of(memberTokenResponse),
                memberTokenResponse.tokenDTO().accessToken()
        );
    }
}
