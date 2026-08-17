package com.exmek.core.persistence.repository;

import java.util.List;

import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.projection.TimestampOfSeries;

public interface BaseNonCategoryRepository<T extends AbstractProductEntity> extends BaseProductRepository<T> {

	List<TimestampOfSeries> findLastCreatedPerSeries();
}
