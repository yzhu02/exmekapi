package com.exmek.core.utils;

import org.springframework.util.ObjectUtils;

public class ExmekUtils {

	private ExmekUtils() {
	}

	public static String fieldNameToDisplayName(String fieldName) {
		//Example: ratedVoltage -> Rated Voltage
		if (ObjectUtils.isEmpty(fieldName)) {
			return fieldName;
		}
		StringBuilder sb = new StringBuilder();
		int prevUppercaseInx = 0;
		for (int i=0; i<fieldName.length(); i++) {
			char c = fieldName.charAt(i);
			if (i == 0) {
				sb.append(Character.toUpperCase(c));
				prevUppercaseInx = i;
			} else {
				if (Character.isUpperCase(c)) {
					if (i == prevUppercaseInx + 1) {
						if (i + 1 < fieldName.length()) {
							if (Character.isLowerCase(fieldName.charAt(i + 1))) {
								sb.append(" ").append(c);
							} else {
								sb.append(c);
							}
						} else {
							sb.append(c);
						}
					} else {
						sb.append(" ").append(c);
					}
					prevUppercaseInx = i;
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}
}
