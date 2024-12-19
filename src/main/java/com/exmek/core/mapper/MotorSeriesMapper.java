package com.exmek.core.mapper;

import org.springframework.stereotype.Component;

import com.exmek.core.model.MotorSeries;
import com.exmek.core.persistence.entity.AbstractMotorSeriesEntity;

@Component
public class MotorSeriesMapper extends AbstractSeriesMapper<MotorSeries, AbstractMotorSeriesEntity> {

	@Override
	public MotorSeries mapToSeriesModel(AbstractMotorSeriesEntity entity) {
		if (entity == null) {
			return null;
		}
		MotorSeries m = super.mapToSeriesModel(entity, MotorSeries::new);
		m.setCategory(entity.getCategory());
		return m;
	}
}
