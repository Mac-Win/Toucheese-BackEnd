package com.toucheese.studio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.toucheese.studio.entity.OperatingHour;

public interface OperatingHourRepository extends JpaRepository<OperatingHour, Long> {

	@Query("SELECT o FROM OperatingHour o WHERE o.studio.id = :studioId AND o.dayOfWeek = :dayOfWeek")
	OperatingHour findByStudioIdAndDayOfWeek(Long studioId, String dayOfWeek);
}
