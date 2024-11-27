package com.toucheese.review.service;

import com.toucheese.review.dto.ReviewDetailResponse;
import com.toucheese.review.dto.ReviewResponse;
import com.toucheese.review.entity.Review;
import com.toucheese.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByStudioId(Long studioId) {
        List<Review> reviews = reviewRepository.findAllByStudioId(studioId);

        return reviews.stream()
                .map(ReviewResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException(("리뷰 없음" + reviewId)));

        return ReviewDetailResponse.of(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);

        return reviews.stream()
                .map(ReviewResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewByProductId(Long studioId, Long productId, Long reviewId) {
        Review review = reviewRepository.findByStudioIdAndProductIdAndId(studioId, productId, reviewId)
                .orElseThrow(()-> new IllegalArgumentException("리뷰 없어요"));

        return ReviewDetailResponse.of(review);
    }



}
