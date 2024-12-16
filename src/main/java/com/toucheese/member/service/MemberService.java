package com.toucheese.member.service;

import com.toucheese.member.dto.LoginRequest;
import com.toucheese.member.dto.TokenDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.member.dto.MemberTokenResponse;
import com.toucheese.member.dto.MemberContactInfoResponse;
import com.toucheese.member.entity.Member;
import com.toucheese.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    /**
     * 회원 정보 검색
     * @param id 회원 아이디
     * @return 회원 정보
     */
    @Transactional(readOnly = true)
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(ToucheeseBadRequestException::new);
    }

    /**
     * 로그인 처리를 위한 메서드
     * @param loginRequest 로그인 정보 (email, password, deviceId)
     * @return 로그인 시 생성 된 접근 토큰
     */
    @Transactional
    public MemberTokenResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new ToucheeseBadRequestException("아이디 혹은 비밀번호가 잘못되었습니다."));

        checkMemberPassword(member, loginRequest.password());
        TokenDTO tokenDTO = tokenService.loginMemberToken(member, loginRequest.deviceId());

        return MemberTokenResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .tokenDTO(tokenDTO)
                .build();
    }

    /**
     * 비밀번호가 같은지 확인하기 위한 메서드
     * @param member email로 찾은 회원
     * @param password 입력한 비밀번호
     */
    private void checkMemberPassword(Member member, String password) {
        if (!member.getPassword().equals(password)) {
            throw new ToucheeseBadRequestException("아이디 혹은 비밀번호가 잘못되었습니다.");
        }
    }

    @Transactional(readOnly = true)
    public MemberContactInfoResponse findMemberContactInfo(Long memberId) {
        return MemberContactInfoResponse.of(findMemberById(memberId));
    }
}
