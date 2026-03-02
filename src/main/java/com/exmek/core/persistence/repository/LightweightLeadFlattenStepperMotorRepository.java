package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.LightweightLeadFlattenStepperMotorEntity;

public interface LightweightLeadFlattenStepperMotorRepository extends BaseProductRepository<LightweightLeadFlattenStepperMotorEntity>, JpaRepository<LightweightLeadFlattenStepperMotorEntity, Long>, JpaSpecificationExecutor<LightweightLeadFlattenStepperMotorEntity> {
}
