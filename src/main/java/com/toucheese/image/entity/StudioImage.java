package com.toucheese.image.entity;

import com.toucheese.studio.entity.Studio;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudioImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @Column(nullable = false)
    private String originalPath;

    @Column(nullable = false)
    private String resizedPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_info_id")
    private ImageInfo imageInfo;

    @Builder

    public StudioImage(Studio studio, String originalPath, String resizedPath, ImageInfo imageInfo) {
        this.studio = studio;
        this.originalPath = originalPath;
        this.resizedPath = resizedPath;
        this.imageInfo = imageInfo;
    }
}