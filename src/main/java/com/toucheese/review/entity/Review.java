package com.toucheese.review.entity;

import java.util.List;

import com.toucheese.image.entity.ReviewImage;
import com.toucheese.member.entity.Member;
import com.toucheese.product.entity.Product;
import com.toucheese.studio.entity.Studio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	private Float rating;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studio_id", nullable = false)
	private Studio studio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = true)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ReviewImage> reviewImages;

	@Builder
	public Review(String content, Float rating, Studio studio, Product product, Member member) {
		this.content = content;
		this.rating = rating;
		this.studio = studio;
		this.product = product;
		this.member = member;
	}
}
