package com.toucheese.review.service;

import com.toucheese.image.entity.ImageType;
import com.toucheese.image.service.ImageService;
import com.toucheese.member.entity.Member;
import com.toucheese.member.service.MemberService;
import com.toucheese.product.entity.Product;
import com.toucheese.product.service.ProductService;
import com.toucheese.review.dto.ReviewRequest;
import com.toucheese.review.entity.Review;
import com.toucheese.review.repository.ReviewRepository;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.service.StudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewCommandService {

    private final ReviewRepository reviewRepository;

    private final MemberService memberService;
    private final StudioService studioService;
    private final ProductService productService;
    private final ImageService imageService;

    @Transactional
    public void createReview(ReviewRequest reviewRequest, Long memberId, Long studioId, Long productId) {
        Member member = memberService.findMemberById(memberId);
        Studio studio = studioService.findStudioById(studioId);
        Product product = productService.findProductById(productId);

        Review review = ReviewRequest.toEntity(reviewRequest, member, studio, product);
        review = reviewRepository.save(review);

        imageService.uploadImageWithDetails(reviewRequest.uploadFiles(), review.getId(), ImageType.REVIEW);
    }

}
