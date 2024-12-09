package com.toucheese.reservation.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toucheese.reservation.dto.CartRequest;
import com.toucheese.reservation.dto.CartResponse;
import com.toucheese.reservation.dto.CartUpdateRequest;
import com.toucheese.reservation.dto.ReservationRequest;
import com.toucheese.reservation.service.CartService;
import com.toucheese.reservation.service.ReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/members")
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
	@PostMapping("/reservations")
	public ResponseEntity<ReservationRequest> reservationCreate(
		@Valid @RequestBody ReservationRequest reservationRequest) {

		reservationService.createReservation(reservationRequest);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "장바구니 저장 기능(회원)", description = """
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
	@PostMapping("/carts")
	public ResponseEntity<CartRequest> cartCreate(
		@Valid @RequestBody CartRequest cartRequest) {

		cartService.createCart(cartRequest);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "장바구니 목록 조회(회원)",
		description = """
        {
            "studioName": "스튜디오 이름",
            "productName": "상품 이름",
            "personnel": 예약 인원,
            "reservationDate": "예약 날짜",
            "reservationTime": "예약 시간",
            "totalPrice": 전체 가격,
            "addOptions": [
                {
                    "optionName": "옵션 이름",
                    "optionPrice": 옵션 가격
                }
            ]
        }
    """
	)
	@GetMapping("/carts/list")
	public ResponseEntity<List<CartResponse>> getCartList(Principal principal) {
		String memberId = principal.getName();

		List<CartResponse> cartList = cartService.getCartList(Long.parseLong(memberId));
		return ResponseEntity.ok(cartList);
	}

	@Operation(summary = "해당 장바구니 삭제", description = "해당하는 장바구니를 삭제합니다.")
	@DeleteMapping("/carts/{cartId}")
	public ResponseEntity<?> deleteCart(@PathVariable Long cartId, Principal principal) {

		String memberId = principal.getName();

		cartService.checkCartOwner(cartId, Long.parseLong(memberId));
		cartService.deleteCart(cartId);

		return ResponseEntity.ok("장바구니 항목이 삭제되었습니다.");
	}

	@Operation(summary = "장바구니 옵션 및 인원 변경", description = "장바구니의 옵션과 인원수를 수정합니다.")
	@PutMapping("/carts/{cartId}")
	public ResponseEntity<?> updateCart(@PathVariable Long cartId,
		@Valid @RequestBody CartUpdateRequest request, Principal principal) {
		String memberId = principal.getName();

		cartService.updateCart(cartId, request, Long.parseLong(memberId));
		return ResponseEntity.ok("장바구니가 성공적으로 업데이트되었습니다.");
	}
}