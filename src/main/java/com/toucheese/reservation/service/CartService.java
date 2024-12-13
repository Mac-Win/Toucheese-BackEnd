package com.toucheese.reservation.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.member.entity.Member;
import com.toucheese.member.service.MemberService;
import com.toucheese.product.dto.ProductDetailResponse;
import com.toucheese.product.entity.AddOption;
import com.toucheese.product.entity.Product;
import com.toucheese.product.entity.ProductAddOption;
import com.toucheese.product.service.ProductService;
import com.toucheese.reservation.dto.CartRequest;
import com.toucheese.reservation.dto.CartResponse;
import com.toucheese.reservation.dto.CartUpdateRequest;
import com.toucheese.reservation.dto.CheckoutCartItemsResponse;
import com.toucheese.reservation.entity.Cart;
import com.toucheese.reservation.repository.CartRepository;
import com.toucheese.reservation.util.CsvUtils;
import com.toucheese.solapi.service.MessageService;
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
	private final ReservationService reservationService;
	private final MessageService messageService;


	@Transactional
	public void createCart(CartRequest cartRequest, Long memberId) {

		Product product = productService.findProductById(cartRequest.productId());
		Studio studio = studioService.findStudioById(cartRequest.studioId());
		Member member = memberService.findMemberById(memberId);
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

			List<CartResponse.SelectAddOptionResponse> SelectAddOptionResponse = productAddOptions.stream()
				.map(productAddOption -> {
					AddOption addOption = addOptionCache.get(productAddOption.getAddOption().getId());
					return new CartResponse.SelectAddOptionResponse(
						addOption.getId(),
						addOption.getAddOptionName(),
						productAddOption.getAddOptionPrice()
					);
				}).toList();

			ProductDetailResponse productDetailResponse = productService.findProductDetailById(cart.getProduct().getId());

			return new CartResponse(
				cart.getId(),
				cart.getStudio().getProfileImage(),
				cart.getStudio().getName(),
				cart.getProduct().getProductImage(),
				cart.getProduct().getName(),
				cart.getProduct().getStandard(),
				cart.getProduct().getPrice(),
				cart.getPersonnel(),
				cart.getCreateDate(),
				cart.getCreateTime(),
				cart.getTotalPrice(),
				SelectAddOptionResponse,
				productDetailResponse.addOptions()
			);
		}).toList();
	}

	@Transactional
	public void deleteCart(Long cartId) {
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new ToucheeseBadRequestException("장바구니 항목이 존재하지 않습니다."));

		cartRepository.delete(cart);
	}

	@Transactional
	public void updateCart(Long cartId, CartUpdateRequest request, Long memberId) {
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new ToucheeseBadRequestException("장바구니 항목을 찾을 수 없습니다."));

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

	@Transactional(readOnly = true)
	public List<CheckoutCartItemsResponse> getCheckoutCartItems(Long memberId, String cartIds) {
		List<Long> cartIdsList = CsvUtils.fromCsv(cartIds);

		List<Cart> carts = cartRepository.findCartsByMemberIdAndCartIds(memberId, cartIdsList);

		return carts.stream().map(cart -> {
			List<Long> addOptionIds = CsvUtils.fromCsv(cart.getAddOptions());
			List<ProductAddOption> productAddOptions = productService.findProductAddOptionsByProductIdAndAddOptionIds(
				cart.getProduct().getId(),
				addOptionIds
			);

			Map<Long, AddOption> addOptionCache = addOptionIds.stream()
				.map(productService::findAddOptionById)
				.collect(Collectors.toMap(AddOption::getId, addOption -> addOption));

			List<CartResponse.SelectAddOptionResponse> SelectAddOptionResponse = productAddOptions.stream()
				.map(productAddOption -> {
					AddOption addOption = addOptionCache.get(productAddOption.getAddOption().getId());
					return new CartResponse.SelectAddOptionResponse(
						addOption.getId(),
						addOption.getAddOptionName(),
						productAddOption.getAddOptionPrice()
					);
				}).toList();

			return new CheckoutCartItemsResponse(
				cart.getId(),
				cart.getStudio().getName(),
				cart.getProduct().getProductImage(),
				cart.getProduct().getName(),
				cart.getProduct().getPrice(),
				cart.getPersonnel(),
				cart.getCreateDate(),
				cart.getCreateTime(),
				cart.getTotalPrice(),
				SelectAddOptionResponse
			);
		}).toList();
	}


	@Transactional
	public void createReservationsFromCart(Long memberId, List<Long> cartIds) {

		List<Cart> carts = cartRepository.findCartsByMemberIdAndCartIds(memberId, cartIds);

		if (carts.isEmpty()) {
			throw new ToucheeseBadRequestException("선택한 장바구니 항목이 없습니다.");
		}

		reservationService.createReservationsFromCarts(carts);

		cartRepository.deleteAll(carts);

		// 트랜잭션 커밋 후 메시지 전송
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				messageService.sendMessageForLoggedInUser(memberId);
			}
		});

	}
}