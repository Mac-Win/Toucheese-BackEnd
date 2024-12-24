package com.toucheese.image.repository;

import com.toucheese.image.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
