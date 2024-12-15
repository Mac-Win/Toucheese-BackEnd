package com.toucheese.cart.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.toucheese.cart.dto.CartRequest;
import com.toucheese.cart.dto.CartUpdateRequest;
import com.toucheese.global.util.CsvUtils;
import com.toucheese.member.entity.Member;
import com.toucheese.product.entity.Product;
import com.toucheese.studio.entity.Studio;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer totalPrice;

	private Integer personnel;

	private LocalDate createDate;

	private LocalTime createTime;

	private String addOptions;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studio_id")
	private Studio studio;

	public void update(CartUpdateRequest request) {
		if (request.totalPrice() != null) {
			this.totalPrice = request.totalPrice();
		}
		if (request.personnel() != null) {
			this.personnel = request.personnel();
		}
		if (request.addOptions() != null) {
			this.addOptions = CsvUtils.toCsv(request.addOptions());
		} else {
			this.addOptions = "";
		}
	}

	public static Cart fromCartRequest(CartRequest cartRequest, Product product, Studio studio, Member member) {
		String addOptionsCsv = CsvUtils.toCsv(cartRequest.addOptions());

		return Cart.builder()
			.product(product)
			.studio(studio)
			.member(member)
			.totalPrice(cartRequest.totalPrice())
			.createDate(cartRequest.createDate())
			.createTime(cartRequest.createTime())
			.personnel(cartRequest.personnel())
			.addOptions(addOptionsCsv)
			.build();
	}
}
