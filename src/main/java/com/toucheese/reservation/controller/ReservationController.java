package com.toucheese.reservation.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toucheese.global.util.PrincipalUtils;
import com.toucheese.cart.dto.CartIdsRequest;
import com.toucheese.cart.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
@Tag(name = "예약 API")
@PreAuthorize("isAuthenticated()")
public class ReservationController {
	private final CartService cartService;

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
	@PostMapping("/reservations")
	public ResponseEntity<?> acceptReservationAfterPayment(Principal principal, @RequestBody CartIdsRequest cartIdsRequest) {

		Long memberId = PrincipalUtils.extractMemberId(principal);

		cartService.createReservationsFromCart(memberId, cartIdsRequest);
		return ResponseEntity.ok("결제가 완료되었습니다.");
	}



}