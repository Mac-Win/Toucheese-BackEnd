package com.toucheese.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.toucheese.reservation.entity.Reservation;

import lombok.Builder;

@Builder
public record CartResponse (
	Long studioId,
	Long productId,
	Integer personnel,
	LocalDate createDate,
	LocalTime createTime,
	Integer totalPrice
){
	public static CartResponse of (Reservation reservation) {
		return CartResponse.builder()
			.studioId(reservation.getStudio().getId())
			.productId(reservation.getProduct().getId())
			.personnel(reservation.getPersonnel())
			.createDate(reservation.getCreateDate())
			.createTime(reservation.getCreateTime())
			.totalPrice(reservation.getTotalPrice())
			.build();
	}
}
