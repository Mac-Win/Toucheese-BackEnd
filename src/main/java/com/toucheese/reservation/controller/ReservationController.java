package com.toucheese.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toucheese.reservation.dto.CartRequest;
import com.toucheese.reservation.dto.ReservationRequest;
import com.toucheese.reservation.service.CartService;
import com.toucheese.reservation.service.ReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/reservations")
@RequiredArgsConstructor
@Tag(name = "예약 API")
public class ReservationController {
	private final ReservationService reservationService;
	final private CartService cartService;

	@Operation(
		summary = "예약 정보 저장 기능",
		description = """
			필요한 예약 정보 : <br>
			productId = 상품 id, <br>
			studioId = 스튜디오 id, <br>
			memberId = 회원 id, <br>
			totalPrice = 상품 + 추가상품의 총금액, <br>
			createDate = 예약날짜, <br>
			createTime = 예약시간 예시 -> 14:30, <br>
			personnel = 예약 인원수, <br>
			addOptions = 추가 상품에서 id는 AddOptionId 필요 <br>
			<br>
			예시 데이터 : <br>
			{<br>
			productId: 1, <br>
			studioId: 1, <br>
			memberId: 1, <br>
			totalPrice: 100000, <br>
			createDate: 2024-12-04, <br>
			createTime: 9:30, <br>
			personnel: 2, <br>
			addOptions: [1, 2, 3] <br>
			}
			"""
	)
	@PostMapping()
	public ResponseEntity<ReservationRequest> reservationCreate(
		@Valid @RequestBody ReservationRequest reservationRequest) {

		reservationService.createReservation(reservationRequest);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(description = """
		{
		 <br>   "productId": 1,
		 <br>   "studioId": 1,
		 <br>   "memberId": 1,
		 <br>   "totalPrice": 100000,
		 <br>   "createDate": "2024-12-04",
		 <br>   "createTime": "09:30",
		 <br>   "personnel": 2,
		 <br>   "addOptions": [1, 2, 3]
		 <br> }""")
	@PostMapping("/cart")
	public ResponseEntity<CartRequest> cartCreate(
		@Valid @RequestBody CartRequest cartRequest) {

		cartService.createCart(cartRequest);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}