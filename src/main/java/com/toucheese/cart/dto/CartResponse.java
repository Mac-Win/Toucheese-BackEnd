package com.toucheese.cart.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toucheese.product.dto.AddOptionResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record CartResponse(
	Long cartId,
	String studioImage,
	String studioName,          // 스튜디오 이름
	String productImage,        // 상품이미지
	String productName,         // 상품 이름
	Integer productStandard,    // 상품 기준 인원
	Integer productPrice,       // 상품 가격
	Integer personnel,          // 예약 인원
	LocalDate reservationDate,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")// 예약 날짜
	@Schema(type = "string", example = "19:00")
	LocalTime reservationTime,  // 예약 시간
	Integer totalPrice,         // 전체 가격
	List<SelectAddOptionResponse> selectAddOptions, // 선택한 추가 옵션 정보
	List<AddOptionResponse> addOptions
) {
}
