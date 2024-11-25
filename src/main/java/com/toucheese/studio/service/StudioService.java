package com.toucheese.studio.service;

import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.repository.StudioRepository;
import com.toucheese.studio.repository.StudioRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudioService {

	private final StudioRepository studioRepository;
	private final StudioRepositoryImpl studioRepositoryImpl;

	public List<StudioSearchResponse> searchStudios(String keyword) {
		List<Studio> studios = studioRepository.findByNameContaining(keyword);

		return studios.stream()
			.map(StudioSearchResponse::of)
			.collect(Collectors.toList());
	}

}