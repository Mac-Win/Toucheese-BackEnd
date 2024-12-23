package com.toucheese.studio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toucheese.studio.entity.OperatingHour;

public interface OperatingHourRepository extends JpaRepository<OperatingHour, Long> {

	List<OperatingHour> findByStudioId(Long studioId);
}
