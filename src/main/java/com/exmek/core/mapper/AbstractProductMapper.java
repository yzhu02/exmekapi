package com.exmek.core.mapper;

import java.util.function.Supplier;

import com.exmek.core.commons.model.MeasuredValue;
import com.exmek.core.model.AbstractProduct;
import com.exmek.core.persistence.entity.AbstractProductEntity;

public abstract class AbstractProductMapper {

	protected <T extends AbstractProduct, E extends AbstractProductEntity> T mapProduct(E entity, Supplier<T> modelCreator) {
		T model = modelCreator.get();
//		model.setId(entity.getId());
		model.setSeries(entity.getSeries());
		model.setModel(entity.getModel());
		model.setName(entity.getName());
		model.setDescription(entity.getDescription());
		model.setLength(MeasuredValue.of(entity.getLength(), entity.getLengthUnit()));
		model.setWeight(MeasuredValue.of(entity.getWeight(), entity.getWeightUnit()));
		model.setFrameSize(MeasuredValue.Typed.of(entity.getFrameSize(), entity.getFrameSizeUnit(), entity.getFrameSizeType()));
		model.setNemaSize(entity.getNemaSize());
		return model;
	}
}
