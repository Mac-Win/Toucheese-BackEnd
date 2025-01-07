package com.toucheese.studio.service;

import com.toucheese.global.config.ImageConfig;
import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.studio.dto.StudioDetailResponse;
import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.repository.StudioQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudioQueryService {

	private final ImageConfig imageConfig;
	private final StudioQueryRepository studioQueryRepository;

	@Transactional(readOnly = true)
	public List<StudioSearchResponse> searchStudios(String keyword) {
		List<Studio> studios = studioQueryRepository.findByNameContaining(keyword);

		return studios.stream()
			.map(StudioSearchResponse::of)
			.toList();
	}

	@Transactional(readOnly = true)
	public Studio findStudioById(Long studioId) {
		return studioQueryRepository.findById(studioId)
				.orElseThrow(() -> new ToucheeseBadRequestException("해당하는 스튜디오를 찾을 수 없습니다."));
	}

	@Transactional(readOnly = true)
	public StudioDetailResponse findStudioDetailById(Long studioId) {
		Studio studio = findStudioById(studioId);
		return StudioDetailResponse.of(studio, imageConfig.getResizedImageBaseUrl());
	}

}