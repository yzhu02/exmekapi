package com.exmek.core.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.model.MotorCategory;
import com.exmek.core.persistence.entity.MotorCategoryEntity;

public interface MotorCategoryRepository extends JpaRepository<MotorCategoryEntity, Long>, JpaSpecificationExecutor<MotorCategoryEntity> {

	Optional<MotorCategoryEntity> findByCategory(MotorCategory.Category category);
}
