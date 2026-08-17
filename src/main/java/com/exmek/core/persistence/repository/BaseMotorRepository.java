package com.exmek.core.persistence.repository;

import java.util.List;

import com.exmek.core.persistence.entity.AbstractMotorEntity;
import com.exmek.core.persistence.projection.TimestampOfCategory;
import com.exmek.core.persistence.projection.TimestampOfSeries;

public interface BaseMotorRepository<T extends AbstractMotorEntity> extends BaseProductRepository<T> {

	List<TimestampOfSeries> findLastCreatedPerSeriesByCategory(String category);
	
	List<TimestampOfCategory> findLastCreatedPerCategory();
}
