package com.toucheese.admin.dto;

import com.toucheese.reservation.entity.ReservationStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateReservationStatusRequest (
	@NotNull
	ReservationStatus status
){
}
