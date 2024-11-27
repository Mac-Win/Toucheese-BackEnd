package com.toucheese.studio.dto;

import com.toucheese.product.dto.ProductResponse;
import com.toucheese.studio.entity.Studio;
import java.util.List;
import lombok.Builder;

@Builder
public record StudioDetailResponse(
    Long id,
    String name,
    String profileImage,
    String description,
    Float rating,
    Integer reviewCount,
    String operationHour,
    String address,
    String notice,
    List<ProductResponse> products
) {

    public static StudioDetailResponse of(Studio studio) {
        return builder()
                .id(studio.getId())
                .name(studio.getName())
                .profileImage(studio.getProfileImage())
                .description(studio.getDescription())
                .rating(studio.getRating())
                .reviewCount(studio.getReviews().size())
                .operationHour(studio.getOperationHour())
                .address(studio.getAddress())
                .notice(studio.getNotice())
                .products(studio.getProducts().stream()
                        .map(ProductResponse::of)
                        .toList()
                )
                .build();
    }
}