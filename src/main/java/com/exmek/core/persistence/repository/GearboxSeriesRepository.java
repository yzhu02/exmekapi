package com.exmek.core.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.GearboxSeriesEntity;

public interface GearboxSeriesRepository extends JpaRepository<GearboxSeriesEntity, Long>, JpaSpecificationExecutor<GearboxSeriesEntity> {
	
	Optional<GearboxSeriesEntity> findBySeries(String series);
}
