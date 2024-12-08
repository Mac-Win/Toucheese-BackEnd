package com.toucheese.reservation.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvUtils {
	public static String toCsv(List<Long> ids) {
		return ids.stream().map(String::valueOf).collect(Collectors.joining(","));
	}

	public static List<Long> fromCsv(String csv) {
		return Arrays.stream(csv.split(","))
			.map(Long::valueOf)
			.collect(Collectors.toList());
	}
}
