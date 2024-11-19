package com.toucheese.studio.dto;

import lombok.Builder;

@Builder
public record StudioSearchResultDTO(
	Long id, String name, String profileImage, String address
) {
}