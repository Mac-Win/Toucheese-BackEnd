package com.toucheese.cart.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CartRequest(
	@NotNull(message = "상품 id는 필수입니다.")
	Long productId,
	@NotNull(message = "스튜디오 id는 필수입니다.")
	Long studioId,
	Long memberId,
	@NotNull(message = "가격은 필수입니다.")
	Integer totalPrice,
	LocalDate createDate,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	@Schema(type = "string", example = "19:00")
	LocalTime createTime,
	@NotNull(message = "인원은 필수입니다.")
	Integer personnel,
	List<Long> addOptions
) {
}