package com.toucheese.studio.util;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateUtils {

	// 특정 날짜로부터 요일 반환 (예: "월", "화", "수")
	public static String getDayOfWeekFromDate(String date) {
		// 입력받은 날짜를 LocalDate로 변환
		LocalDate localDate = LocalDate.parse(date); // 형식: "yyyy-MM-dd"
		return localDate.getDayOfWeek()
			.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
			.substring(0, 1); // "월요일" → "월"
	}
}