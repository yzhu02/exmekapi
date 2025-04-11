package com.exmek.core.mapper;

import java.util.Map;
import java.util.function.Supplier;

import com.exmek.commons.utils.JsonMapperUtils;
import com.exmek.core.model.AbstractSeries;
import com.exmek.core.persistence.entity.AbstractSeriesEntity;
import com.fasterxml.jackson.core.type.TypeReference;

public abstract class AbstractSeriesMapper<M extends AbstractSeries, E extends AbstractSeriesEntity> {

	public abstract M mapToSeriesModel(E entity);
	
	protected M mapToSeriesModel(E entity, Supplier<M> creator) {
		return mapEntityToSeries(entity, creator, true);
	}

	static <M extends AbstractSeries, E extends AbstractSeriesEntity> M mapEntityToSeries(E entity, 
			Supplier<M> modelCreator, boolean shouldMapTechnicalData) {
		if (entity == null) {
			return null;
		}
		M series = modelCreator.get();
//		series.setId(entity.getId());
		series.setSeries(entity.getSeries());
		series.setDisplayName(entity.getDisplayName());
		series.setDescription(entity.getDescription());
		if (shouldMapTechnicalData) {
			series.setTechnicalData(JsonMapperUtils.readValue(entity.getTechnicalData(), new TypeReference<Map<String, String>>() {}));
		}
		return series;
	}
}
