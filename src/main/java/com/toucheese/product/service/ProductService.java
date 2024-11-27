package com.toucheese.product.service;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.product.dto.ProductDetailResponse;
import com.toucheese.product.entity.Product;
import com.toucheese.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDetailResponse findProductDetailById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ToucheeseBadRequestException::new);

        return ProductDetailResponse.of(product);
    }

}
