package com.exmek.core.mapper;

import java.util.Map;
import java.util.function.Supplier;

import com.exmek.commons.utils.JsonMapperUtils;
import com.exmek.core.model.AbstractSeries;
import com.exmek.core.persistence.entity.AbstractSeriesEntity;
import com.fasterxml.jackson.core.type.TypeReference;

public abstract class AbstractSeriesMapper<M extends AbstractSeries, E extends AbstractSeriesEntity> {

	public abstract M mapToSeriesModel(E entity);
	
	protected  M mapToSeriesModel(E entity, Supplier<M> modelCreator) {
		if (entity == null) {
			return null;
		}
		M model = modelCreator.get();
//		model.setId(entity.getId());
		model.setSeries(entity.getSeries());
		model.setDisplayName(entity.getDisplayName());
		model.setDescription(entity.getDescription());
		model.setTechnicalData(JsonMapperUtils.readValue(entity.getTechnicalData(), new TypeReference<Map<String, String>>() {}));
		return model;
	}
}
