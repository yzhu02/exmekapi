package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.DCMotorSeriesEntity;

public interface DCMotorSeriesRepository 
extends BaseMotorSeriesRepository<DCMotorSeriesEntity>, JpaRepository<DCMotorSeriesEntity, Long>, JpaSpecificationExecutor<DCMotorSeriesEntity> {

}
