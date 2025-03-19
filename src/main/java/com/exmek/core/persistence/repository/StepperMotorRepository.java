package com.exmek.core.persistence.repository;

import java.math.BigDecimal;
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

public interface StepperMotorRepository extends BaseProductRepository<StepperMotorEntity>, JpaRepository<StepperMotorEntity, Long>, JpaSpecificationExecutor<StepperMotorEntity> {

	//length
//	@Query("SELECT MIN(m.length) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMinLength();
//
//	@Query("SELECT MAX(m.length) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMaxLength();
//	
//	@Query("SELECT DISTINCT m.lengthUnit FROM StepperMotorEntity m WHERE m.lengthUnit IS NOT NULL")
//    Optional<List<String>> findLengthUnits();
	
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
//	@Query("SELECT MIN(m.weight) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMinWeight();
//
//	@Query("SELECT MAX(m.weight) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMaxWeight();
//	
//	@Query("SELECT DISTINCT m.weightUnit FROM StepperMotorEntity m WHERE m.weightUnit IS NOT NULL")
//    Optional<List<String>> findWeightUnits();

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
//	@Query("SELECT MIN(m.frameSize) FROM StepperMotorEntity m")
//	Optional<BigDecimal> findMinFrameSize();
//
//	@Query("SELECT MAX(m.frameSize) FROM StepperMotorEntity m")
//	Optional<BigDecimal> findMaxFrameSize();
//	
//	@Query("SELECT DISTINCT m.frameSizeUnit FROM StepperMotorEntity m WHERE m.frameSizeUnit IS NOT NULL")
//    Optional<List<String>> findFrameSizeUnits();

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
//	@Query("SELECT MIN(m.nemaSize) FROM StepperMotorEntity m")
//	Optional<BigDecimal> findMinNemaSize();
//
//	@Query("SELECT MAX(m.nemaSize) FROM StepperMotorEntity m")
//	Optional<BigDecimal> findMaxNemaSize();

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
//	@Query("SELECT MIN(m.ratedVoltage) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMinRatedVoltage();
//
//	@Query("SELECT MAX(m.ratedVoltage) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMaxRatedVoltage();
//	
//	@Query("SELECT DISTINCT m.ratedVoltageUnit FROM StepperMotorEntity m WHERE m.ratedVoltageUnit IS NOT NULL")
//    Optional<List<String>> findRatedVoltageUnits();

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
//	@Query("SELECT MIN(m.phaseCurrent) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMinPhaseCurrent();
//
//	@Query("SELECT MAX(m.phaseCurrent) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMaxPhaseCurrent();
//	
//	@Query("SELECT DISTINCT m.phaseCurrentUnit FROM StepperMotorEntity m WHERE m.phaseCurrentUnit IS NOT NULL")
//    Optional<List<String>> findPhaseCurrentUnits();

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
//	@Query("SELECT MIN(m.phaseResistance) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMinPhaseResistance();
//
//	@Query("SELECT MAX(m.phaseResistance) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMaxPhaseResistance();
//	
//	@Query("SELECT DISTINCT m.phaseResistanceUnit FROM StepperMotorEntity m WHERE m.phaseResistanceUnit IS NOT NULL")
//    Optional<List<String>> findPhaseResistanceUnits();

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
//	@Query("SELECT MIN(m.holdingTorque) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMinHoldingTorque();
//
//	@Query("SELECT MAX(m.holdingTorque) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMaxHoldingTorque();
//	
//	@Query("SELECT DISTINCT m.holdingTorqueUnit FROM StepperMotorEntity m WHERE m.holdingTorqueUnit IS NOT NULL")
//    Optional<List<String>> findHoldingTorqueUnits();

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
//	@Query("SELECT MIN(m.detentTorque) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMinDetentTorque();
//
//	@Query("SELECT MAX(m.detentTorque) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMaxDetentTorque();
//	
//	@Query("SELECT DISTINCT m.detentTorqueUnit FROM StepperMotorEntity m WHERE m.detentTorqueUnit IS NOT NULL")
//    Optional<List<String>> findDetentTorqueUnits();
	
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
//	@Query("SELECT MIN(m.stepAngle) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMinStepAngle();
//
//	@Query("SELECT MAX(m.stepAngle) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMaxStepAngle();
//	
//	@Query("SELECT DISTINCT m.stepAngleUnit FROM StepperMotorEntity m WHERE m.stepAngleUnit IS NOT NULL")
//    Optional<List<String>> findStepAngleUnits();

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
//	@Query("SELECT MIN(m.maxThrust) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMinMaxThrust();
//
//	@Query("SELECT MAX(m.maxThrust) FROM StepperMotorEntity m")
//    Optional<BigDecimal> findMaxMaxThrust();
//	
//	@Query("SELECT DISTINCT m.maxThrustUnit FROM StepperMotorEntity m WHERE m.maxThrustUnit IS NOT NULL")
//    Optional<List<String>> findMaxThrustUnits();

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
