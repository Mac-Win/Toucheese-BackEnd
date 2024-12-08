package com.toucheese.reservation.service;

import org.springframework.stereotype.Service;

import com.toucheese.member.entity.Member;
import com.toucheese.member.service.MemberService;
import com.toucheese.product.entity.Product;
import com.toucheese.product.service.ProductService;
import com.toucheese.reservation.dto.CartRequest;
import com.toucheese.reservation.entity.Cart;
import com.toucheese.reservation.repository.CartRepository;
import com.toucheese.reservation.util.CsvUtils;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.service.StudioService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
	private final StudioService studioService;
	private final ProductService productService;
	private final MemberService memberService;

	@Transactional
	public void createCart(CartRequest cartRequest) {

		Product product = productService.findProductById(cartRequest.productId());
		Studio studio = studioService.findStudioById(cartRequest.studioId());
		Member member = memberService.findMemberById(cartRequest.memberId());

		String addOptionsCsv = CsvUtils.toCsv(cartRequest.addOptions());

		Cart cart = Cart.builder()
			.product(product)
			.studio(studio)
			.member(member)
			.totalPrice(cartRequest.totalPrice())
			.createDate(cartRequest.createDate())
			.createTime(cartRequest.createTime())
			.personnel(cartRequest.personnel())
			.addOptions(addOptionsCsv) // 변환된 AddOption 리스트 설정
			.build();

		cartRepository.save(cart);
	}
}
