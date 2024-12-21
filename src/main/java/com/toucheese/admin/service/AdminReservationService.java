package com.toucheese.admin.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.admin.dto.AdminReservationListResponse;
import com.toucheese.global.util.PageUtils;
import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationStatus;
import com.toucheese.reservation.service.ReservationReadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminReservationService {

	private final ReservationReadService reservationReadService;

	@Transactional(readOnly = true)
	public Page<AdminReservationListResponse> findReservations(ReservationStatus status, LocalDate createDate,
		int page) {
		Pageable pageable = PageUtils.createPageable(page);

		return reservationReadService.findReservationsByStatusAndDate(status, createDate, pageable)
			.map(AdminReservationListResponse::of);
	}

	@Transactional
	public void updateReservationStatus(Long reservationId, ReservationStatus newStatus) {
		Reservation reservation = reservationReadService.findReservationById(reservationId);

		reservation.updateStatus(newStatus);
	}
}
