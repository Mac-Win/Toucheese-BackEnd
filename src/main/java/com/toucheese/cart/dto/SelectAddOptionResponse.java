package com.toucheese.cart.dto;

import com.toucheese.reservation.entity.ReservationProductAddOption;

import lombok.Builder;

@Builder
public record SelectAddOptionResponse(
	Long selectOptionId,             //
	String selectOptionName,       // 옵션 이름
	Integer selectOptionPrice      // 옵션 가격
) {

	public static SelectAddOptionResponse of(ReservationProductAddOption reservationProductAddOption) {
		return builder()
			.selectOptionId(reservationProductAddOption.getId())
			.selectOptionName(reservationProductAddOption.getProductAddOption().getAddOption().getAddOptionName())
			.selectOptionPrice(reservationProductAddOption.getAddPrice())
			.build();
	}
}