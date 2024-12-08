package com.toucheese.reservation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.toucheese.member.entity.Member;
import com.toucheese.member.service.MemberService;
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
	private final MemberService memberService;

	@Transactional
	public void createReservation(ReservationRequest reservationRequest) {
		Product product = productService.findProductById(reservationRequest.productId());

		Studio studio = studioService.findStudioById(reservationRequest.studioId());

		Member member = memberService.findMemberById(reservationRequest.memberId());

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
			.member(member)
			.totalPrice(reservationRequest.totalPrice())
			.createDate(reservationRequest.createDate())
			.createTime(reservationRequest.createTime())
			.personnel(reservationRequest.personnel())
			.reservationProductAddOptions(reservationProductAddOptions)
			.build();

		reservationRepository.save(reservation);
	}
}
