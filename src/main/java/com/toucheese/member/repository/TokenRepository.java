package com.toucheese.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toucheese.member.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByDeviceId(String deviceId);
}
