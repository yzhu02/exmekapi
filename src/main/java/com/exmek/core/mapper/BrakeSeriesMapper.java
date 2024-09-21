package com.exmek.core.mapper;

import org.springframework.stereotype.Component;

import com.exmek.core.model.BrakeSeries;
import com.exmek.core.persistence.entity.BrakeSeriesEntity;

@Component
public class BrakeSeriesMapper extends AbstractSeriesMapper {

	public BrakeSeries mapToSeriesModel(BrakeSeriesEntity entity) {
		return super.mapToSeriesModel(entity, BrakeSeries::new);
	}
}
