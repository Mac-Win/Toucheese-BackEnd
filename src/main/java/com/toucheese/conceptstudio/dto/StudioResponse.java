package com.toucheese.conceptstudio.dto;

import com.toucheese.studio.entity.Studio;
import lombok.Builder;

import java.util.List;

@Builder
public record StudioResponse(
        Long id,
        String name,
        String profileImage,
        Float rating,
        Integer price,
        List<String> imageUrls
) {

    public static StudioResponse of(Studio studio, String baseUrl) {
        return builder()
                .id(studio.getId())
                .name(studio.getName())
                .profileImage(baseUrl + studio.getProfileImage())
                .rating(studio.getRating())
                .price(studio.getPrice())
                .imageUrls(studio.getStudioImages().stream()
                        .map(studioImage -> baseUrl + studioImage.getResizedPath())
                        .toList()
                )
                .build();
    }
}