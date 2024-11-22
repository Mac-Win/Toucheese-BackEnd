package com.toucheese.studio.dto;

import com.toucheese.image.entity.Image;
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
            .imageUrls(studio.getImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList())
            )
            .build();
    }
}