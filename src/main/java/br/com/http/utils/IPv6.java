package br.com.http.utils;

import java.util.regex.Pattern;

public class IPv6 {

	private static final Pattern IPv6_GROUP_PATTERN = Pattern.compile("([0-9A-Fa-f]{1,4})?");

	public static boolean isIPv6(String address) {
		if (!address.contains(":")) {
			return false;
		}

		String[] groups = address.split(":");

		if (groups.length > 9) {
			return false;
		}

		for (String group : groups) {
			if (!IPv6_GROUP_PATTERN.matcher(group).matches()) {
				return false;
			}
		}

		return true;
	}
}
