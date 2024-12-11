package com.toucheese.member.service;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.member.dto.LoginMemberResponse;
import com.toucheese.member.dto.LoginResponse;
import com.toucheese.member.entity.Member;
import com.toucheese.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    @Transactional(readOnly = true)
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(ToucheeseBadRequestException::new);
    }

    /**
     * 로그인 처리를 위한 메서드
     * @param email 아이디
     * @param password 비밀번호
     * @return 로그인 시 생성 된 접근 토큰
     */
    @Transactional
    public LoginMemberResponse loginMember(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ToucheeseBadRequestException("아이디 혹은 비밀번호가 잘못되었습니다."));

        checkMemberPassword(member, password);
        String accessToken = tokenService.saveToken(member);

        return LoginMemberResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .accessToken(accessToken)
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

}
