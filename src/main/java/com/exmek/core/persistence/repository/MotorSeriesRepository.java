package com.exmek.core.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.model.MotorCategory;
import com.exmek.core.persistence.entity.MotorSeriesEntity;

public interface MotorSeriesRepository extends JpaRepository<MotorSeriesEntity, Long>, JpaSpecificationExecutor<MotorSeriesEntity> {

	List<MotorSeriesEntity> findAllByCategory(MotorCategory.Category category);
	
	Page<MotorSeriesEntity> findAllByCategory(MotorCategory.Category category, Pageable pageable);
	
	Optional<MotorSeriesEntity> findBySeries(String series);
}
