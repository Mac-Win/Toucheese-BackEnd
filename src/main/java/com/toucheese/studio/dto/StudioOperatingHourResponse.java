package com.toucheese.studio.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.toucheese.studio.entity.OperatingHour;

import lombok.Builder;

@Builder
public record StudioOperatingHourResponse(
	String dayOfWeek,
	String openTime,
	String closeTime
) {
	// 고정된 요일 리스트
	private static final List<String> ALL_DAYS = Arrays.asList("월", "화", "수", "목", "금", "토", "일");

	public static List<StudioOperatingHourResponse> fromEntityList(List<OperatingHour> operatingHours) {
		List<StudioOperatingHourResponse> result = new ArrayList<>();

		for (String day : ALL_DAYS) {
			OperatingHour hour = operatingHours.stream()
				.filter(h -> h.getDayOfWeek().equals(day))
				.findFirst()
				.orElse(null);

			if (hour != null) {
				result.add(fromEntity(hour));
			} else {
				result.add(StudioOperatingHourResponse.builder()
					.dayOfWeek(day)
					.openTime("휴무")
					.closeTime("-")
					.build());
			}
		}

		return result;
	}

	public static StudioOperatingHourResponse fromEntity(OperatingHour operatingHour) {
		return StudioOperatingHourResponse.builder()
			.dayOfWeek(operatingHour.getDayOfWeek())
			.openTime(operatingHour.getOpenTime())
			.closeTime(operatingHour.getCloseTime())
			.build();
	}


}