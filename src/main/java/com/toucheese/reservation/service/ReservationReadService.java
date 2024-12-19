package com.toucheese.reservation.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.global.config.ImageConfig;
import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.global.util.PageUtils;
import com.toucheese.reservation.dto.ReservationResponse;
import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationStatus;
import com.toucheese.reservation.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationReadService {
	private final ReservationRepository reservationRepository;
	private final ImageConfig imageConfig;

	@Transactional(readOnly = true)
	public Reservation findReservationById(Long reservationId) {
		return reservationRepository.findById(reservationId)
			.orElseThrow(() -> new ToucheeseBadRequestException("Reservation not found with ID: " + reservationId));
	}

	@Transactional(readOnly = true)
	public Page<Reservation> findReservationsByStatusAndDate(ReservationStatus status, LocalDate createDate,
		Pageable pageable) {
		return reservationRepository.findReservations(status, createDate, pageable);
	}

	@Transactional(readOnly = true)
	public Page<ReservationResponse> findPagedReservationsByMemberId(Long memberId, int page) {
		Pageable pageable = PageUtils.createPageable(page);
		return reservationRepository.findPagedReservationsByMemberId(memberId, pageable)
			.map(reservation -> ReservationResponse.of(reservation, imageConfig.getResizedImageBaseUrl()));
	}

	@Transactional(readOnly = true)
	public Reservation findReservationByIdAndMemberId(Long reservationId, Long memberId) {
		return reservationRepository.findByIdAndMemberId(reservationId, memberId)
			.orElseThrow(() -> new ToucheeseBadRequestException("해당 예약이 존재하지 않거나 접근 권한이 없습니다."));
	}
}
