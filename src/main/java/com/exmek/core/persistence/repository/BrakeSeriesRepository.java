package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.BrakeSeriesEntity;

public interface BrakeSeriesRepository 
extends BaseSeriesRepository<BrakeSeriesEntity>, JpaRepository<BrakeSeriesEntity, Long>, JpaSpecificationExecutor<BrakeSeriesEntity> {

}
