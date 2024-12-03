package com.toucheese.reservation.controller;

import java.time.LocalTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.product.entity.Product;
import com.toucheese.product.repository.ProductRepository;
import com.toucheese.reservation.dto.ReservationRequest;
import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.service.ReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/reservations")
@RequiredArgsConstructor
@Tag(name = "예약 API", description = "기능 : 예약 정보 저장")
public class ReservationController {
	private final ReservationService reservationService;
	private final ProductRepository productRepository;

	@PostMapping("")
	@Operation(
		summary = "예약 정보 저장 기능",
		description = "필요한 예약 정보 : <br>" +
			"productId = 상품 id, <br>" +
			"studioId = 스튜디오 id, <br>" +
			"totalPrice = 상품 + 추가상품의 총금액, <br>" +
			"name = 사용자 이름, <br>" +
			"phone = 사용자 전화번호, <br>" +
			"createDate = 예약날짜, <br>" +
			"createTime = 예약시간 예시 -> 14:30, <br>"+
			"personnel = 예약 인원수, <br>" +
			"addOptions = 추가 상품에서 id는 productAddOptionId 필요 <br>" +
			"<br>"
			+ "    예시 데이터 : <br>"
			+ "    {<br>"
			+ "    productId: 1, <br>"
			+ "    studioId: 1, <br>"
			+ "    totalPrice: 100000, <br>"
			+ "    name: 홍길동, <br>"
			+ "    phone: 01012345678, <br>"
			+ "    createDate: 2024-12-04, <br>"
			+ "    createTime: 09:30, <br>"
			+ "    personnel: 2, <br> "
			+ "    addOptions: [ <br>"
			+ "    <pre>"
			+ "			{ <br>"
			+ "          id: 1, << 이부분의 id는 productAddOptionId<br>"
			+ "          addOptionPrice: 20000 <br>"
			+ "         }, <br>"
			+ "         {<br>"
			+ "          id: 2, <br>"
			+ "          addOptionPrice: 30000 <br>"
			+ "         } <br>"
			+ "      ] </pre>"
			+ "    } <br>"

	)
	public ResponseEntity<ReservationRequest> reservationCreate(@RequestBody ReservationRequest reservationRequest) {
		String name = reservationRequest.name();
		String phone = reservationRequest.phone();
		if (name.isBlank() || phone.length() < 10 || phone.length() > 13) {
			throw new ToucheeseBadRequestException("Name or Phone is missing");
		}
		Reservation reservation = reservationService.createReservation(reservationRequest);

		Product product = productRepository.findById(reservationRequest.productId())
			.orElseThrow(() -> new ToucheeseBadRequestException("Product not found"));

		if (!reservationRequest.isValidAddOption(product)) {
			throw new ToucheeseBadRequestException("Invalid AddOption for the selected product");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(ReservationRequest.of(reservation));
	}
}
