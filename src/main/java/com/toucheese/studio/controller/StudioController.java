package com.toucheese.studio.controller;

import com.toucheese.studio.dto.FilteringStudioRequest;
import com.toucheese.studio.dto.StudioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.service.StudioService;

@RestController
@RequestMapping("/api/v1/studios")
@RequiredArgsConstructor
public class StudioController {

	private final StudioService studioService;

	@GetMapping("/search")
	public List<StudioSearchResponse> searchStudios(@RequestParam String keyword) {
		return studioService.searchStudios(keyword);
	}

	/**
	 * 필터링 한 스튜디오 목록을 조회한다.
	 * @param page 현재 페이지
	 * @param request 필터링 조건 (가격, 인기, 지역)
	 * @return 현재 페이지에 해당하는 필터링 된 스튜디오 목록
	 */
	@GetMapping("/filters")
	public Page<StudioResponse> getFilteredStudios(@RequestParam int page, @RequestBody FilteringStudioRequest request) {
		return studioService.getFilteredStudiosOrderByName(
				page, request.conceptId(), request.price(), request.rating(), request.locations()
		);
	}

}