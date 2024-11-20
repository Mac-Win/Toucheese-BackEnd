package com.toucheese.studio.dto;

import com.toucheese.studio.entity.Studio;
import lombok.Builder;

@Builder
public record StudioResponse(
    String name,
    String profileImage,
    Integer price,
    Float rating
) {

    public static StudioResponse of(Studio studio) {
        return builder()
                .name(studio.getName())
                .profileImage(studio.getProfileImage())
                .price(studio.getPrice())
                .rating(studio.getRating())
                .build();
    }
}
