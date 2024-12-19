package com.toucheese.image.repository;

import com.toucheese.image.entity.QuestionImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionImageRepository extends JpaRepository<QuestionImage, Long> {
    Optional<QuestionImage> findByUploadFilename(String uploadFilename);
}
