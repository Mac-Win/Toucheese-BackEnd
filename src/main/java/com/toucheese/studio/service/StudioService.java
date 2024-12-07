package com.toucheese.studio.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toucheese.global.exception.ToucheeseBadRequestException;
import com.toucheese.studio.dto.CalendarDayResponse;
import com.toucheese.studio.dto.StudioDetailResponse;
import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.entity.OperatingHour;
import com.toucheese.studio.entity.Studio;
import com.toucheese.studio.repository.OperatingHourRepository;
import com.toucheese.studio.repository.StudioRepository;
import com.toucheese.studio.util.DateUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudioService {

	private final StudioRepository studioRepository;
	private final OperatingHourRepository operatingHourRepository;

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

		return StudioDetailResponse.of(studio);
	}

	@Transactional(readOnly = true)
	public Studio findStudioById(Long studioId) {
		return studioRepository.findById(studioId)
			.orElseThrow(() -> new ToucheeseBadRequestException("Studio not found"));
	}

	/**
	 * 특정 월의 휴무일과 영업시간을 반환
	 *
	 * @param studioId 스튜디오 ID
	 * @param yearMonth 2024-12 연도월
	 * @return 날짜별 상태 리스트
	 */
	@Transactional(readOnly = true)
	public List<CalendarDayResponse> getMonthlyCalendar(Long studioId, String yearMonth) {
		// 연도와 월 기본값 설정 (서울 기준 현재 날짜)
		YearMonth targetYearMonth = (yearMonth != null && !yearMonth.isEmpty())
			? YearMonth.parse(yearMonth) // "2024-12" → YearMonth 객체로 변환
			: YearMonth.now(ZoneId.of("Asia/Seoul")); // 입력값 없으면 현재 날짜 기준

		List<CalendarDayResponse> calendarDays = new ArrayList<>();

		// 해당 월의 시작일과 종료일 계산
		LocalDate firstDay = targetYearMonth.atDay(1);
		LocalDate lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());

		// 날짜별 처리
		for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
			String dayOfWeek = DateUtils.getDayOfWeekFromDate(date.toString());
			OperatingHour operatingHour = operatingHourRepository.findByStudioIdAndDayOfWeek(studioId, dayOfWeek);

			calendarDays.add(CalendarDayResponse.of(date, operatingHour));
		}
		return calendarDays;
	}
}