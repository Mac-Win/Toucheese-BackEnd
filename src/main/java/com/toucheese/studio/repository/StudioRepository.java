package com.toucheese.studio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toucheese.studio.entity.Studio;

public interface StudioRepository extends JpaRepository<Studio, Long> {

	List<Studio> findByNameContaining(String name);
}
