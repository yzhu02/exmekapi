package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.StepperMotorEntity;

public interface LightStepperMotorRepository extends BaseProductRepository<StepperMotorEntity.Light>, JpaRepository<StepperMotorEntity.Light, Long>, JpaSpecificationExecutor<StepperMotorEntity.Light> {
}
