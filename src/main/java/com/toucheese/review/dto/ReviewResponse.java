package com.toucheese.review.dto;

import com.toucheese.review.entity.Review;
import lombok.Builder;

@Builder
public record ReviewResponse(
        Long id,
        String firstImage
){
    public static ReviewResponse of(Review review, String baseUrl) {
        String firstImage = (review.getReviewImages() != null && !review.getReviewImages().isEmpty())
                ? baseUrl + review.getReviewImages().get(0).getResizedPath()
                : null;

        return ReviewResponse.builder()
                .id(review.getId())
                .firstImage(firstImage)
                .build();
    }
}
