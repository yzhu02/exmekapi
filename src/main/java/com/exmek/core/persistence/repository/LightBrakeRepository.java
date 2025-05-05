package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.BrakeEntity;

public interface LightBrakeRepository extends BaseProductRepository<BrakeEntity.Light>, JpaRepository<BrakeEntity.Light, Long>, JpaSpecificationExecutor<BrakeEntity.Light> {
}
