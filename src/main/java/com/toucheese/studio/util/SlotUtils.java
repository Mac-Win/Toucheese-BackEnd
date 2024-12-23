package com.toucheese.studio.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SlotUtils {

	public static List<String> createStartTimeSlots(String openTime, String closeTime, Integer term) {
		List<String> startTimeSlots = new ArrayList<>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
		LocalTime start = LocalTime.parse(openTime, formatter);
		LocalTime end = LocalTime.parse(closeTime, formatter);

		// 입력값 검증: openTime이 closeTime보다 이후일 경우 예외 처리
		if (!start.isBefore(end)) {
			throw new IllegalArgumentException("Open time must be before close time.");
		}

		if (start.equals(LocalTime.MIDNIGHT) && end.equals(LocalTime.MIDNIGHT)) {
			for (int hour = 0; hour < 24; hour++) {
				startTimeSlots.add(LocalTime.of(hour, 0).format(formatter));
			}
			return startTimeSlots; // 24시간 슬롯 반환
		}

		// term이 60분 고정이므로 end 이전까지만 슬롯 추가
		while (!start.plusMinutes(term).isAfter(end)) {
			startTimeSlots.add(start.format(formatter));
			start = start.plusMinutes(60); // 60분씩 증가

			// start가 하루를 초과하지 않도록 보장
			if (start.equals(LocalTime.MIDNIGHT)) { // 00:00으로 순환되었을 경우 종료
				break;
			}
		}

		return startTimeSlots;
	}
}
