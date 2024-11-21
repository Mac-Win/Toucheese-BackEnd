package com.toucheese.studio.dto;

import com.toucheese.image.entity.Image;
import com.toucheese.studio.entity.Studio;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record StudioResponse(
    String name,
    String profileImage,
    Integer price,
    Float rating,
    List<String> imageUrls
) {

    public static StudioResponse of(Studio studio) {
        return builder()
            .name(studio.getName())
            .profileImage(studio.getProfileImage())
            .price(studio.getPrice())
            .rating(studio.getRating())
            .imageUrls(studio.getImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList())
            )
            .build();
    }
}