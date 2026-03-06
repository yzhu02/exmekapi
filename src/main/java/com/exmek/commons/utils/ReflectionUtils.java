package com.exmek.commons.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.exmek.core.commons.enums.Symbolizable;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionUtils {
	
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

	public static Field getField(Class<?> type, String fieldName) {
		if (type == null || StringUtils.isEmpty(fieldName)) {
			return null;
		}
		Class<?> clazz = type;
		while (clazz != Object.class) {
			for (final Field f : clazz.getDeclaredFields()) {
				if (StringUtils.equals(f.getName(), fieldName)) {
					return f;
				}
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}
	
	public static Object readValueFromMethod(Object fromObject, String methodName, Class<?>[] paramTypes, Object... paramValues) {
		if (ObjectUtils.isEmpty(methodName) || fromObject == null) {
			return null;
		}
		Method method = null;
		try {
			if (ObjectUtils.isNotEmpty(paramTypes)) {
				method = fromObject.getClass().getMethod(methodName, paramTypes);
			} else {
				method = fromObject.getClass().getMethod(methodName);
			}
			if (method == null) {
				log.error("Can't find method {} from object of {} ", methodName, fromObject.getClass());
				return null;
			}
			if (log.isDebugEnabled()) {
				log.debug("The method {} is invoked. ", method);
			}
			Object resultValue = null;
			if (ObjectUtils.isNotEmpty(paramValues)) {
				resultValue = method.invoke(fromObject, paramValues);
			} else {
				resultValue = method.invoke(fromObject);
			}
			if (resultValue instanceof Optional) {
				@SuppressWarnings("unchecked")
				Optional<Object> op = (Optional<Object>) resultValue;
				if (op.isPresent()) {
					return op.get();
				} else {
					return null;
				}
			}
			return resultValue;
		} catch (Exception e) {
			log.error("Unable to read value from method: {} ", methodName, e);
			return null;
		}
	}

	public static Object readValue(Object fromObject, Method method) {
		try {
			return method.invoke(fromObject);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static Object readValue(Object fromObject, Field field) {
		field.setAccessible(true);
		try {
			return field.get(fromObject);
		} catch (IllegalArgumentException | IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static Enum<?> readEnumConstant(Class<? extends Enum<?>> enumClass, String enumStrVal) {
		if (enumClass == null || StringUtils.isEmpty(enumStrVal)) {
			return null;
		}
		Enum<?> enumConstValue = null;
		if (Symbolizable.class.isAssignableFrom(enumClass)) {
			enumConstValue = readEnumConstant(enumClass, enumStrVal, "fromSymbol");
		}
		if (enumConstValue == null) {
			enumConstValue = readEnumConstant(enumClass, enumStrVal, "valueOf");
		}
		return enumConstValue;
	}

	private static Enum<?> readEnumConstant(Class<? extends Enum<?>> enumClass, String enumStrVal, String readMethodName) {
		if (enumClass == null || StringUtils.isEmpty(enumStrVal)) {
			return null;
		}
		Method readMethod = null;
		try {
			readMethod = enumClass.getMethod(readMethodName, String.class);
		} catch (Exception e) {
			log.warn("No method of {}(String) of {} ", readMethodName, enumClass);
			return null;
		}
		Enum<?> enumConstValue = null;
		try {
			enumConstValue = (Enum<?>) readMethod.invoke(null, enumStrVal);
		} catch (Exception e) {
			log.error("Unable to read from {}.{}(String) ", enumClass, readMethodName, e);
		}
		return enumConstValue;
	}

	public static String getEnumJsonValue(Enum<?> enumObject) {
		if (enumObject == null) {
			return null;
		}
		Method jsonValueMethod = ReflectionUtils.getMethodAnnotatedWith(enumObject.getClass(), JsonValue.class);
		if (jsonValueMethod != null) {
			Object unitVal = ReflectionUtils.readValue(enumObject, jsonValueMethod);
			if (unitVal != null) {
				return unitVal.toString();
			}
		} else {
			Field jsonValueField = ReflectionUtils.getFieldAnnotatedWith(enumObject.getClass(), JsonValue.class);
			if (jsonValueField != null) {
				Object unitVal = ReflectionUtils.readValue(enumObject, jsonValueField);
				if (unitVal != null) {
					return unitVal.toString();
				}
			}
		}
		return enumObject.name();
	}

	public static Map<String, Field> collectFields(Class<?> clazz, Predicate<Field> filter) {
		Map<String, Field> fieldsMap = new HashMap<>();
		while (clazz != null && clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isStatic(field.getModifiers()) 
						|| Modifier.isTransient(field.getModifiers()) 
						|| Modifier.isVolatile(field.getModifiers())) {
					continue;
				}
				if (filter.test(field)) {
					fieldsMap.put(field.getName(), field);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return fieldsMap;
	}


}
