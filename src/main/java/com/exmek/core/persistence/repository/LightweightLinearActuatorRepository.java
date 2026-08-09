package com.exmek.core.persistence.repository;

import com.exmek.core.persistence.entity.LightweightLinearActuatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LightweightLinearActuatorRepository extends BaseProductRepository<LightweightLinearActuatorEntity>, JpaRepository<LightweightLinearActuatorEntity, Long>, JpaSpecificationExecutor<LightweightLinearActuatorEntity> {
}
