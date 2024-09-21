package com.exmek.core.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.exmek.core.persistence.entity.DCMotorEntity;

public interface DCMotorRepository extends BaseProductRepository<DCMotorEntity>, JpaRepository<DCMotorEntity, Long>, JpaSpecificationExecutor<DCMotorEntity> {

	
	//length
	@Query("SELECT MIN(m.length) FROM DCMotorEntity m")
    Optional<BigDecimal> findMinLength();

	@Query("SELECT MAX(m.length) FROM DCMotorEntity m")
    Optional<BigDecimal> findMaxLength();

	@Query("SELECT DISTINCT m.lengthUnit FROM DCMotorEntity m WHERE m.lengthUnit IS NOT NULL")
    Optional<List<String>> findLengthUnits();
	//


	//weight
	@Query("SELECT MIN(m.weight) FROM DCMotorEntity m")
    Optional<BigDecimal> findMinWeight();

	@Query("SELECT MAX(m.weight) FROM DCMotorEntity m")
    Optional<BigDecimal> findMaxWeight();

	@Query("SELECT DISTINCT m.weightUnit FROM DCMotorEntity m WHERE m.weightUnit IS NOT NULL")
    Optional<List<String>> findWeightUnits();
	//
	

	//frameSize
	@Query("SELECT MIN(m.frameSize) FROM DCMotorEntity m")
	Optional<BigDecimal> findMinFrameSize();

	@Query("SELECT MAX(m.frameSize) FROM DCMotorEntity m")
	Optional<BigDecimal> findMaxFrameSize();

	@Query("SELECT DISTINCT m.frameSizeUnit FROM DCMotorEntity m WHERE m.frameSizeUnit IS NOT NULL")
    Optional<List<String>> findFrameSizeUnits();
	//
	

	//nemaSize
	@Query("SELECT MIN(m.nemaSize) FROM DCMotorEntity m")
	Optional<BigDecimal> findMinNemaSize();

	@Query("SELECT MAX(m.nemaSize) FROM DCMotorEntity m")
	Optional<BigDecimal> findMaxNemaSize();
	//

	
	
	//ratedVoltage
	@Query("SELECT MIN(m.ratedVoltage) FROM DCMotorEntity m")
    Optional<BigDecimal> findMinRatedVoltage();

	@Query("SELECT MAX(m.ratedVoltage) FROM DCMotorEntity m")
    Optional<BigDecimal> findMaxRatedVoltage();
	
	@Query("SELECT DISTINCT m.ratedVoltageUnit FROM DCMotorEntity m WHERE m.ratedVoltageUnit IS NOT NULL")
    Optional<List<String>> findRatedVoltageUnits();
	//

	
	//ratedCurrent
	@Query("SELECT MIN(m.ratedCurrent) FROM DCMotorEntity m")
    Optional<BigDecimal> findMinRatedCurrent();

	@Query("SELECT MAX(m.ratedCurrent) FROM DCMotorEntity m")
    Optional<BigDecimal> findMaxRatedCurrent();
	
	@Query("SELECT DISTINCT m.ratedCurrentUnit FROM DCMotorEntity m WHERE m.ratedCurrentUnit IS NOT NULL")
    Optional<List<String>> findRatedCurrentUnits();
	//


	//ratedPower
	@Query("SELECT MIN(m.ratedPower) FROM DCMotorEntity m")
    Optional<BigDecimal> findMinRatedPower();

	@Query("SELECT MAX(m.ratedPower) FROM DCMotorEntity m")
    Optional<BigDecimal> findMaxRatedPower();
	
	@Query("SELECT DISTINCT m.ratedPowerUnit FROM DCMotorEntity m WHERE m.ratedPowerUnit IS NOT NULL")
    Optional<List<String>> findRatedPowerUnits();
	//

	
	//ratedTorque
	@Query("SELECT MIN(m.ratedTorque) FROM DCMotorEntity m")
    Optional<BigDecimal> findMinRatedTorque();

	@Query("SELECT MAX(m.ratedTorque) FROM DCMotorEntity m")
    Optional<BigDecimal> findMaxRatedTorque();
	
	@Query("SELECT DISTINCT m.ratedTorqueUnit FROM DCMotorEntity m WHERE m.ratedTorqueUnit IS NOT NULL")
    Optional<List<String>> findRatedTorqueUnits();
	//

	
	//ratedRotatingSpeed
	@Query("SELECT MIN(m.ratedRotatingSpeed) FROM DCMotorEntity m")
    Optional<BigDecimal> findMinRatedRotatingSpeed();

	@Query("SELECT MAX(m.ratedRotatingSpeed) FROM DCMotorEntity m")
    Optional<BigDecimal> findMaxRatedRotatingSpeed();
	
	@Query("SELECT DISTINCT m.ratedRotatingSpeedUnit FROM DCMotorEntity m WHERE m.ratedRotatingSpeedUnit IS NOT NULL")
    Optional<List<String>> findRatedRotatingSpeedUnits();
	//

	
	//ratedLinearSpeed
	@Query("SELECT MIN(m.ratedLinearSpeed) FROM DCMotorEntity m")
    Optional<BigDecimal> findMinRatedLinearSpeed();

	@Query("SELECT MAX(m.ratedLinearSpeed) FROM DCMotorEntity m")
    Optional<BigDecimal> findMaxRatedLinearSpeed();
	
	@Query("SELECT DISTINCT m.ratedLinearSpeedUnit FROM DCMotorEntity m WHERE m.ratedLinearSpeedUnit IS NOT NULL")
    Optional<List<String>> findRatedLinearSpeedUnits();
	//

	
	//peakCurrent
	@Query("SELECT MIN(m.peakCurrent) FROM DCMotorEntity m")
    Optional<BigDecimal> findMinPeakCurrent();

	@Query("SELECT MAX(m.peakCurrent) FROM DCMotorEntity m")
    Optional<BigDecimal> findMaxPeakCurrent();
	
	@Query("SELECT DISTINCT m.peakCurrentUnit FROM DCMotorEntity m WHERE m.peakCurrentUnit IS NOT NULL")
    Optional<List<String>> findPeakCurrentUnits();
	//

	
	//peakTorque
	@Query("SELECT MIN(m.peakTorque) FROM DCMotorEntity m")
    Optional<BigDecimal> findMinPeakTorque();

	@Query("SELECT MAX(m.peakTorque) FROM DCMotorEntity m")
    Optional<BigDecimal> findMaxPeakTorque();
	
	@Query("SELECT DISTINCT m.peakTorqueUnit FROM DCMotorEntity m WHERE m.peakTorqueUnit IS NOT NULL")
    Optional<List<String>> findPeakTorqueUnits();
	//

	
	//maxSortingWeight
	@Query("SELECT MIN(m.maxSortingWeight) FROM DCMotorEntity m")
    Optional<BigDecimal> findMinMaxSortingWeight();

	@Query("SELECT MAX(m.maxSortingWeight) FROM DCMotorEntity m")
    Optional<BigDecimal> findMaxMaxSortingWeight();
	
	@Query("SELECT DISTINCT m.maxSortingWeightUnit FROM DCMotorEntity m WHERE m.maxSortingWeightUnit IS NOT NULL")
    Optional<List<String>> findMaxSortingWeightUnits();
	//
}
