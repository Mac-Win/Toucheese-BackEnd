package com.toucheese.admin.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.admin.dto.AdminReservationListResponse;
import com.toucheese.reservation.entity.ReservationStatus;
import com.toucheese.reservation.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminReservationService {

	private final ReservationService reservationService;

	private static final int PAGE_SIZE = 10;

	@Transactional(readOnly = true)
	public Page<AdminReservationListResponse> findReservations(String status, LocalDate createDate, int page) {
		ReservationStatus reservationStatus = ReservationStatus.valueOf(status);
		Pageable pageable = createPageRequest(page);

		return reservationService.findReservationsByStatusAndDate(reservationStatus, createDate, pageable)
			.map(AdminReservationListResponse::of);
	}

	@Transactional
	public void updateReservationStatus(Long reservationId, ReservationStatus newStatus) {
		reservationService.changeReservationStatus(reservationId, newStatus);
	}

	private Pageable createPageRequest(int page) {
		return PageRequest.of(page, PAGE_SIZE);
	}
}
