package com.toucheese.studio.dto;

import java.util.List;

import com.toucheese.image.entity.FacilityImage;
import com.toucheese.product.dto.ProductResponse;
import com.toucheese.studio.entity.Studio;

import lombok.Builder;

@Builder
public record StudioDetailResponse(
	Long id,
	String name,
	String profileImage,
	String description,
	Float rating,
	Integer reviewCount,
	String address,
	String notice,
	List<String> facilityImageUrls,
	List<ProductResponse> products,
	List<StudioOperatingHourResponse> operatingHours
) {

	public static StudioDetailResponse of(Studio studio, String baseUrl) {
		return builder()
			.id(studio.getId())
			.name(studio.getName())
			.profileImage(baseUrl + studio.getProfileImage())
			.description(studio.getDescription())
			.rating(studio.getRating())
			.reviewCount(studio.getReviews().size())
			.address(studio.getAddress())
			.notice(studio.getNotice())
			.facilityImageUrls(studio.getFacilityImages().stream()
				.map(facilityImage -> baseUrl + facilityImage.getResizedPath())
				.toList()
			)
			.products(studio.getProducts().stream()
				.map(product -> ProductResponse.of(product, baseUrl))
				.toList()
			)
			.operatingHours(StudioOperatingHourResponse.fromEntityList(studio.getOperatingHours())
			)
			.build();
	}
}