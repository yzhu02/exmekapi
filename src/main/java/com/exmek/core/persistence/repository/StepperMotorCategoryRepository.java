package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.StepperMotorCategoryEntity;

public interface StepperMotorCategoryRepository 
extends BaseMotorCategoryRepository<StepperMotorCategoryEntity>, JpaRepository<StepperMotorCategoryEntity, Long>, JpaSpecificationExecutor<StepperMotorCategoryEntity> {

}
