package com.exmek.core.persistence.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exmek.core.commons.enums.AngleUnit;
import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.ForceUnit;
import com.exmek.core.commons.enums.LengthUnit;
import com.exmek.core.commons.enums.ResistanceUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.commons.enums.VoltageUnit;
import com.exmek.core.commons.enums.WeightUnit;
import com.exmek.core.commons.model.Range;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.StepperMotorEntity;
import com.exmek.core.persistence.projection.LastUpdatedTimestampPerSeries;

public interface StepperMotorRepository extends BaseMotorRepository<StepperMotorEntity>, JpaRepository<StepperMotorEntity, Long>, JpaSpecificationExecutor<StepperMotorEntity> {

	@Override
	@Query(value = """
			SELECT SERIES, 
			       MAX(COALESCE(UPDATED_TIMESTAMP, CREATED_TIMESTAMP)) AS lastUpdated
			FROM STEPPER_MOTOR
			WHERE CATEGORY = :category
			GROUP BY SERIES
			ORDER BY lastUpdated DESC
			""",
			nativeQuery = true)
	List<LastUpdatedTimestampPerSeries> findLastUpdatedPerSeriesByCategory(@Param("category") String category);

	@Override
	@Query(value = """
			SELECT MAX(COALESCE(UPDATED_TIMESTAMP, CREATED_TIMESTAMP)) AS lastUpdated
			FROM STEPPER_MOTOR
			WHERE SERIES = :series
			""",
			nativeQuery = true)
	Date findLastUpdatedBySeries(@Param("series") String series);

	//length	
	@Query("""
			SELECT MIN(m.length), MAX(m.length), m.lengthUnit 
			FROM StepperMotorEntity m 
			WHERE m.length IS NOT NULL AND m.lengthUnit IS NOT NULL
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.lengthUnit 
			"""
	)
	List<Object[]> findLengthMinMaxByUnits(
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<LengthUnit, Range<BigDecimal>> findLengthMinMaxByUnits(String category, String series) {
		return JPAUtils.findMinMaxByUnits(category, series, this::findLengthMinMaxByUnits);
	}
	//

	//weight
	@Query("""
			SELECT MIN(m.weight), MAX(m.weight), m.weightUnit 
			FROM StepperMotorEntity m 
			WHERE m.weight IS NOT NULL AND m.weightUnit IS NOT NULL 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.weightUnit 
			"""
	)
	List<Object[]> findWeightMinMaxByUnits(
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<WeightUnit, Range<BigDecimal>> findWeightMinMaxByUnits(String category, String series) {
		return JPAUtils.findMinMaxByUnits(category, series, this::findWeightMinMaxByUnits);
	}
	//

	
	//frameSize
	@Query("""
			SELECT MIN(m.frameSize), MAX(m.frameSize), m.frameSizeUnit 
			FROM StepperMotorEntity m 
			WHERE m.frameSize IS NOT NULL AND m.frameSizeUnit IS NOT NULL 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.frameSizeUnit 
			"""
	)
	List<Object[]> findFrameSizeMinMaxByUnits(
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<LengthUnit, Range<BigDecimal>> findFrameSizeMinMaxByUnits(String category, String series) {
		return JPAUtils.findMinMaxByUnits(category, series, this::findFrameSizeMinMaxByUnits);
	}
	//

	//nemaSize
	@Query("""
			SELECT MIN(m.nemaSize), MAX(m.nemaSize), '' 
			FROM StepperMotorEntity m 
			WHERE m.nemaSize IS NOT NULL
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series) 
			"""
	)
	List<Object[]> findNemaSizeMinMaxByUnits(
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<String, Range<BigDecimal>> findNemaSizeMinMaxByUnits(String category, String series) {
		return JPAUtils.findMinMaxByUnits(category, series, this::findNemaSizeMinMaxByUnits);
	}
	//
	
	//ratedVoltage
	@Query("""
			SELECT MIN(m.ratedVoltage), MAX(m.ratedVoltage), m.ratedVoltageUnit 
			FROM StepperMotorEntity m 
			WHERE m.ratedVoltage IS NOT NULL AND m.ratedVoltageUnit IS NOT NULL 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.ratedVoltageUnit 
			"""
	)
	List<Object[]> findRatedVoltageMinMaxByUnits(
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<VoltageUnit, Range<BigDecimal>> findRatedVoltageMinMaxByUnits(String category, String series) {
		return JPAUtils.findMinMaxByUnits(category, series, this::findRatedVoltageMinMaxByUnits);
	}
	//

	//phaseCurrent
	@Query("""
			SELECT MIN(m.phaseCurrent), MAX(m.phaseCurrent), m.phaseCurrentUnit 
			FROM StepperMotorEntity m 
			WHERE m.phaseCurrent IS NOT NULL AND m.phaseCurrentUnit IS NOT NULL 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.phaseCurrentUnit 
			"""
	)
	List<Object[]> findPhaseCurrentMinMaxByUnits(
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<CurrentUnit, Range<BigDecimal>> findPhaseCurrentMinMaxByUnits(String category, String series) {
		return JPAUtils.findMinMaxByUnits(category, series, this::findPhaseCurrentMinMaxByUnits);
	}
	//

	//phaseResistance
	@Query("""
			SELECT MIN(m.phaseResistance), MAX(m.phaseResistance), m.phaseResistanceUnit 
			FROM StepperMotorEntity m 
			WHERE m.phaseResistance IS NOT NULL AND m.phaseResistanceUnit IS NOT NULL 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.phaseResistanceUnit 
			"""
	)
	List<Object[]> findPhaseResistanceMinMaxByUnits(
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<ResistanceUnit, Range<BigDecimal>> findPhaseResistanceMinMaxByUnits(String category, String series) {
		return JPAUtils.findMinMaxByUnits(category, series, this::findPhaseResistanceMinMaxByUnits);
	}
	//

	//holdingTorque
	@Query("""
			SELECT MIN(m.holdingTorque), MAX(m.holdingTorque), m.holdingTorqueUnit 
			FROM StepperMotorEntity m 
			WHERE m.holdingTorque IS NOT NULL AND m.holdingTorqueUnit IS NOT NULL 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.holdingTorqueUnit 
			"""
	)
	List<Object[]> findHoldingTorqueMinMaxByUnits(
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<TorqueUnit, Range<BigDecimal>> findHoldingTorqueMinMaxByUnits(String category, String series) {
		return JPAUtils.findMinMaxByUnits(category, series, this::findHoldingTorqueMinMaxByUnits);
	}
	//

	//detentTorque
	@Query("""
			SELECT MIN(m.detentTorque), MAX(m.detentTorque), m.detentTorqueUnit 
			FROM StepperMotorEntity m 
			WHERE m.detentTorque IS NOT NULL AND m.detentTorqueUnit IS NOT NULL 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.detentTorqueUnit 
			"""
	)
	List<Object[]> findDetentTorqueMinMaxByUnits(
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<TorqueUnit, Range<BigDecimal>> findDetentTorqueMinMaxByUnits(String category, String series) {
		return JPAUtils.findMinMaxByUnits(category, series, this::findDetentTorqueMinMaxByUnits);
	}
	//

	//stepAngle
	@Query("""
			SELECT MIN(m.stepAngle), MAX(m.stepAngle), m.stepAngleUnit 
			FROM StepperMotorEntity m 
			WHERE m.stepAngle IS NOT NULL AND m.stepAngleUnit IS NOT NULL 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.stepAngleUnit 
			"""
	)
	List<Object[]> findStepAngleMinMaxByUnits(
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<AngleUnit, Range<BigDecimal>> findStepAngleMinMaxByUnits(String category, String series) {
		return JPAUtils.findMinMaxByUnits(category, series, this::findStepAngleMinMaxByUnits);
	}
	//

	//maxThrust
	@Query("""
			SELECT MIN(m.maxThrust), MAX(m.maxThrust), m.maxThrustUnit 
			FROM StepperMotorEntity m 
			WHERE m.maxThrust IS NOT NULL AND m.maxThrustUnit IS NOT NULL 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.maxThrustUnit 
			"""
	)
	List<Object[]> findMaxThrustMinMaxByUnits(
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<ForceUnit, Range<BigDecimal>> findMaxThrustMinMaxByUnits(String category, String series) {
		return JPAUtils.findMinMaxByUnits(category, series, this::findMaxThrustMinMaxByUnits);
	}
	//
}
