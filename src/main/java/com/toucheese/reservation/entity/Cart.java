package com.toucheese.reservation.entity;

import java.time.LocalDate;
import java.time.LocalTime;

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

	public void updatePersonnel(Integer personnel) {
		this.personnel = personnel;
	}

	public void updateAddOptions(String addOptions) {
		this.addOptions = addOptions;
	}

	public void updateTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
}
