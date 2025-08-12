package com.exmek.commons.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.exmek.core.config.AppConfigProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	public static boolean isNonNullNonBlankValue(String str) {
		return StringUtils.isNotBlank(str) && !"null".equalsIgnoreCase(str.trim());
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
				parsedValues[r][c] = valueCreator.apply(rowValues[c]);
			}
		}
		return parsedValues;
	}
	
	public static BigDecimal parseBigDecimalValue(String s) {
		if (s == null || ObjectUtils.isEmpty(s)) {
			return null;
		}
		s = s.trim();
		if ("null".equals(s.toLowerCase())) {
			return null;
		}
		if ("-".equals(s) || "_".equals(s)) {
			return null;
		}
		try {
			return new BigDecimal(s);
		} catch (Exception ex) {
			log.error("Failed to parse {} to BigDecimal.", s, ex);
			return null;
		}
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

	public static String fieldNameToDisplayName(String fieldName, AppConfigProvider appConfigProvider) {
		//Example: ratedVoltage -> Rated Voltage
		if (ObjectUtils.isEmpty(fieldName)) {
			return fieldName;
		}
		if (appConfigProvider != null) {
			Map<String, String> fieldDisplayNameMappings = appConfigProvider.getMetaFieldDisplayNameMappings();
			if (fieldDisplayNameMappings != null) {
				String displayName = fieldDisplayNameMappings.get(fieldName);
				if (StringUtils.isNotEmpty(displayName)) {
					return displayName;
				}
			}
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

	public static int findLastDigitIndexBackward(String s) {
		if (s == null || s.length() == 0) {
			return -1;
		}
		for (int i= s.length() - 1; i >= 0; i--) {
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				return i;
			}
		}
		return -1;
	}

}
