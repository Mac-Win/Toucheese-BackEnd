package com.toucheese.studio.dto;

import com.toucheese.studio.entity.Studio;

import lombok.Builder;

@Builder
public record StudioSearchResponse(Long id, String name, String profileImage, String address) {
	public static StudioSearchResponse of(Studio studio) {
		return StudioSearchResponse.builder()
			.id(studio.getId())
			.name(studio.getName())
			.profileImage(studio.getProfileImage())
			.address(studio.getAddress())
			.build();
	}
}