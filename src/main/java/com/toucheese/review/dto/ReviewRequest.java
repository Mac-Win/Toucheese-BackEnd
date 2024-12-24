package com.toucheese.review.dto;

import com.toucheese.member.entity.Member;
import com.toucheese.product.entity.Product;
import com.toucheese.review.entity.Review;
import com.toucheese.studio.entity.Studio;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record ReviewRequest(
        String content,
        Float rating,
        List<MultipartFile> uploadFiles
){

    public static Review toEntity(
            ReviewRequest reviewRequest,
            Member member,
            Studio studio,
            Product product
    ) {
        return Review.builder()
                .content(reviewRequest.content)
                .rating(reviewRequest.rating)
                .member(member)
                .studio(studio)
                .product(product)
                .build();
    }
}
