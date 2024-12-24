package com.toucheese.image.repository;

import com.toucheese.image.entity.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageInfoRepository extends JpaRepository<ImageInfo, Long> {
    Optional<ImageInfo> findByUploadFilename(String uploadFilename);
}
