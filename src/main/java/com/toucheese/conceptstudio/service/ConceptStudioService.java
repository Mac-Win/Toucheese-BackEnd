package com.toucheese.conceptstudio.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.conceptstudio.dto.StudioResponse;
import com.toucheese.conceptstudio.repository.ConceptStudioRepository;
import com.toucheese.global.config.ImageConfig;
import com.toucheese.global.util.PageUtils;
import com.toucheese.studio.entity.Location;
import com.toucheese.studio.repository.StudioRepositoryImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConceptStudioService {

	private final ConceptStudioRepository conceptStudioRepository;
	private final StudioRepositoryImpl studioRepositoryImpl;
	private final ImageConfig imageConfig;

	@Transactional(readOnly = true)
	public Page<StudioResponse> getStudiosByConceptId(Long conceptId, int page) {
		Pageable pageable = PageUtils.createPageable(page);
		return conceptStudioRepository.findByConceptId(conceptId, pageable).map( conceptStudio ->
				StudioResponse.of(conceptStudio.getStudio(), imageConfig.getResizedImageBaseUrl())
		);
	}

	/**
	 * 필터링 된 스튜디오 목록을 이름으로 정렬하여 조회한다.
	 * @param page 현재 페이지
	 * @param conceptId 선택된 컨셉 요소 아이디
	 * @param price 가격순 필터링 요소
	 * @param rating 인기순 필터링 요소
	 * @param locations 지역 필터링 요소
	 * @return 현재 페이지에 해당하는 필터링 후 정렬된 스튜디오 목록
	 */
	@Transactional(readOnly = true)
	public Page<StudioResponse> getFilteredStudiosOrderByName(int page, Long conceptId, Integer price, Float rating, List<Location> locations) {
		Pageable pageable = PageUtils.createPageable(page);
		return studioRepositoryImpl.getFilteredStudiosOrderByName(price, rating, locations, conceptId, pageable)
				.map(studio -> StudioResponse.of(studio, imageConfig.getResizedImageBaseUrl()));
	}

}
