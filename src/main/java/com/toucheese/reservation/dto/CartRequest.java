package com.toucheese.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CartRequest(
	Long productId,
	Long studioId,
	Long memberId,
	Integer totalPrice,
	LocalDate createDate,
	LocalTime createTime,
	Integer personnel,
	List<Long> addOptions
) {
}