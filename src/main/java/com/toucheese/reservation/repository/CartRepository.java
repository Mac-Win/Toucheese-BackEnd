package com.toucheese.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toucheese.reservation.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
