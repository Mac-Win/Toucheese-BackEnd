package com.toucheese.review.service;

import com.toucheese.review.dto.ReviewDTO;
import com.toucheese.review.entity.Review;
import com.toucheese.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<ReviewDTO> getReviewsByStudioId(Long studioId) {
        List<Review> reviews = reviewRepository.findAllByStudioId(studioId);

        return reviews.stream()
                .map(ReviewDTO::of)
                .collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException(("리뷰 없음" + reviewId)));

        return ReviewDTO.of(review);

    }
}
