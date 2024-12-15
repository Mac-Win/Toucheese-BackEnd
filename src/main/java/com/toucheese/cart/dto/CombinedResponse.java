package com.toucheese.cart.dto;

import java.util.List;

import com.toucheese.member.dto.MemberContactInfoResponse;

public record CombinedResponse(
	List<CheckoutCartItemsResponse> CheckoutCartItems, // CartPaymentResponse 리스트
	MemberContactInfoResponse memberContactInfo       // 단일 MemberContactInfo 객체
) {}