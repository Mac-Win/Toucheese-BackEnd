package com.toucheese.studio.repository;

import com.toucheese.studio.entity.Studio;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface StudioQueryRepository extends JpaRepository<Studio, Long> {

	List<Studio> findByNameContaining(String name);

}
