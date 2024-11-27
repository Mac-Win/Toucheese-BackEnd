package com.toucheese.review.controller;

import com.toucheese.review.dto.ReviewDTO;
import com.toucheese.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studios/{studioId}")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public List<ReviewDTO> getStudioReviews(@PathVariable("studioId") Long studioId) {
        return reviewService.getReviewsByStudioId(studioId);
    }

    @GetMapping("/reviews/{reviewId}")
    public ReviewDTO getStudioReviewDetail(@PathVariable("reviewId") Long reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @GetMapping("/products/{productId}/reviews")
    public List<ReviewDTO> getProductReviews(@PathVariable("productId") Long productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    @GetMapping("/products/{productId}/reviews/{reviewId}")
    public ReviewDTO getProductReviewDetail(@PathVariable("studioId") Long studioId,
                                            @PathVariable("productId") Long productId,
                                            @PathVariable("reviewId") Long reviewId) {
        return reviewService.getReviewByProductId(studioId, productId, reviewId);
    }
}
