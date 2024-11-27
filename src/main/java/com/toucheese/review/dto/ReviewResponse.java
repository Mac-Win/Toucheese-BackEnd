package com.toucheese.review.dto;

import com.toucheese.review.entity.Review;
import lombok.Builder;

@Builder
public record ReviewResponse(
        Long id,
        String firstImage
){
    public static ReviewResponse of(Review review) {
        String firstImage = (review.getReviewImages() != null && !review.getReviewImages().isEmpty())
                ? review.getReviewImages().get(0).getUrl()
                : null;

        return ReviewResponse.builder()
                .id(review.getId())
                .firstImage(firstImage)
                .build();
    }
}
