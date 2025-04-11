package com.exmek.core.mapper;

import org.springframework.stereotype.Component;

import com.exmek.core.model.MotorSeries;
import com.exmek.core.persistence.entity.AbstractMotorSeriesEntity;
import com.exmek.core.persistence.entity.DCMotorSeriesEntity;
import com.exmek.core.persistence.entity.StepperMotorSeriesEntity;

@Component
public class MotorSeriesMapper extends AbstractSeriesMapper<MotorSeries, AbstractMotorSeriesEntity> {

	@Override
	public MotorSeries mapToSeriesModel(AbstractMotorSeriesEntity entity) {
		if (entity == null) {
			return null;
		}
		MotorSeries m = super.mapToSeriesModel(entity, MotorSeries::new);
		m.setCategory(entity.getCategory());
		if (entity instanceof DCMotorSeriesEntity) {
			m.setMotorCategory(MotorCategoryMapper.mapEntityToCategory(((DCMotorSeriesEntity) entity).getMotorCategory(), false));
		} else if (entity instanceof StepperMotorSeriesEntity) {
			m.setMotorCategory(MotorCategoryMapper.mapEntityToCategory(((StepperMotorSeriesEntity) entity).getMotorCategory(), false));
		}
		return m;
	}
}
