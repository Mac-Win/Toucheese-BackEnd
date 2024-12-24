package com.toucheese.image.repository;

import com.toucheese.image.entity.FacilityImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityImageRepository extends JpaRepository<FacilityImage, Long> {
}
