package com.toucheese.studio.controller;

import com.toucheese.studio.dto.StudioDetailResponse;
import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.service.StudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/studios")
@RequiredArgsConstructor
public class StudioController {

	private final StudioService studioService;

	@GetMapping
	public List<StudioSearchResponse> searchStudios(@RequestParam String keyword) {
		return studioService.searchStudios(keyword);
	}

	/**
	 * 스튜디오 상세 정보를 조회한다.
	 * @param studioId 스튜디오 아이디
	 * @return 아이디에 해당하는 스튜디오 상세 정보
	 */
	@GetMapping("/{studioId}")
	public StudioDetailResponse findStudioDetailById(@PathVariable Long studioId) {
		return studioService.findStudioDetailById(studioId);
	}

}