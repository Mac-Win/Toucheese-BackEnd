package com.toucheese.review.controller;

import com.toucheese.review.dto.ReviewDetailResponse;
import com.toucheese.review.dto.ReviewResponse;
import com.toucheese.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/studios/{studioId}")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public List<ReviewResponse> getStudioReviews(@PathVariable("studioId") Long studioId) {
        return reviewService.getReviewsByStudioId(studioId);
    }

    @GetMapping("/reviews/{reviewId}")
    public ReviewDetailResponse findStudioReviewDetail(@PathVariable("reviewId") Long reviewId) {
        return reviewService.findReviewById(reviewId);
    }

    @GetMapping("/products/{productId}/reviews")
    public List<ReviewResponse> getProductReviews(@PathVariable("productId") Long productId) {
        return reviewService.getReviewsByProductId(productId);
    }
}
