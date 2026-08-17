package com.exmek.core.persistence.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exmek.core.commons.enums.ForceUnit;
import com.exmek.core.commons.enums.LengthUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.commons.enums.WeightUnit;
import com.exmek.core.commons.model.Range;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.PlanetaryGearboxEntity;
import com.exmek.core.persistence.projection.TimestampOfSeries;

public interface PlanetaryGearboxRepository extends BaseNonCategoryRepository<PlanetaryGearboxEntity>, JpaRepository<PlanetaryGearboxEntity, Long>, JpaSpecificationExecutor<PlanetaryGearboxEntity> {

	@Override
	@Query(value = """
			SELECT SERIES, MAX(CREATED_TIMESTAMP) AS timestamp
			FROM PLANETARY_GEARBOX
			GROUP BY SERIES
			ORDER BY timestamp DESC
			""",
			nativeQuery = true)
	List<TimestampOfSeries> findLastCreatedPerSeries();

	@Override
	@Query(value = """
			SELECT MAX(CREATED_TIMESTAMP) AS timestamp
			FROM PLANETARY_GEARBOX
			WHERE SERIES = :series
			""",
			nativeQuery = true)
	Date findLastCreatedBySeries(@Param("series") String series);

	//length
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
	
	default Map<LengthUnit, Range<BigDecimal>> findLengthMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findLengthMinMaxByUnits);
	}
	//

	//weight
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

	//maxRadialLoad
	@Query("""
			SELECT MIN(m.maxRadialLoad), MAX(m.maxRadialLoad), m.maxRadialLoadUnit 
			FROM PlanetaryGearboxEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.maxRadialLoad IS NOT NULL AND m.maxRadialLoadUnit IS NOT NULL 
			GROUP BY m.maxRadialLoadUnit 
			"""
			)
	List<Object[]> findMaxRadialLoadMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);

	default Map<ForceUnit, Range<BigDecimal>> findMaxRadialLoadMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findMaxRadialLoadMinMaxByUnits);
	}
	//

	//maxAxialLoad
	@Query("""
			SELECT MIN(m.maxAxialLoad), MAX(m.maxAxialLoad), m.maxAxialLoadUnit 
			FROM PlanetaryGearboxEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.maxAxialLoad IS NOT NULL AND m.maxAxialLoadUnit IS NOT NULL 
			GROUP BY m.maxAxialLoadUnit 
			"""
			)
	List<Object[]> findMaxAxialLoadMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);

	default Map<ForceUnit, Range<BigDecimal>> findMaxAxialLoadMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findMaxAxialLoadMinMaxByUnits);
	}
	//

	//maxShaftPress
	@Query("""
			SELECT MIN(m.maxShaftPress), MAX(m.maxShaftPress), m.maxShaftPressUnit 
			FROM PlanetaryGearboxEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.maxShaftPress IS NOT NULL AND m.maxShaftPressUnit IS NOT NULL 
			GROUP BY m.maxShaftPressUnit 
			"""
			)
	List<Object[]> findMaxShaftPressMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);

	default Map<ForceUnit, Range<BigDecimal>> findMaxShaftPressMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findMaxShaftPressMinMaxByUnits);
	}
	//
}
