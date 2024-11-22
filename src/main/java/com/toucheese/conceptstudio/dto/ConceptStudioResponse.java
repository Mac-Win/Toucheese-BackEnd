package com.toucheese.conceptstudio.dto;

import com.toucheese.studio.entity.Studio;

import java.util.List;

public record ConceptStudioResponse(
	Long id,
	String name,
	String profileImage,
	Float rating,
	Integer price,
	List<String> imageUrls
) {

	public static ConceptStudioResponse of(Studio studio, List<String> imageUrls) {
		return new ConceptStudioResponse(
			studio.getId(),
			studio.getName(),
			studio.getProfileImage(),
			studio.getRating(),
			studio.getPrice(),
			imageUrls
		);
	}
}