package com.toucheese.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toucheese.product.entity.ProductAddOption;

public interface ProductAddOptionRepository extends JpaRepository<ProductAddOption, Long> {

	List<ProductAddOption> findByProduct_IdAndAddOption_IdIn(Long productId, List<Long> addOptionIds);

}
