package com.toucheese.product.controller;

import com.toucheese.product.dto.ProductDetailResponse;
import com.toucheese.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 상세 정보를 조회한다.
     * @param productId 상품 아이디
     * @return 아이디에 해당하는 상품 상세 정보
     */
    @GetMapping("/{productId}")
    public ProductDetailResponse findProductDetailById(@PathVariable("productId") Long productId) {
        return productService.findProductDetailById(productId);
    }

}
