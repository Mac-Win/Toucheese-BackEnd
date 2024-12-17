package com.toucheese.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.cart.entity.Cart;
import com.toucheese.cart.repository.CartRepository;
import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.member.entity.Member;
import com.toucheese.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartReadService {
	private final CartRepository cartRepository;
	private final MemberService memberService;

	@Transactional(readOnly = true)
	public Cart findCartById(Long id) {
		return cartRepository.findById(id)
			.orElseThrow(() -> new ToucheeseBadRequestException("장바구니 항목이 존재하지 않습니다."));

	}

	@Transactional(readOnly = true)
	public List<Cart> findCartListByMemberId(Long memberId) {
		Member member = memberService.findMemberById(memberId);
		return cartRepository.findByMember(member);
	}

	@Transactional(readOnly = true)
	public List<Cart> findCheckoutCartItems(Long memberId, List<Long> cartIds) {
		return cartRepository.findByMemberIdAndIdIn(memberId, cartIds);
	}

	@Transactional(readOnly = true)
	public Cart validateCartOwnership(Long cartId, Long memberId) {
		Cart cart = findCartById(cartId);
		if (!cart.getMember().getId().equals(memberId)) {
			throw new ToucheeseBadRequestException("해당 장바구니 권한이 없습니다.");
		}
		return cart;
	}
}