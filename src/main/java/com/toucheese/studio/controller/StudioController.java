package com.toucheese.studio.controller;

import java.util.List;

import com.toucheese.global.data.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toucheese.studio.dto.CalendarDayResponse;
import com.toucheese.studio.dto.StudioDetailResponse;
import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.service.StudioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/studios")
@Tag(name = "스튜디오 API", description = "스튜디오 검색, 스튜디오 상세조회")
@RequiredArgsConstructor
public class StudioController {

	private final StudioService studioService;

	@GetMapping
	@Operation(summary = "스튜디오 검색",
		description = "사용자가 입력한 키워드로 스튜디오를 검색합니다.",
		parameters = @Parameter(name = "keyword", description = "검색할 키워드", required = true))
	public ResponseEntity<?> searchStudios(@RequestParam String keyword) {
		return ApiResponse.getObjectSuccess(studioService.searchStudios(keyword));
	}

	/**
	 * 스튜디오 상세 정보를 조회한다.
	 * @param studioId 스튜디오 아이디
	 * @return 아이디에 해당하는 스튜디오 상세 정보
	 */
	@GetMapping("/{studioId}")
	@Operation(summary = "스튜디오 상세 조회",
		description = "사용자가 클릭한 스튜디오 상세조회")
	public ResponseEntity<?> findStudioDetailById(@PathVariable Long studioId) {
		return ApiResponse.getObjectSuccess(studioService.findStudioDetailById(studioId));
	}

	@GetMapping("/{studioId}/calendars")
	@Operation(summary = "캘린더 휴무일 및 예약 희망 시간",
		description = "상품 예약시 캘린더 클릭하였을때 휴무일과 영업일의 스튜디오 영업시간 기준 예약 희망 시간 조회")
	public ResponseEntity<?> getMonthlyCalendar(
		@PathVariable Long studioId,
		@RequestParam(required = false) String yearMonth
	) {
		return ApiResponse.getObjectSuccess(studioService.getMonthlyCalendar(studioId, yearMonth));
	}
}