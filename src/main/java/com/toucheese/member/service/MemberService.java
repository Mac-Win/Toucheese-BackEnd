package com.toucheese.member.service;

import java.security.Principal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.global.util.PrincipalUtils;
import com.toucheese.member.dto.AuthProvider;
import com.toucheese.member.dto.FindEmailRequest;
import com.toucheese.member.dto.KakaoMember;
import com.toucheese.member.dto.LoginRequest;
import com.toucheese.member.dto.MemberContactInfoResponse;
import com.toucheese.member.dto.MemberFirstLoginUpdateRequest;
import com.toucheese.member.dto.MemberTokenResponse;
import com.toucheese.member.dto.ResetPasswordRequest;
import com.toucheese.member.dto.SignupRequest;
import com.toucheese.member.dto.TokenDTO;
import com.toucheese.member.entity.Member;
import com.toucheese.member.entity.Role;
import com.toucheese.member.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
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

	@Transactional(readOnly = true)
	public Member findMemberByEmail(String email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(ToucheeseBadRequestException::new);
	}

	/**
	 * 로그인 처리를 위한 메서드
	 * @param loginRequest 로그인 정보 (email, password, deviceId)
	 * @return 로그인 시 생성 된 접근 토큰
	 */
	@Transactional
	public MemberTokenResponse login(LoginRequest loginRequest) {
		Member member = findMemberByEmail(loginRequest.email());

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

	/**
	 * 카카오 사용자 정보를 기반으로 회원 조회 또는 생성
	 * @param kakaoMember 카카오 사용자 정보
	 * @return 회원 엔티티
	 */
	public Member findOrCreateMember(KakaoMember kakaoMember) {
		return memberRepository.findByEmail(kakaoMember.email())
			.orElseGet(() -> createMember(kakaoMember));
	}

	private Member createMember(KakaoMember kakaoMember) {
		Member member = Member.builder()
			.email(kakaoMember.email())
			.name(kakaoMember.nickname())
			.password(null) // 소셜 로그인 사용자는 비밀번호 없음
			.role(Role.USER)
			.authProvider(AuthProvider.KAKAO)
			.isFirstLogin(true) // 처음 생성되는 경우 true로 설정
			.build();
		return memberRepository.save(member);
	}

	@Transactional
	public void memberFirstLoginUpdate(MemberFirstLoginUpdateRequest request, Principal principal) {
		Long MemberId = PrincipalUtils.extractMemberId(principal);

		Member member = findMemberById(MemberId);

		member.firstLoginUpdate(request);
	}

	@Transactional
	public void createSignup(SignupRequest signupRequest) {
		Member member = Member.builder()
			.email(signupRequest.email())
			.name(signupRequest.name())
			.password(signupRequest.password())
			.phone(signupRequest.phone())
			.role(Role.USER)
			.authProvider(AuthProvider.LOCAL)
			.build();
		memberRepository.save(member);
	}

	@Transactional
	public void deleteMember(Long memberId) {
		Member member = findMemberById(memberId);
		memberRepository.delete(member);
	}


	@Transactional(readOnly = true)
	public String findEmail(FindEmailRequest findEmailRequest) {
		return memberRepository.findByNameAndPhone(findEmailRequest.name(), findEmailRequest.phone())
			.map(Member::getEmail)
			.orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));
	}

	@Transactional
	public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
		Member member = findMemberByEmail(resetPasswordRequest.email());

		member.passwordUpdate(resetPasswordRequest.password());
	}
}
