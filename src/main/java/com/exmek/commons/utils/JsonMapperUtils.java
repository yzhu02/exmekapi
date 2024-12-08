package com.exmek.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapperUtils {

	private static final Logger logger = LoggerFactory.getLogger(JsonMapperUtils.class);

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	static {
		objectMapper.setSerializationInclusion(Include.NON_NULL);
	}

	private JsonMapperUtils() {
	}

	public static <T> T readValue(String valueStr, TypeReference<T> typeRef) {
		if (ObjectUtils.isEmpty(valueStr)) {
			return null;
		}
		try {
			return objectMapper.readValue(valueStr, typeRef);
		} catch (JsonProcessingException e) {
			logger.error("Failed to deserialize to {} object mapping from json string: {} ", typeRef.getType(), valueStr, e);
			return null;
		}
	}
	
	public static String writeValueAsString(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("Failed to serialize to json string from given object", e);
			return null;
		}
	}
}
