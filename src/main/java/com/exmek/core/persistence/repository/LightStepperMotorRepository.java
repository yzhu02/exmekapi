package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.LightweightStepperMotorEntity;

public interface LightStepperMotorRepository extends BaseProductRepository<LightweightStepperMotorEntity>, JpaRepository<LightweightStepperMotorEntity, Long>, JpaSpecificationExecutor<LightweightStepperMotorEntity> {
}
