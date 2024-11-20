package com.toucheese.studio.service;

import com.toucheese.studio.dto.StudioResponse;
import com.toucheese.studio.entity.Location;
import com.toucheese.studio.repository.StudioRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.repository.StudioRepository;

@Service
@RequiredArgsConstructor
public class StudioService {

	private final StudioRepository studioRepository;
	private final StudioRepositoryImpl studioRepositoryImpl;

	private static final Integer PAGE_SIZE = 10;

	public List<StudioSearchResponse> searchStudios(String keyword) {
		List<Studio> studios = studioRepository.findByNameContaining(keyword);

		return studios.stream()
			.map(StudioSearchResponse::of)
			.collect(Collectors.toList());
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
	public Page<StudioResponse> getFilteredStudiosOrderByName(int page, Long conceptId, Integer price, Float rating, List<Location> locations) {
		Pageable pageable = PageRequest.of(page, PAGE_SIZE);
		return studioRepositoryImpl.getFilteredStudiosOrderByName(price, rating, locations, conceptId, pageable)
				.map(StudioResponse::of);
	}
}