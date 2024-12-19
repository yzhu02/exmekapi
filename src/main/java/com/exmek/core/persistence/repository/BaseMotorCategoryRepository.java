package com.exmek.core.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.exmek.core.model.MotorCategory;
import com.exmek.core.persistence.entity.AbstractMotorCategoryEntity;

public interface BaseMotorCategoryRepository<T extends AbstractMotorCategoryEntity> {

	Optional<T> findByCategory(String category);
	
	List<T> findByType(MotorCategory.Type type);
	
	List<T> findAll();
}
