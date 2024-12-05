package com.toucheese.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationProductAddOption;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ReservationRequest(
	Long productId,
	Long studioId,
	Integer totalPrice,
	@NotBlank(message = "이름은 필수 값입니다.")
	String name,
	@NotBlank(message = "전화번호는 필수 값입니다.")
	String phone,
	LocalDate createDate,
	LocalTime createTime,
	Integer personnel,
	List<Long> addOptions
) {

	public static ReservationRequest of(Reservation reservation) {
		return ReservationRequest.builder()
			.productId(reservation.getProduct().getId())
			.studioId(reservation.getStudio().getId())
			.totalPrice(reservation.getTotalPrice())
			.name(reservation.getName())
			.phone(reservation.getPhone())
			.createDate(reservation.getCreateDate())
			.createTime(reservation.getCreateTime())
			.personnel(reservation.getPersonnel())
			.addOptions(reservation.getReservationProductAddOptions() != null ?
				reservation.getReservationProductAddOptions().stream()
					.map(ReservationProductAddOption::getId)
					.collect(Collectors.toList()) : Collections.emptyList())
			.build();
	}
}
