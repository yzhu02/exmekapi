package com.exmek.commons.utils;

public class UrlUtils {

	private UrlUtils() {
	}

	public static String concatURL(String... components) {
		if (components == null) {
			return null;
		}
		if (components.length < 1) {
			return "";
		}
		String url = components[0];
		if (components.length == 1) {
			return url;
		}
		String pathSeparator = "/";
		String prevEnd = url;
		StringBuilder sb = new StringBuilder(url);
		for (int i=1; i<components.length; i++) {
			if (components[i] == null) {
				continue;
			}
			if (prevEnd.endsWith(pathSeparator)) {
				if (components[i].startsWith(pathSeparator)) {
					sb.append(components[i].substring(1));
				} else {
					sb.append(components[i]);
				}
			} else {
				if (components[i].startsWith(pathSeparator)) {
					sb.append(components[i]);
				} else {
					sb.append(pathSeparator);
					sb.append(components[i]);
				}
			}
			prevEnd = components[i];
		}
		return sb.toString();
	}
}
