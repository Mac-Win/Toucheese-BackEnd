package com.toucheese.member.controller;

import com.toucheese.global.data.ApiResponse;
import com.toucheese.global.util.TokenUtils;
import com.toucheese.member.dto.MemberTokenResponse;
import com.toucheese.member.dto.LoginResponse;
import com.toucheese.member.dto.ReissueRequest;
import com.toucheese.member.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/tokens")
public class TokenController {

    private final TokenService tokenService;
    private final TokenUtils tokenUtils;

    @PostMapping("/reissue")
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
