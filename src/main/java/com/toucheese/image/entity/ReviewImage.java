package com.toucheese.image.entity;

import com.toucheese.review.entity.Review;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;

	@Column(nullable = false)
	private String originalPath;

	@Column(nullable = false)
	private String resizedPath;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_info_id")
	private ImageInfo imageInfo;

	@Builder
	public ReviewImage(Review review, String originalPath, String resizedPath, ImageInfo imageInfo) {
		this.review = review;
		this.originalPath = originalPath;
		this.resizedPath = resizedPath;
		this.imageInfo = imageInfo;
	}
}
