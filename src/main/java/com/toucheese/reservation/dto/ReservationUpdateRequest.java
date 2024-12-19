package com.toucheese.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toucheese.reservation.entity.ReservationStatus;

public record ReservationUpdateRequest(
	LocalDate createDate,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	LocalTime createTime,
	ReservationStatus status
) {
}
