package com.toucheese.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.toucheese.question.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	Page<Question> findAllByMemberId(Long memberId, Pageable pageable);

	Question findByAnswerId(Long answerId);
}
