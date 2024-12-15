package com.toucheese.cart.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CheckoutCartItemsResponse (
	Long cartId,
	String studioName,          // 스튜디오 이름
	String productImage,        // 상품이미지
	String productName,         // 상품 이름
	Integer productPrice,		// 상품 가격
	Integer personnel,          // 예약 인원
	LocalDate reservationDate,  // 예약 날짜
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	LocalTime reservationTime,  // 예약 시간
	Integer totalPrice,         // 상품 1개 전체 가격
	List<SelectAddOptionResponse> selectAddOptions // 선택한 추가 옵션 정보
) {
}