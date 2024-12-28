package com.toucheese.member.controller;

import java.security.Principal;

import com.toucheese.global.util.PrincipalUtils;
import com.toucheese.member.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.toucheese.global.data.ApiResponse;
import com.toucheese.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
@Tag(name = "회원 API")
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인 요청을 처리하는 API
     * @param loginRequest 로그인 요청 정보 (email, password, deviceId)
     * @return 로그인 시 생성 된 접근 토큰 및 로그인 정보
     */
    @PostMapping
    @Operation(summary = "회원 로그인", description = "email, password로 로그인 합니다.")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        MemberTokenResponse memberTokenResponse = memberService.login(loginRequest);
        return ApiResponse.accessTokenResponse(
                LoginResponse.of(memberTokenResponse),
                memberTokenResponse.tokenDTO().accessToken()
        );
    }

    @PutMapping
    @Operation(summary = "첫 로그인 회원 정보 변경")
    public ResponseEntity<?> memberFirstLoginUpdate(@RequestBody @Valid MemberFirstLoginUpdateRequest request, Principal principal) {
        memberService.memberFirstLoginUpdate(request, principal);
        return ApiResponse.updatedSuccess("회원 정보를 성공적으로 업데이트했습니다.");
    }

    @GetMapping
    @Operation(summary = "내 정보 불러오기")
    public ResponseEntity<MemberResponse> getMemberInfo(Principal principal) {
        Long memberId = PrincipalUtils.extractMemberId(principal);
        return ApiResponse.getObjectSuccess(
                MemberResponse.of(memberService.findMemberById(memberId))
        );
    }
}