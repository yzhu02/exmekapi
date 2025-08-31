package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.LightweightPlanetaryGearboxEntity;

public interface LightweightPlanetaryGearboxRepository extends BaseProductRepository<LightweightPlanetaryGearboxEntity>, JpaRepository<LightweightPlanetaryGearboxEntity, Long>, JpaSpecificationExecutor<LightweightPlanetaryGearboxEntity> {
}
