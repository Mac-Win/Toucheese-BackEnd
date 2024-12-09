package com.toucheese.reservation.service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.member.entity.Member;
import com.toucheese.member.service.MemberService;
import com.toucheese.product.entity.AddOption;
import com.toucheese.product.entity.Product;
import com.toucheese.product.entity.ProductAddOption;
import com.toucheese.product.service.ProductService;
import com.toucheese.reservation.dto.CartRequest;
import com.toucheese.reservation.dto.CartResponse;
import com.toucheese.reservation.dto.CartUpdateRequest;
import com.toucheese.reservation.entity.Cart;
import com.toucheese.reservation.repository.CartRepository;
import com.toucheese.reservation.util.CsvUtils;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.service.StudioService;

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
			.addOptions(addOptionsCsv)
			.build();

		cartRepository.save(cart);
	}

	@Transactional(readOnly = true)
	public List<CartResponse> getCartList(Long memberId) {
		Member member = memberService.findMemberById(memberId);
		List<Cart> carts = cartRepository.findByMember(member);

		return carts.stream().map(cart -> {
			List<Long> addOptionIds = CsvUtils.fromCsv(cart.getAddOptions());

			List<ProductAddOption> productAddOptions = productService.findProductAddOptionsByProductIdAndAddOptionIds(
				cart.getProduct().getId(),
				addOptionIds
			);

			Map<Long, AddOption> addOptionCache = addOptionIds.stream()
				.map(productService::findAddOptionById)
				.collect(Collectors.toMap(AddOption::getId, addOption -> addOption));

			List<CartResponse.AddOptionResponse> addOptionResponses = productAddOptions.stream()
				.map(productAddOption -> {
					AddOption addOption = addOptionCache.get(productAddOption.getAddOption().getId());
					return new CartResponse.AddOptionResponse(
						addOption.getAddOptionName(),
						productAddOption.getAddOptionPrice()
					);
				}).toList();

			return new CartResponse(
				cart.getStudio().getName(),
				cart.getProduct().getName(),
				cart.getPersonnel(),
				cart.getCreateDate(),
				cart.getCreateTime(),
				cart.getTotalPrice(),
				addOptionResponses
			);
		}).toList();
	}

	@Transactional
	public void deleteCart(Long cartId) {
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new NoSuchElementException("장바구니 항목이 존재하지 않습니다."));

		cartRepository.delete(cart);
	}

	@Transactional
	public void updateCart(Long cartId, CartUpdateRequest request, Long memberId) {
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new NoSuchElementException("장바구니 항목을 찾을 수 없습니다."));

		Member cartOwner = cartRepository.findMemberByCartId(cartId);

		if (cartOwner == null) {
			throw new ToucheeseBadRequestException("장바구니를 찾을 수 없습니다.");
		}

		if (!cartOwner.getId().equals(memberId)) {
			throw new ToucheeseBadRequestException("해당 장바구니를 변경할 권한이 없습니다.");
		}

		if (request.totalPrice() != null) {
			cart.updateTotalPrice(request.totalPrice());
		}

		if (request.personnel() != null) {
			cart.updatePersonnel(request.personnel());
		}

		if (request.addOptions() != null) {
			String optionsAsCsv = CsvUtils.toCsv(request.addOptions());
			cart.updateAddOptions(optionsAsCsv);
		} else {
			cart.updateAddOptions("");
		}

		cartRepository.save(cart);
	}

	public void checkCartOwner(Long cartId, Long memberId) {
		Member cartOwner = cartRepository.findMemberByCartId(cartId);

		if (cartOwner == null) {
			throw new ToucheeseBadRequestException("장바구니를 찾을 수 없습니다.");
		}

		if (!cartOwner.getId().equals(memberId)) {
			throw new ToucheeseBadRequestException("해당 장바구니를 삭제할 권한이 없습니다.");
		}
	}
}