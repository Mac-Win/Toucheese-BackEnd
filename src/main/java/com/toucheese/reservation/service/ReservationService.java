package com.toucheese.reservation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.toucheese.product.entity.Product;
import com.toucheese.product.entity.ProductAddOption;
import com.toucheese.product.service.ProductService;
import com.toucheese.reservation.dto.ReservationRequest;
import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationProductAddOption;
import com.toucheese.reservation.repository.ReservationRepository;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.service.StudioService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final StudioService studioService;
	private final ProductService productService;

	@Transactional
	public void createReservation(ReservationRequest reservationRequest) {
		Product product = productService.findProductById(reservationRequest.productId());

		Studio studio = studioService.findStudioById(reservationRequest.studioId());

		List<ProductAddOption> productAddOptions = productService.findProductAddOptionsByProductIdAndAddOptionIds(
			reservationRequest.productId(),
			reservationRequest.addOptions()
		);

		List<ReservationProductAddOption> reservationProductAddOptions = productAddOptions.stream()
			.map(option -> new ReservationProductAddOption(option, option.getAddOptionPrice()))
			.collect(Collectors.toList());

		Reservation reservation = Reservation.builder()
			.product(product)
			.studio(studio)
			.totalPrice(reservationRequest.totalPrice())
			.name(reservationRequest.name())
			.phone(reservationRequest.phone())
			.createDate(reservationRequest.createDate())
			.createTime(reservationRequest.createTime())
			.personnel(reservationRequest.personnel())
			.reservationProductAddOptions(reservationProductAddOptions)
			.build();

		reservationRepository.save(reservation);
	}
}
