package com.toucheese.review.service;

import com.toucheese.global.config.ImageConfig;
import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.review.dto.ReviewDetailResponse;
import com.toucheese.review.dto.ReviewResponse;
import com.toucheese.review.entity.Review;
import com.toucheese.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQueryService {

    private final ImageConfig imageConfig;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ToucheeseBadRequestException(reviewId + "번 리뷰가 없습니다."));
    }

    @Transactional(readOnly = true)
    public ReviewDetailResponse findReviewDetailById(Long reviewId) {
        Review review = findReviewById(reviewId);
        return ReviewDetailResponse.of(review, imageConfig.getSourceImageBaseUrl());
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByStudioId(Long studioId) {
        List<Review> reviews = reviewRepository.findAllByStudioId(studioId);

        return reviews.stream()
                .map(review -> ReviewResponse.of(review, imageConfig.getResizedImageBaseUrl()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);

        return reviews.stream()
                .map(review -> ReviewResponse.of(review, imageConfig.getResizedImageBaseUrl()))
                .toList();
    }
}
