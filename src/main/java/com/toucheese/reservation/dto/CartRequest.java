package com.toucheese.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CartRequest(
	Long productId,
	Long studioId,
	Long memberId,
	Integer totalPrice,
	LocalDate createDate,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	LocalTime createTime,
	Integer personnel,
	List<Long> addOptions
) {
}