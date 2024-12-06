package com.toucheese.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.product.dto.ProductDetailResponse;
import com.toucheese.product.entity.Product;
import com.toucheese.product.entity.ProductAddOption;
import com.toucheese.product.repository.ProductAddOptionRepository;
import com.toucheese.product.repository.ProductRepository;
import com.toucheese.product.util.DateUtils;
import com.toucheese.product.util.SlotUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductAddOptionRepository productAddOptionRepository;

    /**
     * 상품 상세 정보를 조회한다.
     * @param productId 상품 아이디
     * @return 아이디에 해당하는 상품 상세 정보
     * @throws ToucheeseBadRequestException 존재하지 않는 상품에 접근할 때 발생
     */
    @Transactional(readOnly = true)
    public ProductDetailResponse findProductDetailById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ToucheeseBadRequestException::new);

        return ProductDetailResponse.of(product);
    }

    @Transactional(readOnly = true)
    public Product findProductById(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ToucheeseBadRequestException("Product not found"));
    }

    /**
     addOptionId와 product_id 맞는 productAddOptionId 매칭 후 반환
     없을 경우 빈 List 반환
     */
    @Transactional(readOnly = true)
    public List<ProductAddOption> findProductAddOptionsByProductIdAndAddOptionIds(Long productId, List<Long> addOptionIds) {
        return productAddOptionRepository.findByProduct_IdAndAddOption_IdIn(productId, addOptionIds);
    }

    @Transactional(readOnly = true)
    public List<String> getAvailableTimes(Long productId, String date) {
        // 상품 조회
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ToucheeseBadRequestException("Product not found"));

        // 날짜를 기반으로 요일 계산
        String dayOfWeek = DateUtils.getDayOfWeekFromDate(date);

        // 해당 요일의 영업시간 필터링
        return product.getStudio().getOperatingHours().stream()
            .filter(hour -> hour.getDayOfWeek().equals(dayOfWeek)) // 요일 필터링
            .flatMap(hour -> SlotUtils.createStartTimeSlots(
                hour.getOpenTime(),
                hour.getCloseTime(),
                hour.getTerm()
            ).stream())
            .toList(); // 예약 가능한 시간 리스트 반환
    }
}
