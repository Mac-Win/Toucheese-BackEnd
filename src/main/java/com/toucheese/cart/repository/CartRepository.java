package com.toucheese.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.toucheese.member.entity.Member;
import com.toucheese.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	List<Cart> findByMember(Member member);

	@Query("SELECT c.member FROM Cart c WHERE c.id = :cartId")
	Member findMemberByCartId(@Param("cartId") Long cartId);

	@Query("SELECT c FROM Cart c WHERE c.member.id = :memberId AND c.id IN :cartIds")
	List<Cart> findCartsByMemberIdAndCartIds(@Param("memberId") Long memberId, @Param("cartIds") List<Long> cartIds);

}
