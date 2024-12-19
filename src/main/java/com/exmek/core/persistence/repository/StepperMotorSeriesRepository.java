package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.StepperMotorSeriesEntity;

public interface StepperMotorSeriesRepository 
extends BaseMotorSeriesRepository<StepperMotorSeriesEntity>, JpaRepository<StepperMotorSeriesEntity, Long>, JpaSpecificationExecutor<StepperMotorSeriesEntity> {

}
