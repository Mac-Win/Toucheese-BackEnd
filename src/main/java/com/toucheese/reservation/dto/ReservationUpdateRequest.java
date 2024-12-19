package com.toucheese.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toucheese.reservation.entity.ReservationStatus;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReservationUpdateRequest(
	LocalDate createDate,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	@Schema(type = "string", example = "19:00")
	LocalTime createTime,
	@Schema(example = "예약취소")
	ReservationStatus status
) {
}
