package com.toucheese.studio.service;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.studio.dto.StudioDetailResponse;
import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.repository.StudioRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudioService {

	private final StudioRepository studioRepository;

	public List<StudioSearchResponse> searchStudios(String keyword) {
		List<Studio> studios = studioRepository.findByNameContaining(keyword);

		return studios.stream()
			.map(StudioSearchResponse::of)
			.collect(Collectors.toList());
	}

	/**
	 * 스튜디오 상세 정보를 조회한다.
	 * @param studioId 스튜디오 아이디
	 * @return 아이디에 해당하는 스튜디오 상세 정보
	 * @throws ToucheeseBadRequestException 존재하지 않는 스튜디오에 접근할 때 발생
	 */
	@Transactional(readOnly = true)
	public StudioDetailResponse findStudioDetailById(Long studioId) {
		Studio studio = studioRepository.findById(studioId)
				.orElseThrow(ToucheeseBadRequestException::new);

		return StudioDetailResponse.of(studio);
	}

}