package com.toucheese.reservation.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.member.entity.Member;
import com.toucheese.member.service.MemberService;
import com.toucheese.product.dto.ProductDetailResponse;
import com.toucheese.product.entity.AddOption;
import com.toucheese.product.entity.Product;
import com.toucheese.product.entity.ProductAddOption;
import com.toucheese.product.service.ProductService;
import com.toucheese.reservation.dto.CartIdsRequest;
import com.toucheese.reservation.dto.CartRequest;
import com.toucheese.reservation.dto.CartResponse;
import com.toucheese.reservation.dto.CartUpdateRequest;
import com.toucheese.reservation.dto.CheckoutCartItemsResponse;
import com.toucheese.reservation.entity.Cart;
import com.toucheese.reservation.event.ReservationMessageEvent;
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
	private final ReservationService reservationService;
	private final ApplicationEventPublisher eventPublisher;


	@Transactional
	public void createCart(CartRequest cartRequest, Principal principal) {

		Long memberId = memberService.getAuthenticatedMemberId(principal);

		Product product = productService.findProductById(cartRequest.productId());
		Studio studio = studioService.findStudioById(cartRequest.studioId());
		Member member = memberService.findMemberById(memberId);

		Cart cart = Cart.fromCartRequest(cartRequest, product, studio, member);

		cartRepository.save(cart);
	}

	@Transactional(readOnly = true)
	public List<CartResponse> findCartList(Principal principal) {
		Long memberId = memberService.getAuthenticatedMemberId(principal);

		Member member = memberService.findMemberById(memberId);
		List<Cart> carts = cartRepository.findByMember(member);

		return carts.stream()
			.map(this::convertToCartResponse)
			.toList();
	}

	@Transactional
	public void deleteCart(Long cartId, Principal principal) {
		Long memberId = memberService.getAuthenticatedMemberId(principal);

		Cart cart = validateCartOwnership(cartId, memberId);

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

	/**
	 * 장바구니 상품 결제성공시 예약 테이블로 데이터 복사, 해당 장바구니 상품 삭제, 메시지 및 이메일 전송(비동기)
	 */
	@Transactional
	public void createReservationsFromCart(Principal principal, CartIdsRequest cartIdsRequest) {

		Long memberId = memberService.getAuthenticatedMemberId(principal);

		List<Long> cartIds = cartIdsRequest.toCartIdList();

		List<Cart> carts = cartRepository.findCartsByMemberIdAndCartIds(memberId, cartIds);

		validateCarts(carts);

		reservationService.createReservationsFromCarts(carts);

		cartRepository.deleteAll(carts);

		// 객체를 스프링 컨텍스트에 전달 -> 스프링 컨텍스트 해당 이벤트를 처리할 수 있는 리스너 검색하여 호출
		eventPublisher.publishEvent(new ReservationMessageEvent(memberId));
	}

	// 이 아래는 검증 메서드

	/**
	 * 장바구니가 비어 있는 경우 예외를 발생시킵니다.
	 */
	private void validateCarts(List<Cart> carts) {
		if (carts.isEmpty()) {
			throw new ToucheeseBadRequestException("선택한 장바구니 항목이 없습니다.");
		}
	}

	/**
	 * 장바구니 소유권 검증 및 Cart 조회
	 */
	private Cart validateCartOwnership(Long cartId, Long memberId) {
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new ToucheeseBadRequestException("장바구니 항목이 존재하지 않습니다."));

		if (!cart.getMember().getId().equals(memberId)) {
			throw new ToucheeseBadRequestException("해당 장바구니를 삭제할 권한이 없습니다.");
		}

		return cart;
	}

	// 이 아래는 Helper 메서드
	private CartResponse convertToCartResponse(Cart cart) {
		List<Long> addOptionIds = CsvUtils.fromCsv(cart.getAddOptions());

		Map<Long, AddOption> addOptionCache = createAddOptionCache(addOptionIds);

		List<CartResponse.SelectAddOptionResponse> selectAddOptionResponses = mapToSelectAddOptionResponses(addOptionCache, addOptionIds, cart);

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
			selectAddOptionResponses,
			productDetailResponse.addOptions()
		);
	}

	/**
	 * AddOption 조회 중복 방지
	 */
	private Map<Long, AddOption> createAddOptionCache(List<Long> addOptionIds) {
		return addOptionIds.stream()
			.map(productService::findAddOptionById)
			.collect(Collectors.toMap(AddOption::getId, addOption -> addOption));
	}

	/**
	 * AddOption 데이터를 SelectAddOptionResponse로 변환
	 */
	private List<CartResponse.SelectAddOptionResponse> mapToSelectAddOptionResponses(
		Map<Long, AddOption> addOptionCache, List<Long> addOptionIds, Cart cart) {

		List<ProductAddOption> productAddOptions = productService.findProductAddOptionsByProductIdAndAddOptionIds(
			cart.getProduct().getId(), addOptionIds);

		return productAddOptions.stream()
			.map(productAddOption -> {
				AddOption addOption = addOptionCache.get(productAddOption.getAddOption().getId());
				return new CartResponse.SelectAddOptionResponse(
					addOption.getId(),
					addOption.getAddOptionName(),
					productAddOption.getAddOptionPrice()
				);
			}).toList();
	}
}