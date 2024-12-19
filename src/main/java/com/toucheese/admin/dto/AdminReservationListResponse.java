package com.toucheese.admin.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toucheese.cart.dto.SelectAddOptionResponse;
import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AdminReservationListResponse (

	Long reservationId,
	String customerName,
	String customerPhone,
	String studioName,
	ReservationStatus status,
	LocalDate createDate,	// 예약 날짜
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	@Schema(type = "string", example = "19:00")
	LocalTime createTime,	// 예약 시간
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
			.createTime(reservation.getCreateTime())
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