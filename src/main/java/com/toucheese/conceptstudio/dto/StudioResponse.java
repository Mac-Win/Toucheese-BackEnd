package com.toucheese.conceptstudio.dto;

import com.toucheese.image.entity.StudioImage;
import com.toucheese.studio.entity.Studio;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record StudioResponse(
    Long id,
    String name,
    String profileImage,
    Float rating,
    Integer price,
    List<String> imageUrls
) {

    public static StudioResponse of(Studio studio) {
        return builder()
            .id(studio.getId())
            .name(studio.getName())
            .profileImage(studio.getProfileImage())
            .rating(studio.getRating())
            .price(studio.getPrice())
            .imageUrls(studio.getStudioImages().stream()
                .map(StudioImage::getUrl)
                .collect(Collectors.toList())
            )
            .build();
    }
}