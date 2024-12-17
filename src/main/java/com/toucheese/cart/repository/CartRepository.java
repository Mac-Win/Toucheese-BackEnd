package com.toucheese.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toucheese.cart.entity.Cart;
import com.toucheese.member.entity.Member;

public interface CartRepository extends JpaRepository<Cart, Long> {

	List<Cart> findByMember(Member member);

	List<Cart> findByMemberIdAndIdIn(Long memberId, List<Long> cartIds);
}