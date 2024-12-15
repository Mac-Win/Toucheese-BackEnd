package com.toucheese.global.util;

import java.security.Principal;

public class PrincipalUtils {

	private PrincipalUtils() {
		throw new UnsupportedOperationException("Utility class should not be instantiated");
	}

	public static Long extractMemberId(Principal principal) {
		return Long.parseLong(principal.getName());
	}
}