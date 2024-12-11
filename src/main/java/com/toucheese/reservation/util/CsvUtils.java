package com.toucheese.reservation.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvUtils {
	public static String toCsv(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return "";
		}
		return ids.stream().map(String::valueOf).collect(Collectors.joining(","));
	}

	public static List<Long> fromCsv(String csv) {
		if (csv == null || csv.isEmpty()) {
			return List.of();
		}
		try {
			return Arrays.stream(csv.split(","))
				.map(Long::valueOf)
				.collect(Collectors.toList());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("CSV 문자열 형식이 잘못되었습니다: " + csv, e);
		}
	}
}