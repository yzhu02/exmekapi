package com.exmek.core.mapper;

import org.springframework.stereotype.Component;

import com.exmek.core.model.GearboxSeries;
import com.exmek.core.persistence.entity.GearboxSeriesEntity;

@Component
public class GearboxSeriesMapper extends AbstractSeriesMapper {

	public GearboxSeries mapToSeriesModel(GearboxSeriesEntity entity) {
		return super.mapToSeriesModel(entity, GearboxSeries::new);
	}
}
