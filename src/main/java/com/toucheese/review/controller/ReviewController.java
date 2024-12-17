package com.toucheese.review.controller;

import com.toucheese.global.data.ApiResponse;
import com.toucheese.review.dto.ReviewDetailResponse;
import com.toucheese.review.dto.ReviewResponse;
import com.toucheese.review.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/studios/{studioId}")
@Tag(name = "리뷰 API", description = "스튜디오 리뷰 목록 조회, 리뷰 상세 조회, 상품별 리뷰 조회")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviews")
    @Operation(summary = "스튜디오 리뷰 목록 조회", description = "리뷰 탭 클릭 시 해당 스튜디오 리뷰 전체 조회")
    public ResponseEntity<?> getStudioReviews(@PathVariable("studioId") Long studioId) {
        return ApiResponse.getObjectSuccess(reviewService.getReviewsByStudioId(studioId));
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "특정 리뷰 상세 조회", description = "리뷰 사진 클릭 시 해당 리뷰 조회")
    public ResponseEntity<?> findStudioReviewDetail(@PathVariable("reviewId") Long reviewId) {
        return ApiResponse.getObjectSuccess(reviewService.findReviewById(reviewId));
    }

    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "특정 상품 리뷰 목록 조회", description = "상품 리뷰 클릭시 해당 상품 리뷰 조회")
    public ResponseEntity<?> getProductReviews(@PathVariable("productId") Long productId) {
        return ApiResponse.getObjectSuccess(reviewService.getReviewsByProductId(productId));
    }
}
