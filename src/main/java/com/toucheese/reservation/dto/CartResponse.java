package com.toucheese.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CartResponse(
	Long cartId,
	String studioImage,
	String studioName,          // 스튜디오 이름
	String productImage,        // 상품이미지
	String productName,         // 상품 이름
	Integer personnel,          // 예약 인원
	LocalDate reservationDate,  // 예약 날짜
	LocalTime reservationTime,  // 예약 시간
	Integer totalPrice,         // 전체 가격
	List<AddOptionResponse> addOptions // 추가 옵션 정보
) {
	public record AddOptionResponse(
		String optionName,       // 옵션 이름
		Integer optionPrice      // 옵션 가격
	) {}
}
