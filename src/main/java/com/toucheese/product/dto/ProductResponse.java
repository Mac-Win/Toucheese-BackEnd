package com.toucheese.product.dto;

import com.toucheese.product.entity.Product;
import lombok.Builder;

@Builder
public record ProductResponse(
        Long id,
        String name,
        String description,
        String productImage,
        Integer reviewCount,
        Integer standard,
        Integer price
) {

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .productImage(product.getProductImage())
                .reviewCount(product.getReviews().size())
                .standard(product.getStandard())
                .price(product.getPrice())
                .build();
    }
}
