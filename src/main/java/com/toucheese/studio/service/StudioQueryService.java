package com.toucheese.studio.service;

import com.toucheese.global.config.ImageConfig;
import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.studio.dto.StudioDetailResponse;
import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.repository.StudioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudioQueryService {

	private final ImageConfig imageConfig;
	private final StudioRepository studioRepository;

	@Transactional(readOnly = true)
	public List<StudioSearchResponse> searchStudios(String keyword) {
		List<Studio> studios = studioRepository.findByNameContaining(keyword);

		return studios.stream()
			.map(StudioSearchResponse::of)
			.toList();
	}

	@Transactional(readOnly = true)
	public StudioDetailResponse findStudioDetailById(Long studioId) {
		Studio studio = studioRepository.findById(studioId)
			.orElseThrow(ToucheeseBadRequestException::new);

		return StudioDetailResponse.of(studio, imageConfig.getResizedImageBaseUrl());
	}

	@Transactional(readOnly = true)
	public Studio findStudioById(Long studioId) {
		return studioRepository.findById(studioId)
			.orElseThrow(() -> new ToucheeseBadRequestException("Studio not found"));
	}

}