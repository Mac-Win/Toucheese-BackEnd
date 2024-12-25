package com.toucheese.studio.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.toucheese.global.exception.ToucheeseBadRequestException;

public class SlotUtils {

	public static List<String> createStartTimeSlots(String openTime, String closeTime, Integer term) {
		List<String> startTimeSlots = new ArrayList<>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
		LocalTime start = LocalTime.parse(openTime, formatter);
		LocalTime end = LocalTime.parse(closeTime, formatter);

		validateTimeInputs(start, end);

		if (start.equals(LocalTime.MIDNIGHT) && end.equals(LocalTime.MIDNIGHT)) {
			for (int hour = 0; hour < 24; hour++) {
				startTimeSlots.add(LocalTime.of(hour, 0).format(formatter));
			}
			return startTimeSlots; // 24시간 슬롯 반환
		}

		// end 이전까지만 슬롯 추가
		while (!start.plusMinutes(term).isAfter(end)) {
			startTimeSlots.add(start.format(formatter));
			start = start.plusMinutes(term); // 60분씩 증가

			// start가 하루를 초과하지 않도록 보장
			if (start.equals(LocalTime.MIDNIGHT)) { // 00:00으로 순환되었을 경우 종료
				break;
			}
		}

		return startTimeSlots;
	}

	private static void validateTimeInputs(LocalTime start, LocalTime end) {
		if (!start.isBefore(end)) {
			throw new ToucheeseBadRequestException("Open time must be before close time.");
		}
	}
}
