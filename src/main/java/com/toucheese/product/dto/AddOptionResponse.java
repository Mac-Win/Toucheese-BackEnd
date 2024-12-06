package com.toucheese.product.dto;

import com.toucheese.product.entity.ProductAddOption;

import lombok.Builder;

@Builder
public record AddOptionResponse(
	Long id,
	Integer price,
	String name
) {

	public static AddOptionResponse of(ProductAddOption productAddOption) {
		return AddOptionResponse.builder()
			.id(productAddOption.getAddOption().getId())
			.name(productAddOption.getAddOption().getAddOptionName())
			.price(productAddOption.getAddOptionPrice())
			.build();
	}
}
