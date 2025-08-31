package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.LightweightBrakeEntity;

public interface LightweightBrakeRepository extends BaseProductRepository<LightweightBrakeEntity>, JpaRepository<LightweightBrakeEntity, Long>, JpaSpecificationExecutor<LightweightBrakeEntity> {
}
