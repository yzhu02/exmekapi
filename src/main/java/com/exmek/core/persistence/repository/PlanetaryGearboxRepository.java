package com.exmek.core.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.exmek.core.persistence.entity.PlanetaryGearboxEntity;

public interface PlanetaryGearboxRepository extends BaseProductRepository<PlanetaryGearboxEntity>, JpaRepository<PlanetaryGearboxEntity, Long>, JpaSpecificationExecutor<PlanetaryGearboxEntity> {

	
	//length
	@Query("SELECT MIN(p.length) FROM PlanetaryGearboxEntity p")
    Optional<BigDecimal> findMinLength();

	@Query("SELECT MAX(p.length) FROM PlanetaryGearboxEntity p")
    Optional<BigDecimal> findMaxLength();
	
	@Query("SELECT DISTINCT p.lengthUnit FROM PlanetaryGearboxEntity p WHERE p.lengthUnit IS NOT NULL")
    Optional<List<String>> findLengthUnits();
	//


	//weight
	@Query("SELECT MIN(p.weight) FROM PlanetaryGearboxEntity p")
    Optional<BigDecimal> findMinWeight();

	@Query("SELECT MAX(p.weight) FROM PlanetaryGearboxEntity p")
    Optional<BigDecimal> findMaxWeight();
	
	@Query("SELECT DISTINCT p.weightUnit FROM PlanetaryGearboxEntity p WHERE p.weightUnit IS NOT NULL")
    Optional<List<String>> findWeightUnits();
	//

	
	//frameSize
	@Query("SELECT MIN(p.frameSize) FROM PlanetaryGearboxEntity p")
	Optional<BigDecimal> findMinFrameSize();

	@Query("SELECT MAX(p.frameSize) FROM PlanetaryGearboxEntity p")
	Optional<BigDecimal> findMaxFrameSize();
	
	@Query("SELECT DISTINCT p.frameSizeUnit FROM PlanetaryGearboxEntity p WHERE p.frameSizeUnit IS NOT NULL")
    Optional<List<String>> findFrameSizeUnits();
	//

	
	//nemaSize
	@Query("SELECT MIN(p.nemaSize) FROM PlanetaryGearboxEntity p")
	Optional<BigDecimal> findMinNemaSize();

	@Query("SELECT MAX(p.nemaSize) FROM PlanetaryGearboxEntity p")
	Optional<BigDecimal> findMaxNemaSize();
	//

	
	
	
	//numOfStages
	@Query("SELECT MIN(p.numOfStages) FROM PlanetaryGearboxEntity p")
	Optional<BigDecimal> findMinNumOfStages();

	@Query("SELECT MAX(p.numOfStages) FROM PlanetaryGearboxEntity p")
	Optional<BigDecimal> findMaxNumOfStages();
	//


	//efficiency
	@Query("SELECT MIN(p.efficiency) FROM PlanetaryGearboxEntity p")
	Optional<BigDecimal> findMinEfficiency();

	@Query("SELECT MAX(p.efficiency) FROM PlanetaryGearboxEntity p")
	Optional<BigDecimal> findMaxEfficiency();
	
	@Query("SELECT DISTINCT p.efficiencyUnit FROM PlanetaryGearboxEntity p WHERE p.efficiencyUnit IS NOT NULL")
    Optional<List<String>> findEfficiencyUnits();
	//

	
	//ratedContinuousTorque
	@Query("SELECT MIN(p.ratedContinuousTorque) FROM PlanetaryGearboxEntity p")
	Optional<BigDecimal> findMinRatedContinuousTorque();

	@Query("SELECT MAX(p.ratedContinuousTorque) FROM PlanetaryGearboxEntity p")
	Optional<BigDecimal> findMaxRatedContinuousTorque();
	
	@Query("SELECT DISTINCT p.ratedContinuousTorqueUnit FROM PlanetaryGearboxEntity p WHERE p.ratedContinuousTorqueUnit IS NOT NULL")
    Optional<List<String>> findRatedContinuousTorqueUnits();
	//
	

	//maxMomentaryTorque
	@Query("SELECT MIN(p.maxMomentaryTorque) FROM PlanetaryGearboxEntity p")
	Optional<BigDecimal> findMinMaxMomentaryTorque();

	@Query("SELECT MAX(p.maxMomentaryTorque) FROM PlanetaryGearboxEntity p")
	Optional<BigDecimal> findMaxMaxMomentaryTorque();
	
	@Query("SELECT DISTINCT p.maxMomentaryTorqueUnit FROM PlanetaryGearboxEntity p WHERE p.maxMomentaryTorqueUnit IS NOT NULL")
    Optional<List<String>> findMaxMomentaryTorqueUnits();
	//
}
