package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.PlanetaryGearboxEntity;

public interface LightPlanetaryGearboxRepository extends BaseProductRepository<PlanetaryGearboxEntity.Light>, JpaRepository<PlanetaryGearboxEntity.Light, Long>, JpaSpecificationExecutor<PlanetaryGearboxEntity.Light> {
}
