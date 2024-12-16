package com.toucheese.product.controller;

import com.toucheese.global.data.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toucheese.product.dto.ProductDetailResponse;
import com.toucheese.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@Tag(name = "상품 API", description = "상품 상세 조회")
public class ProductController {

	private final ProductService productService;

	/**
	 * 상품 상세 정보를 조회한다.
	 * @param productId 상품 아이디
	 * @return 아이디에 해당하는 상품 상세 정보
	 */
	@GetMapping("/{productId}")
	@Operation(summary = "상품 상세 조회", description = "상품 클릭 시 상품 상세 조회")
	public ResponseEntity<?> findProductDetailById(@PathVariable("productId") Long productId) {
		return ApiResponse.getObjectSuccess(productService.findProductDetailById(productId));
	}
}
