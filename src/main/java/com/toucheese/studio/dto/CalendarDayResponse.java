package com.toucheese.studio.dto;

import java.time.LocalDate;
import java.util.List;

import com.toucheese.studio.entity.OperatingHour;
import com.toucheese.studio.util.SlotUtils;

import lombok.Builder;

@Builder
public record CalendarDayResponse(
	String date,
	boolean status,
	List<String> times
) {
	public static CalendarDayResponse of(
		LocalDate date,
		OperatingHour operatingHour
	) {
		// 영업시간이 없는 경우 (휴무일)
		if (operatingHour == null) {
			return new CalendarDayResponse(
				date.toString(),
				false,
				List.of() // 빈 리스트 반환
			);
		}

		// 영업일 처리
		List<String> startTimes = SlotUtils.createStartTimeSlots(
			operatingHour.getOpenTime(),
			operatingHour.getCloseTime(),
			operatingHour.getTerm()
		);

		return new CalendarDayResponse(
			date.toString(),
			true,
			startTimes
		);
	}
}
