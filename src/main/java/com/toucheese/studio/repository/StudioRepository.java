package com.toucheese.studio.repository;

import com.toucheese.studio.entity.Studio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudioRepository extends JpaRepository<Studio, Long> {

	List<Studio> findByNameContaining(String name);

	Page<Studio> findAllByOrderByName(Pageable pageable);
}
