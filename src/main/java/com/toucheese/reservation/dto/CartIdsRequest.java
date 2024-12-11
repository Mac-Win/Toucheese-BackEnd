package com.toucheese.reservation.dto;

public record CartIdsRequest(String cartIds) {
	public static CartIdsRequest of(String cartIds) {
		return new CartIdsRequest(cartIds);
	}
}