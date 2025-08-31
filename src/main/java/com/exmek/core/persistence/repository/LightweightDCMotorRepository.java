package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.LightweightDCMotorEntity;

public interface LightweightDCMotorRepository extends BaseProductRepository<LightweightDCMotorEntity>, JpaRepository<LightweightDCMotorEntity, Long>, JpaSpecificationExecutor<LightweightDCMotorEntity> {
}
