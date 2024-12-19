package com.toucheese.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ReservationResponse (
	Long reservationId,
	Long studioId,
	String studioName,
	String studioImage,
	String productName,
	LocalDate createDate,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	@Schema(type = "string", example = "19:00")
	LocalTime createTime,
	ReservationStatus status
){
	public static ReservationResponse of(Reservation reservation, String baseUrl) {
		return builder()
			.reservationId(reservation.getId())
			.studioId(reservation.getStudio().getId())
			.studioName(reservation.getStudio().getName())
			.studioImage(baseUrl + reservation.getStudio().getProfileImage())
			.productName(reservation.getProduct().getName())
			.createDate(reservation.getCreateDate())
			.createTime(reservation.getCreateTime())
			.status(reservation.getStatus())
			.build();
	}
}
