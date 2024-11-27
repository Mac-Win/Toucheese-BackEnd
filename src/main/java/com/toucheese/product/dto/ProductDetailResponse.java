package com.toucheese.product.dto;

import com.toucheese.product.entity.Product;
import java.util.List;
import lombok.Builder;

@Builder
public record ProductDetailResponse(
        Long id,
        String name,
        String description,
        String productImage,
        Integer reviewCount,
        Integer standard,
        Integer price,
        List<AddOptionResponse> addOptions
) {

    public static ProductDetailResponse of(Product product) {
        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .productImage(product.getProductImage())
                .reviewCount(product.getReviews().size())
                .standard(product.getStandard())
                .price(product.getPrice())
                .addOptions(product.getProductAddOptions().stream()
                        .map(AddOptionResponse::of)
                        .toList()
                )
                .build();
    }
}
