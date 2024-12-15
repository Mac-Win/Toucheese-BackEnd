package com.toucheese.reservation.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.toucheese.product.entity.ProductAddOption;
import com.toucheese.product.service.ProductService;
import com.toucheese.cart.entity.Cart;
import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationProductAddOption;
import com.toucheese.reservation.entity.ReservationStatus;
import com.toucheese.reservation.repository.ReservationRepository;
import com.toucheese.global.util.CsvUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final ProductService productService;

	@Transactional
	public void createReservationsFromCarts(List<Cart> carts) {
		List<Reservation> reservations = carts.stream().map(cart -> {
			List<Long> addOptionIds = CsvUtils.fromCsv(cart.getAddOptions());

			// ProductAddOption 리스트 조회
			List<ProductAddOption> productAddOptions = productService.findProductAddOptionsByProductIdAndAddOptionIds(
				cart.getProduct().getId(), addOptionIds
			);

			// ReservationProductAddOption 생성
			List<ReservationProductAddOption> reservationProductAddOptions = productAddOptions.stream()
				.map(productAddOption -> new ReservationProductAddOption(productAddOption, productAddOption.getAddOptionPrice()))
				.collect(Collectors.toList());

			// Reservation 생성
			return Reservation.builder()
				.product(cart.getProduct())
				.studio(cart.getStudio())
				.member(cart.getMember())
				.totalPrice(cart.getTotalPrice())
				.createDate(cart.getCreateDate())
				.createTime(cart.getCreateTime())
				.personnel(cart.getPersonnel())
				.reservationProductAddOptions(reservationProductAddOptions)
				.status(ReservationStatus.예약접수)
				.reservationCompletedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
				.build();
		}).collect(Collectors.toList());

		reservationRepository.saveAll(reservations);
	}
}