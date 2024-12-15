package com.toucheese.cart.dto;

public record SelectAddOptionResponse(
	Long selectOptionId,             //
	String selectOptionName,       // 옵션 이름
	Integer selectOptionPrice      // 옵션 가격
) {
}