package com.exmek.core.mapper;

import java.util.Map;
import java.util.function.Supplier;

import com.exmek.core.model.AbstractSeries;
import com.exmek.core.persistence.entity.AbstractSeriesEntity;
import com.fasterxml.jackson.core.type.TypeReference;

import commons.utils.JsonMapperUtils;

public abstract class AbstractSeriesMapper {

	protected <M extends AbstractSeries, E extends AbstractSeriesEntity> M mapToSeriesModel(E entity, Supplier<M> modelCreator) {
		if (entity == null) {
			return null;
		}
		M model = modelCreator.get();
//		model.setId(entity.getId());
		model.setSeries(entity.getSeries());
		model.setDescription(entity.getDescription());
		model.setTechnicalData(JsonMapperUtils.readValue(entity.getTechnicalData(), new TypeReference<Map<String, String>>() {}));
		return model;
	}
}
