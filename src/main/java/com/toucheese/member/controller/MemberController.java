package com.toucheese.member.controller;

import com.toucheese.member.dto.LoginRequest;
import com.toucheese.member.dto.LoginResponse;
import com.toucheese.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
     * 로그인 API
     * @param loginRequest
     * @return
     */
    @PostMapping
    @Operation(summary = "회원 로그인", description = "username, password로 로그인 합니다.")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        return memberService.loginMember(loginRequest.username(), loginRequest.password());
    }

}
