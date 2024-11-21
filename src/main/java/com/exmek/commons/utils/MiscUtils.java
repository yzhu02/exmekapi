package com.exmek.commons.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class MiscUtils {
	
	private MiscUtils() {
	}

    public static boolean isNumeric(final CharSequence cs) {
        if (cs == null || cs.isEmpty()) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

	public static <T> void addNonNullToList(List<T> resultList, Supplier<T> creator) {
		if (resultList == null) {
			return;
		}
		T element = creator.get();
		if (element != null) {
			resultList.add(element);
		}
	}
	
	public static String[] split(String content, String delimiterRegex) {
		if (content == null || content.isBlank()) {
			return null;
		}
		String[] splittArray = content.split(delimiterRegex);
		for (int i = 0; i < splittArray.length; i++) {
			splittArray[i] = splittArray[i].trim();
		}
		return splittArray;
	}

	public static <T> T[][] parseCSVLikeValues(String content,
			Function<Integer, T[][]> rowsArrayCreator, Function<Integer, T[]> rowOfCellsArrayCreator, Function<String, T> valueCreator) {
		if (content == null || content.isBlank()) {
			return null;
		}
		String[] valueLines = content.split("\n");
		T[][] parsedValues = rowsArrayCreator.apply(valueLines.length);
		for (int r = 0; r < valueLines.length; r++) {
			String[] rowValues = valueLines[r].split(",");
			parsedValues[r] = rowOfCellsArrayCreator.apply(rowValues.length);
			for (int c = 0; c < rowValues.length; c++) {
				parsedValues[r][c] = valueCreator.apply(rowValues[c].trim());
			}
		}
		return parsedValues;
	}

	public static int findIndex(String[] sourceArray, String targetToFind) {
		if (sourceArray == null || sourceArray.length < 1) {
			return -1;
		}
		for (int i = 0; i < sourceArray.length; i++) {
			if (Objects.equals(sourceArray[i], targetToFind)) {
				return i;
			}
		}
		return -1;
	}

	public static void requireNonEmpty(String s, String message) {
		if (ObjectUtils.isEmpty(s)) {
			throw new NullPointerException(message);
		}
	}

	public static void requireAllNonEmpty(String[] ss, String message) {
		if (ObjectUtils.isEmpty(ss)) {
			throw new NullPointerException(message);
		}
		for (String s : ss) {
			if (ObjectUtils.isEmpty(s)) {
				throw new NullPointerException(message);
			}
		}
	}

	public static String joinAsStr(List<?> objs, String delimiter, String quote) {
		if (objs == null) {
			return null;
		}
		if (CollectionUtils.isEmpty(objs)) {
			return "";
		}
		return objs.stream()
				.filter(obj -> obj != null)
				.map(obj -> toStrQuoted(obj, quote))
				.collect(Collectors.joining(delimiter));
	}

	private static String toStrQuoted(Object obj, String quote) {
		String s = obj.toString();
		return quote != null ? quote + s + quote : s;
	}
}
