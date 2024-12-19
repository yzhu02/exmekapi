package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.GearboxSeriesEntity;

public interface GearboxSeriesRepository 
extends BaseSeriesRepository<GearboxSeriesEntity>, JpaRepository<GearboxSeriesEntity, Long>, JpaSpecificationExecutor<GearboxSeriesEntity> {

}
