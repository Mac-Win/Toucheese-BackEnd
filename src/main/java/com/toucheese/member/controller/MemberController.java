package com.toucheese.member.controller;

import com.toucheese.global.data.ApiResponse;
import com.toucheese.member.dto.LoginMemberResponse;
import com.toucheese.member.dto.LoginRequest;
import com.toucheese.member.dto.LoginResponse;
import com.toucheese.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
@Tag(name = "회원 API")
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인 요청을 처리하는 메서드
     * @param loginRequest 로그인 요청 정보 (email, password)
     * @return 로그인 시 생성 된 접근 토큰 (accessToken)
     */
    @PostMapping
    @Operation(summary = "회원 로그인", description = "email, password로 로그인 합니다.")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest loginRequest) {
        LoginMemberResponse loginMemberResponse = memberService.loginMember(loginRequest.email(), loginRequest.password());
        return ApiResponse.accessTokenResponse(
            LoginResponse.of(loginMemberResponse), loginMemberResponse.accessToken()
        );
    }

}
