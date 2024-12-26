package com.toucheese.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toucheese.question.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
