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
        List<String> reviewImages
) {
    public static ReviewDTO of(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .reviewImages(review.getReviewImages().stream()
                        .map(ReviewImage::getUrl)
                        .collect(Collectors.toList()))
                .build();

    }
}