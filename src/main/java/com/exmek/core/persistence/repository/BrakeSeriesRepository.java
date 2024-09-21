package com.exmek.core.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.BrakeSeriesEntity;

public interface BrakeSeriesRepository extends JpaRepository<BrakeSeriesEntity, Long>, JpaSpecificationExecutor<BrakeSeriesEntity> {
	
	Optional<BrakeSeriesEntity> findBySeries(String series);
}
