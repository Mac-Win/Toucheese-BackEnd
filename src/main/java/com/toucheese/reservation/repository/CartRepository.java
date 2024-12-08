package com.toucheese.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toucheese.member.entity.Member;
import com.toucheese.reservation.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	List<Cart> findByMember(Member member);
}
