package com.toucheese.image.repository;

import com.toucheese.image.entity.StudioImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioImageRepository extends JpaRepository<StudioImage, Long> {
    Optional<StudioImage> findByUploadFilename(String uploadFilename);

}
