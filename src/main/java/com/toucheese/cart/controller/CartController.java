package com.toucheese.cart.controller;

import com.toucheese.cart.dto.*;
import com.toucheese.cart.service.CartService;
import com.toucheese.global.data.ApiResponse;
import com.toucheese.global.util.PrincipalUtils;
import com.toucheese.member.dto.MemberContactInfoResponse;
import com.toucheese.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
@Tag(name = "장바구니 API")
@PreAuthorize("isAuthenticated()")
public class CartController {
	private final CartService cartService;
	private final MemberService memberService;

	@Operation(summary = "장바구니 저장 기능(회원)", description = """
			선택한 상품을 장바구니에 저장합니다.
			```json
			{
			  "productId": 상품 id
			  "studioId": 스튜디오 id
			  "totalPrice": 상품 + 추가 상품의 총 금액
			  "createDate": 예약 날짜 (2024-12-11)
			  "createTime": 예약 시간 (09:30)
			  "personnel": 예약 인원 수
			  "addOptions": 추가 옵션에 대한 id값[
				1, 2, 3
			  ]
			}
			"""
	)
	@PostMapping("/carts")
	public ResponseEntity<?> cartCreate(
		@Valid @RequestBody CartRequest cartRequest, Principal principal
	) {
		Long memberId = PrincipalUtils.extractMemberId(principal);
		cartService.createCart(cartRequest, memberId);

		return ApiResponse.createdSuccess("장바구니가 생성되었습니다.");
	}

	@Operation(summary = "장바구니 목록 조회(회원)",
		description = """
        {
        	"cartId": 장바구니 id값,
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
	public ResponseEntity<?> getCartList(Principal principal) {
		Long memberId = PrincipalUtils.extractMemberId(principal);
		return ApiResponse.getObjectSuccess(cartService.findCartList(memberId));
	}

	@Operation(summary = "해당 장바구니 삭제", description = "해당하는 장바구니를 삭제합니다.")
	@DeleteMapping("/carts/{cartId}")
	public ResponseEntity<?> deleteCart(@PathVariable String cartId, Principal principal) {
		Long memberId = PrincipalUtils.extractMemberId(principal);
		cartService.deleteCart(cartId, memberId);

		return ApiResponse.deletedSuccess("장바구니 항목이 삭제되었습니다.");
	}

	@Operation(summary = "장바구니 옵션 및 인원 변경", description = """
		장바구니의 옵션과 인원수를 수정합니다.<br>
		```json
		{
		  "totalPrice": 상품 + 선택한 옵션의 총 가격 수정,
		  "personnel": 인원수 수정,
		  "addOptions": 추가옵션 수정[
		    1, 2, 3
		  ]
		}
		""")
	@PutMapping("/carts/{cartId}")
	public ResponseEntity<?> updateCart(@PathVariable Long cartId,
		@Valid @RequestBody CartUpdateRequest request, Principal principal
	) {
		Long memberId = PrincipalUtils.extractMemberId(principal);
		cartService.updateCart(cartId, request, memberId);

		return ApiResponse.updatedSuccess("장바구니가 성공적으로 업데이트되었습니다.");
	}

	@Operation(summary = "장바구니 결제 조회", description = """
		선택한 장바구니를 결제화면에서 조회합니다. - 다른 회원의 장바구니에 있는 id를 넣어도 검색되지 않습니다.<br>
		https://api.toucheese-macwin.store/v1/members/carts/checkout-items?cartIds=1,2,3
		```
		{
		    "checkoutCartItems": [
		        {
		            "cartId": 해당 장바구니 id
		            "studioName": 스튜디오 이름
		            "productImage": 상품 이미지
		            "productName": 상품 이름
		            "productPrice": 상품 가격
		            "personnel": 상품 기준 인원
		            "reservationDate": 예약한 날짜(year-month-day)
		            "reservationTime": 예약한 시간
		            "totalPrice": 상품가격 + 선택한 옵션가격
		            "selectAddOptions": [
		                {
		                    "selectOptionId": 선택한 옵션 id
		                    "selectOptionName": 선택한 옵션 이름
		                    "selectOptionPrice": 선택한 옵션 가격
		                }
		            ]
		        },
		    ],
		    "memberContactInfo": {
		        "email": 해당 회원의 이메일
		        "name": 해당 회원의 이름
		        "phone": 해당 회원의 전화번호
		    }
		}
		""")
	@GetMapping("/carts/checkout-items")
	public ResponseEntity<?> getCombinedResponse(Principal principal, @RequestParam String cartIds) {
		Long memberId = PrincipalUtils.extractMemberId(principal);
		List<CheckoutCartItemsResponse> checkoutCartItems = cartService.getCheckoutCartItems(memberId, cartIds);
		MemberContactInfoResponse memberContactInfo = memberService.findMemberContactInfo(memberId);

		CombinedResponse combinedResponse = new CombinedResponse(checkoutCartItems, memberContactInfo);
		return ApiResponse.getObjectSuccess(combinedResponse);
	}
}
