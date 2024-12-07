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

		while (!start.isAfter(end.minusMinutes(term))) {
			startTimeSlots.add(start.format(formatter));
			start = start.plusMinutes(term);
		}

		return startTimeSlots;
	}
}
