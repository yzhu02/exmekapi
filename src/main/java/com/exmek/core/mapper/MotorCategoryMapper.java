package com.exmek.core.mapper;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.exmek.commons.utils.JsonMapperUtils;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.persistence.entity.AbstractMotorCategoryEntity;
import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class MotorCategoryMapper {

	public MotorCategory mapToCategoryModel(AbstractMotorCategoryEntity entity) {
		return mapEntityToCategory(entity, true);
	}

	static MotorCategory mapEntityToCategory(AbstractMotorCategoryEntity entity, boolean shouldMapTechnicalData) {
		if (entity == null) {
			return null;
		}
		MotorCategory mc = new MotorCategory();
//		mc.setId(entity.getId());
		mc.setCategory(entity.getCategory());
		mc.setType(entity.getType());
		mc.setDisplayName(entity.getDisplayName());
		mc.setDescription(entity.getDescription());
		if (shouldMapTechnicalData) {
			mc.setTechnicalData(JsonMapperUtils.readValue(entity.getTechnicalData(), new TypeReference<Map<String, String>>() {}));
		}
		return mc;
	}
}
