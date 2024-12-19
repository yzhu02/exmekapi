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
		if (entity == null) {
			return null;
		}
		MotorCategory m = new MotorCategory();
//		m.setId(entity.getId());
		m.setCategory(entity.getCategory());
		m.setType(entity.getType());
		m.setDisplayName(entity.getDisplayName());
		m.setDescription(entity.getDescription());
		m.setTechnicalData(JsonMapperUtils.readValue(entity.getTechnicalData(), new TypeReference<Map<String, String>>() {}));
		return m;
	}

}
