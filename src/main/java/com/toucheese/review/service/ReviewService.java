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

}
