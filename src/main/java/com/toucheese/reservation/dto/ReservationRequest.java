package com.toucheese.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationProductAddOption;

import lombok.Builder;

@Builder
public record ReservationRequest(
	Long productId,
	Long studioId,
	Long memberId,
	Integer totalPrice,
	LocalDate createDate,
	LocalTime createTime,
	Integer personnel,
	List<Long> addOptions
) {

	public static ReservationRequest of(Reservation reservation) {
		return ReservationRequest.builder()
			.productId(reservation.getProduct().getId())
			.studioId(reservation.getStudio().getId())
			.memberId(reservation.getMember().getId())
			.totalPrice(reservation.getTotalPrice())
			.createDate(reservation.getCreateDate())
			.createTime(reservation.getCreateTime())
			.personnel(reservation.getPersonnel())
			.addOptions(reservation.getReservationProductAddOptions() != null ?
				reservation.getReservationProductAddOptions().stream()
					.map(ReservationProductAddOption::getId)
					.collect(Collectors.toList()) : Collections.emptyList())
			.build();
	}
}
