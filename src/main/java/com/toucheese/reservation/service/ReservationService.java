package com.toucheese.reservation.service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.product.entity.Product;
import com.toucheese.product.entity.ProductAddOption;
import com.toucheese.product.repository.ProductAddOptionRepository;
import com.toucheese.product.repository.ProductRepository;
import com.toucheese.reservation.dto.ReservationRequest;
import com.toucheese.reservation.entity.Reservation;
import com.toucheese.reservation.entity.ReservationProductAddOption;
import com.toucheese.reservation.repository.ReservationRepository;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.repository.StudioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final StudioRepository studioRepository;
	private final ProductRepository productRepository;
	private final ProductAddOptionRepository productAddOptionRepository;

	@Transactional
	public Reservation createReservation(ReservationRequest reservationRequest) {
		Product product = productRepository.findById(reservationRequest.productId())
			.orElseThrow(() -> new ToucheeseBadRequestException("Product not found"));

		Studio studio = studioRepository.findById(reservationRequest.studioId())
			.orElseThrow(() -> new ToucheeseBadRequestException("Studio not found"));

		List<ReservationProductAddOption> reservationProductAddOptions = reservationRequest.addOptions().stream()
			.map(addOptionRequest -> {
				ProductAddOption productAddOption = productAddOptionRepository.findById(addOptionRequest.id())
					.orElseThrow(() -> new ToucheeseBadRequestException("AddOption not found"));
				if (!product.getProductAddOptions().contains(productAddOption)) {
					throw new ToucheeseBadRequestException("Invalid AddOption for the selected product");
				}

				return new ReservationProductAddOption(productAddOption, addOptionRequest.addOptionPrice());
			})
			.collect(Collectors.toList());

		Reservation reservation = new Reservation(
			product,
			studio,
			reservationRequest.totalPrice(),
			reservationRequest.name(),
			reservationRequest.phone(),
			reservationRequest.createDate(),
			reservationRequest.createTime(),
			reservationRequest.personnel(),
			reservationProductAddOptions
		);

		return reservationRepository.save(reservation);
	}
}
