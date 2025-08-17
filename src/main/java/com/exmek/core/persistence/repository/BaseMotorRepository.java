package com.exmek.core.persistence.repository;

import java.util.List;

import com.exmek.core.persistence.entity.AbstractMotorEntity;
import com.exmek.core.persistence.projection.LastUpdatedTimestampPerSeries;

public interface BaseMotorRepository<T extends AbstractMotorEntity> extends BaseProductRepository<T> {

	List<LastUpdatedTimestampPerSeries> findLastUpdatedPerSeriesByCategory(String category);
}
