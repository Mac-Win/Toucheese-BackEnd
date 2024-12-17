package com.toucheese.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toucheese.member.dto.MemberContactInfoResponse;

public record ReservationResponse (
	Long id,
	String studioName,
	String productName,
	List<String> addOptionNames,
	Long totalPrice,
	Long personnel,
	LocalDate reservationDay,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	LocalTime reservaionTime,
	String status,
	MemberContactInfoResponse memberContactInfo
){
}
