package com.toucheese.admin.controller;

import java.time.LocalDate;

import com.toucheese.global.data.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toucheese.admin.dto.AdminReservationListResponse;
import com.toucheese.admin.dto.UpdateReservationStatusRequest;
import com.toucheese.admin.service.AdminReservationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/admin/reservations")
@RequiredArgsConstructor
@Tag(name = "관리자 예약 관리 API")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminReservationController {

	private final AdminReservationService adminReservationService;

	@GetMapping
	public ResponseEntity<?> findReservations(
		@RequestParam(required = false) String status,
		@RequestParam(required = false) LocalDate createDate,
		@RequestParam int page
	) {
		return ApiResponse.getObjectSuccess(adminReservationService.findReservations(status, createDate, page));
	}

	@PutMapping("/{reservationId}/status")
	public ResponseEntity<?> updateReservationStatus(
		@PathVariable Long reservationId,
		@RequestBody UpdateReservationStatusRequest request
	) {
		adminReservationService.updateReservationStatus(reservationId, request.status());
		return ApiResponse.updatedSuccess("예약 상태를 성공적으로 업데이트했습니다.");
	}
}