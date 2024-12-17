package com.toucheese.admin.dto;

import java.time.LocalDate;
import java.util.List;

import com.toucheese.cart.dto.SelectAddOptionResponse;
import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationStatus;

import lombok.Builder;

@Builder
public record AdminReservationListResponse (

	Long reservationId,
	String customerName,
	String customerPhone,
	String studioName,
	ReservationStatus status,            // 예약 상태
	LocalDate createDate,  // 예약 날짜
	Integer personnel,
	Integer totalPrice,
	String productName,
	Integer productPrice,
	List<SelectAddOptionResponse> selectAddOptions
) {
	public static AdminReservationListResponse of(Reservation reservation) {
		return builder()
			.reservationId(reservation.getId())
			.customerName(reservation.getMember().getName())
			.customerPhone(reservation.getMember().getPhone())
			.studioName(reservation.getStudio().getName())
			.status(reservation.getStatus())
			.createDate(reservation.getCreateDate())
			.personnel(reservation.getPersonnel())
			.totalPrice(reservation.getTotalPrice())
			.productName(reservation.getProduct().getName())
			.productPrice(reservation.getProduct().getPrice())
			.selectAddOptions(
				reservation.getReservationProductAddOptions().stream()
					.map(SelectAddOptionResponse::of)
					.toList()
			)
			.build();
	}
}