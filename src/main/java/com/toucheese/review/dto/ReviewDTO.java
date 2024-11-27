package com.toucheese.review.dto;

import com.toucheese.image.entity.ReviewImage;
import com.toucheese.review.entity.Review;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ReviewDTO(
        Long id,
        String content,
        Float rating,
        List<String> review_Images
) {
    public static ReviewDTO of(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .review_Images(review.getReview_Images().stream()
                        .map(ReviewImage::getUrl)
                        .collect(Collectors.toList()))
                .build();

    }
}