package com.toucheese.review.repository;

import com.toucheese.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByStudioId(Long studioId);

    List<Review> findAllByProductId(Long productId);

}
