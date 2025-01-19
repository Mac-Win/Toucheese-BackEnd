package com.toucheese.cart.dto;

import java.util.List;

import com.toucheese.global.util.CsvUtils;

import io.swagger.v3.oas.annotations.media.Schema;

public record CartIdsRequest(
	@Schema(type = "string", example = "1,2,3")
	String cartIds) {
	public static CartIdsRequest of(String cartIds) {
		return new CartIdsRequest(cartIds);
	}

	// CSV 문자열을 List<Long>으로 변환
	public List<Long> toCartIdList() {
		return CsvUtils.fromCsv(this.cartIds());
	}

	// List<Long>을 CSV 문자열로 변환
	public static CartIdsRequest fromCartIdList(List<Long> cartIds) {
		return new CartIdsRequest(CsvUtils.toCsv(cartIds));
	}
}