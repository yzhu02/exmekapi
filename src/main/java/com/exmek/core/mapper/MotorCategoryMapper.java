package com.exmek.core.mapper;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.exmek.core.model.MotorCategory;
import com.exmek.core.persistence.entity.MotorCategoryEntity;
import com.fasterxml.jackson.core.type.TypeReference;

import commons.utils.JsonMapperUtils;

@Component
public class MotorCategoryMapper {

	public MotorCategory mapToCategoryModel(MotorCategoryEntity entity) {
		if (entity == null) {
			return null;
		}
		MotorCategory m = new MotorCategory();
//		m.setId(entity.getId());
		m.setCategory(entity.getCategory());
		m.setDisplayName(entity.getDisplayName());
		m.setDescription(entity.getDescription());
		m.setTechnicalData(JsonMapperUtils.readValue(entity.getTechnicalData(), new TypeReference<Map<String, String>>() {}));
		return m;
	}

}
