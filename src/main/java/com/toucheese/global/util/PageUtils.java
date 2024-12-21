package com.toucheese.global.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtils {
	private PageUtils() {
		throw new UnsupportedOperationException("Utility class cannot be instantiated");
	}

	private static final int PAGE_SIZE = 10;

	public static Pageable createPageable(int page) {
		return PageRequest.of(page, PAGE_SIZE);
	}
}
