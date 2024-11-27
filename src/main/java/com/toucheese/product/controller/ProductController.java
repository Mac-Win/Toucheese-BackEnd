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

    @GetMapping("/{productId}")
    public ProductDetailResponse findProductDetailById(@PathVariable("productId") Long productId) {
        return productService.findProductDetailById(productId);
    }

}
