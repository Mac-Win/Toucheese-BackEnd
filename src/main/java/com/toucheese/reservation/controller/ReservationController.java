package com.toucheese.reservation.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toucheese.cart.dto.CartIdsRequest;
import com.toucheese.cart.service.CartService;
import com.toucheese.global.data.ApiResponse;
import com.toucheese.global.util.PrincipalUtils;
import com.toucheese.reservation.dto.ReservationResponse;
import com.toucheese.reservation.dto.ReservationUpdateRequest;
import com.toucheese.reservation.service.ReservationReadService;
import com.toucheese.reservation.service.ReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/members/reservations")
@RequiredArgsConstructor
@Tag(name = "사용자 예약 API")
@PreAuthorize("isAuthenticated()")
public class ReservationController {
	private final CartService cartService;
	private final ReservationService reservationService;
	private final ReservationReadService reservationReadService;

	@Operation(
		summary = "예약 기능",
		description = """
			선택한 장바구니를 결제하면 예약 테이블로 해당 데이터를 옮깁니다.
			```json
			{
			    "cartIds": "1, 2, 3"    << String 입니다.
			}
			"""
	)
	@PostMapping
	public ResponseEntity<?> acceptReservationAfterPayment(Principal principal,
		@RequestBody CartIdsRequest cartIdsRequest) {
		Long memberId = PrincipalUtils.extractMemberId(principal);

		cartService.createReservationsFromCart(memberId, cartIdsRequest);
		return ApiResponse.createdSuccess("결제가 완료되었습니다.");
	}

	@Operation(summary = "사용자 예약 조회",
		description = """
		createDate = 예약날짜,
		createTime = 예약시간""")
	@GetMapping
	public ResponseEntity<Page<ReservationResponse>> findReservations(Principal principal, @RequestParam int page) {
		Long memberId = PrincipalUtils.extractMemberId(principal);

		Page<ReservationResponse> reservations = reservationReadService.findPagedReservationsByMemberId(memberId, page);
		return ApiResponse.getObjectSuccess(reservations);
	}

	@Operation(summary = "사용자 예약 수정")
	@PutMapping("/{reservationId}")
	public ResponseEntity<?> updateReservationTime(
		Principal principal,
		@PathVariable Long reservationId,
		@RequestBody ReservationUpdateRequest request
	) {
		Long memberId = PrincipalUtils.extractMemberId(principal);

		reservationService.updateReservation(memberId, reservationId, request);
		return ApiResponse.updatedSuccess("예약 상태를 성공적으로 업데이트했습니다.");
	}

}