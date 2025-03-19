package com.exmek.core.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exmek.core.commons.enums.LengthUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.commons.enums.WeightUnit;
import com.exmek.core.commons.model.Range;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.PlanetaryGearboxEntity;

public interface PlanetaryGearboxRepository extends BaseProductRepository<PlanetaryGearboxEntity>, JpaRepository<PlanetaryGearboxEntity, Long>, JpaSpecificationExecutor<PlanetaryGearboxEntity> {

	
	//length
//	@Query("SELECT MIN(p.length) FROM PlanetaryGearboxEntity p")
//    Optional<BigDecimal> findMinLength();
//
//	@Query("SELECT MAX(p.length) FROM PlanetaryGearboxEntity p")
//    Optional<BigDecimal> findMaxLength();
//	
//	@Query("SELECT DISTINCT p.lengthUnit FROM PlanetaryGearboxEntity p WHERE p.lengthUnit IS NOT NULL")
//    Optional<List<String>> findLengthUnits();

	@Query("""
			SELECT MIN(m.length), MAX(m.length), m.lengthUnit 
			FROM PlanetaryGearboxEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.length IS NOT NULL AND m.lengthUnit IS NOT NULL 
			GROUP BY m.lengthUnit 
			"""
	)
	List<Object[]> findLengthMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<LengthUnit, Range<Integer>> findLengthMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findLengthMinMaxByUnits);
	}
	//


	//weight
//	@Query("SELECT MIN(p.weight) FROM PlanetaryGearboxEntity p")
//    Optional<BigDecimal> findMinWeight();
//
//	@Query("SELECT MAX(p.weight) FROM PlanetaryGearboxEntity p")
//    Optional<BigDecimal> findMaxWeight();
//	
//	@Query("SELECT DISTINCT p.weightUnit FROM PlanetaryGearboxEntity p WHERE p.weightUnit IS NOT NULL")
//    Optional<List<String>> findWeightUnits();

	@Query("""
			SELECT MIN(m.weight), MAX(m.weight), m.weightUnit 
			FROM PlanetaryGearboxEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.weight IS NOT NULL AND m.weightUnit IS NOT NULL 
			GROUP BY m.weightUnit 
			"""
	)
	List<Object[]> findWeightMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<WeightUnit, Range<BigDecimal>> findWeightMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findWeightMinMaxByUnits);
	}
	//

	
	//frameSize
//	@Query("SELECT MIN(p.frameSize) FROM PlanetaryGearboxEntity p")
//	Optional<BigDecimal> findMinFrameSize();
//
//	@Query("SELECT MAX(p.frameSize) FROM PlanetaryGearboxEntity p")
//	Optional<BigDecimal> findMaxFrameSize();
//	
//	@Query("SELECT DISTINCT p.frameSizeUnit FROM PlanetaryGearboxEntity p WHERE p.frameSizeUnit IS NOT NULL")
//    Optional<List<String>> findFrameSizeUnits();

	@Query("""
			SELECT MIN(m.frameSize), MAX(m.frameSize), m.frameSizeUnit 
			FROM PlanetaryGearboxEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.frameSize IS NOT NULL AND m.frameSizeUnit IS NOT NULL 
			GROUP BY m.frameSizeUnit 
			"""
	)
	List<Object[]> findFrameSizeMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<LengthUnit, Range<BigDecimal>> findFrameSizeMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findFrameSizeMinMaxByUnits);
	}
	//

	
	//nemaSize
//	@Query("SELECT MIN(p.nemaSize) FROM PlanetaryGearboxEntity p")
//	Optional<BigDecimal> findMinNemaSize();
//
//	@Query("SELECT MAX(p.nemaSize) FROM PlanetaryGearboxEntity p")
//	Optional<BigDecimal> findMaxNemaSize();

	@Query("""
			SELECT MIN(m.nemaSize), MAX(m.nemaSize), '' 
			FROM PlanetaryGearboxEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series) 
				AND m.nemaSize IS NOT NULL
			"""
	)
	List<Object[]> findNemaSizeMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<String, Range<BigDecimal>> findNemaSizeMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findNemaSizeMinMaxByUnits);
	}
	//
	
	
	//numOfStages
//	@Query("SELECT MIN(p.numOfStages) FROM PlanetaryGearboxEntity p")
//	Optional<BigDecimal> findMinNumOfStages();
//
//	@Query("SELECT MAX(p.numOfStages) FROM PlanetaryGearboxEntity p")
//	Optional<BigDecimal> findMaxNumOfStages();

	@Query("""
			SELECT MIN(m.numOfStages), MAX(m.numOfStages), '' 
			FROM PlanetaryGearboxEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series) 
				AND m.numOfStages IS NOT NULL
			"""
	)
	List<Object[]> findNumOfStagesMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<String, Range<Integer>> findNumOfStagesMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findNumOfStagesMinMaxByUnits);
	}
	//


	//efficiency
//	@Query("SELECT MIN(p.efficiency) FROM PlanetaryGearboxEntity p")
//	Optional<BigDecimal> findMinEfficiency();
//
//	@Query("SELECT MAX(p.efficiency) FROM PlanetaryGearboxEntity p")
//	Optional<BigDecimal> findMaxEfficiency();
//	
//	@Query("SELECT DISTINCT p.efficiencyUnit FROM PlanetaryGearboxEntity p WHERE p.efficiencyUnit IS NOT NULL")
//    Optional<List<String>> findEfficiencyUnits();

	@Query("""
			SELECT MIN(m.efficiency), MAX(m.efficiency), m.efficiencyUnit 
			FROM PlanetaryGearboxEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.efficiency IS NOT NULL AND m.efficiencyUnit IS NOT NULL 
			GROUP BY m.efficiencyUnit 
			"""
	)
	List<Object[]> findEfficiencyMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<String, Range<BigDecimal>> findEfficiencyMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findEfficiencyMinMaxByUnits);
	}
	//

	
	//ratedContinuousTorque
//	@Query("SELECT MIN(p.ratedContinuousTorque) FROM PlanetaryGearboxEntity p")
//	Optional<BigDecimal> findMinRatedContinuousTorque();
//
//	@Query("SELECT MAX(p.ratedContinuousTorque) FROM PlanetaryGearboxEntity p")
//	Optional<BigDecimal> findMaxRatedContinuousTorque();
//	
//	@Query("SELECT DISTINCT p.ratedContinuousTorqueUnit FROM PlanetaryGearboxEntity p WHERE p.ratedContinuousTorqueUnit IS NOT NULL")
//    Optional<List<String>> findRatedContinuousTorqueUnits();

	@Query("""
			SELECT MIN(m.ratedContinuousTorque), MAX(m.ratedContinuousTorque), m.ratedContinuousTorqueUnit 
			FROM PlanetaryGearboxEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.ratedContinuousTorque IS NOT NULL AND m.ratedContinuousTorqueUnit IS NOT NULL 
			GROUP BY m.ratedContinuousTorqueUnit 
			"""
	)
	List<Object[]> findRatedContinuousTorqueMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<TorqueUnit, Range<BigDecimal>> findRatedContinuousTorqueMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findRatedContinuousTorqueMinMaxByUnits);
	}
	//
	

	//maxMomentaryTorque
//	@Query("SELECT MIN(p.maxMomentaryTorque) FROM PlanetaryGearboxEntity p")
//	Optional<BigDecimal> findMinMaxMomentaryTorque();
//
//	@Query("SELECT MAX(p.maxMomentaryTorque) FROM PlanetaryGearboxEntity p")
//	Optional<BigDecimal> findMaxMaxMomentaryTorque();
//	
//	@Query("SELECT DISTINCT p.maxMomentaryTorqueUnit FROM PlanetaryGearboxEntity p WHERE p.maxMomentaryTorqueUnit IS NOT NULL")
//    Optional<List<String>> findMaxMomentaryTorqueUnits();

	@Query("""
			SELECT MIN(m.maxMomentaryTorque), MAX(m.maxMomentaryTorque), m.maxMomentaryTorqueUnit 
			FROM PlanetaryGearboxEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.maxMomentaryTorque IS NOT NULL AND m.maxMomentaryTorqueUnit IS NOT NULL 
			GROUP BY m.maxMomentaryTorqueUnit 
			"""
	)
	List<Object[]> findMaxMomentaryTorqueMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<TorqueUnit, Range<BigDecimal>> findMaxMomentaryTorqueMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findMaxMomentaryTorqueMinMaxByUnits);
	}
	//
}
