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
                .map(review -> {
                    String firstImage = (review.getReviewImages() != null && !review.getReviewImages().isEmpty())
                            ? review.getReviewImages().get(0).getUrl()
                            : null;

                    ReviewDTO reviewDTO = ReviewDTO.of(review);
                    return new ReviewDTO(
                            reviewDTO.id(),
                            reviewDTO.content(),
                            reviewDTO.rating(),
                            List.of(firstImage)
                    );
                })
                .collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException(("리뷰 없음" + reviewId)));

        return ReviewDTO.of(review);
    }

    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);

        return reviews.stream()
                .map(review -> {
                    String firstImage = (review.getReviewImages() != null && !review.getReviewImages().isEmpty())
                            ? review.getReviewImages().get(0).getUrl()
                            : null;

                    ReviewDTO reviewDTO = ReviewDTO.of(review);
                    return new ReviewDTO(
                            reviewDTO.id(),
                            reviewDTO.content(),
                            reviewDTO.rating(),
                            List.of(firstImage)
                    );
                })
                .collect(Collectors.toList());
    }

    public ReviewDTO getReviewByProductId(Long studioId, Long productId, Long reviewId) {
        Review review = reviewRepository.findByStudioIdAndProductIdAndId(studioId, productId, reviewId)
                .orElseThrow(()-> new IllegalArgumentException("리뷰 없어요"));

        return ReviewDTO.of(review);
    }



}
