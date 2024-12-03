package com.toucheese.member.service;

import com.toucheese.global.exception.ToucheeseBadRequestException;
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

    @Transactional
    public LoginResponse loginMember(String username, String password) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new ToucheeseBadRequestException("아이디 혹은 비밀번호가 잘못되었습니다."));

        checkMemberPassword(member, password);

        String accessToken = tokenService.saveToken(member);
        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private void checkMemberPassword(Member member, String password) {
        if (!member.getPassword().equals(password)) {
            throw new ToucheeseBadRequestException("아이디 혹은 비밀번호가 잘못되었습니다.");
        }
    }

}
