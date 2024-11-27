package com.toucheese.product.dto;

import com.toucheese.product.entity.ProductAddOption;
import lombok.Builder;

@Builder
public record AddOptionResponse(
        String name,
        Integer price
) {

    public static AddOptionResponse of(ProductAddOption productAddOption) {
        return AddOptionResponse.builder()
                .name(productAddOption.getAddOption().getAddOptionName())
                .price(productAddOption.getAddOptionPrice())
                .build();
    }
}
