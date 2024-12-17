package com.toucheese.review.dto;

import com.toucheese.image.entity.ReviewImage;
import com.toucheese.review.entity.Review;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ReviewDetailResponse(
        Long id,
        String content,
        Float rating,
        List<String> reviewImages
) {
    public static ReviewDetailResponse of(Review review, String baseUrl) {
        return ReviewDetailResponse.builder()
                .id(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .reviewImages(review.getReviewImages().stream()
                        .map(reviewImage -> baseUrl + reviewImage.getOriginalPath())
                        .toList()
                )
                .build();

    }
}