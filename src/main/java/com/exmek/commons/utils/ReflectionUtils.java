package com.exmek.commons.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonValue;

public class ReflectionUtils {

	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
	
	private ReflectionUtils() {
	}

	public static Method getMethodAnnotatedWith(Class<?> type, Class<? extends Annotation> annotationClass) {
		List<Method> methods = getMethodsAnnotatedWith(type, annotationClass);
		if (methods == null || methods.size() == 0) {
			return null;
		}
		return methods.get(0);
	}

	public static List<Method> getMethodsAnnotatedWith(Class<?> type, Class<? extends Annotation> annotationClass) {
		if (type == null || annotationClass == null) {
			return null;
		}
		List<Method> resultMethods = new ArrayList<>();
		Method[] publicMethods = type.getMethods();
		for (final Method m : publicMethods) {
			if (m.isAnnotationPresent(annotationClass)) {
				resultMethods.add(m);
			}
		}
		return resultMethods;
	}

	public static Field getFieldAnnotatedWith(Class<?> type, Class<? extends Annotation> annotationClass) {
		List<Field> fields = getFieldsAnnotatedWith(type, annotationClass);
		if (fields == null || fields.size() == 0) {
			return null;
		}
		return fields.get(0);
	}

	public static List<Field> getFieldsAnnotatedWith(Class<?> type, Class<? extends Annotation> annotationClass) {
		if (type == null || annotationClass == null) {
			return null;
		}
		List<Field> resultFields = new ArrayList<>();
		Class<?> clazz = type;
		while (clazz != Object.class) {
			for (final Field f : clazz.getDeclaredFields()) {
				if (f.isAnnotationPresent(annotationClass)) {
					resultFields.add(f);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return resultFields;
	}

	public static Object readValueFromMethod(String methodName, Object fromObject) {
		if (ObjectUtils.isEmpty(methodName) || fromObject == null) {
			return null;
		}
		Method method = null;
		try {
			method = fromObject.getClass().getMethod(methodName);
			if (method != null) {
				Object value = method.invoke(fromObject);
				if (value instanceof Optional) {
					@SuppressWarnings("unchecked")
					Optional<Object> op = (Optional<Object>) value;
					if (op.isPresent()) {
						return op.get();
					} else {
						return null;
					}
				}
				return value;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Unable to read value from method: {} ", methodName, e);
			return null;
		}
	}

	public static Object readValue(Method method, Object fromObject) {
		try {
			return method.invoke(fromObject);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static Object readValue(Field field, Object fromObject) {
		field.setAccessible(true);
		try {
			return field.get(fromObject);
		} catch (IllegalArgumentException | IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String getEnumJsonValue(Enum<?> enumObject) {
		if (enumObject == null) {
			return null;
		}
		Method jsonValueMethod = ReflectionUtils.getMethodAnnotatedWith(enumObject.getClass(), JsonValue.class);
		if (jsonValueMethod != null) {
			Object unitVal = ReflectionUtils.readValue(jsonValueMethod, enumObject);
			if (unitVal != null) {
				return unitVal.toString();
			}
		} else {
			Field jsonValueField = ReflectionUtils.getFieldAnnotatedWith(enumObject.getClass(), JsonValue.class);
			if (jsonValueField != null) {
				Object unitVal = ReflectionUtils.readValue(jsonValueField, enumObject);
				if (unitVal != null) {
					return unitVal.toString();
				}
			}
		}
		return enumObject.name();
	}
}
