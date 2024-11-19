package com.toucheese.studio.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.repository.StudioRepository;

@Service
public class StudioService {

	private final StudioRepository studioRepository;

	public StudioService(StudioRepository studioRepository) {
		this.studioRepository = studioRepository;
	}

	public List<StudioSearchResponse> searchStudios(String keyword) {
		List<Studio> studios = studioRepository.findByNameContaining(keyword);

		return studios.stream()
			.map(StudioSearchResponse::of)
			.collect(Collectors.toList());
	}
}