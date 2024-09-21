package com.exmek.core.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.exmek.core.persistence.entity.StepperMotorEntity;

public interface StepperMotorRepository extends BaseProductRepository<StepperMotorEntity>, JpaRepository<StepperMotorEntity, Long>, JpaSpecificationExecutor<StepperMotorEntity> {

	//length
	@Query("SELECT MIN(m.length) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMinLength();

	@Query("SELECT MAX(m.length) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMaxLength();
	
	@Query("SELECT DISTINCT m.lengthUnit FROM StepperMotorEntity m WHERE m.lengthUnit IS NOT NULL")
    Optional<List<String>> findLengthUnits();
	//


	//weight
	@Query("SELECT MIN(m.weight) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMinWeight();

	@Query("SELECT MAX(m.weight) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMaxWeight();
	
	@Query("SELECT DISTINCT m.weightUnit FROM StepperMotorEntity m WHERE m.weightUnit IS NOT NULL")
    Optional<List<String>> findWeightUnits();
	//

	
	//frameSize
	@Query("SELECT MIN(m.frameSize) FROM StepperMotorEntity m")
	Optional<BigDecimal> findMinFrameSize();

	@Query("SELECT MAX(m.frameSize) FROM StepperMotorEntity m")
	Optional<BigDecimal> findMaxFrameSize();
	
	@Query("SELECT DISTINCT m.frameSizeUnit FROM StepperMotorEntity m WHERE m.frameSizeUnit IS NOT NULL")
    Optional<List<String>> findFrameSizeUnits();
	//

	
	//nemaSize
	@Query("SELECT MIN(m.nemaSize) FROM StepperMotorEntity m")
	Optional<BigDecimal> findMinNemaSize();

	@Query("SELECT MAX(m.nemaSize) FROM StepperMotorEntity m")
	Optional<BigDecimal> findMaxNemaSize();
	//

	
	
	//ratedVoltage
	@Query("SELECT MIN(m.ratedVoltage) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMinRatedVoltage();

	@Query("SELECT MAX(m.ratedVoltage) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMaxRatedVoltage();
	
	@Query("SELECT DISTINCT m.ratedVoltageUnit FROM StepperMotorEntity m WHERE m.ratedVoltageUnit IS NOT NULL")
    Optional<List<String>> findRatedVoltageUnits();
	//

	
	//phaseCurrent
	@Query("SELECT MIN(m.phaseCurrent) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMinPhaseCurrent();

	@Query("SELECT MAX(m.phaseCurrent) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMaxPhaseCurrent();
	
	@Query("SELECT DISTINCT m.phaseCurrentUnit FROM StepperMotorEntity m WHERE m.phaseCurrentUnit IS NOT NULL")
    Optional<List<String>> findPhaseCurrentUnits();
	//

	
	//phaseResistance
	@Query("SELECT MIN(m.phaseResistance) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMinPhaseResistance();

	@Query("SELECT MAX(m.phaseResistance) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMaxPhaseResistance();
	
	@Query("SELECT DISTINCT m.phaseResistanceUnit FROM StepperMotorEntity m WHERE m.phaseResistanceUnit IS NOT NULL")
    Optional<List<String>> findPhaseResistanceUnits();
	//

	
	//holdingTorque
	@Query("SELECT MIN(m.holdingTorque) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMinHoldingTorque();

	@Query("SELECT MAX(m.holdingTorque) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMaxHoldingTorque();
	
	@Query("SELECT DISTINCT m.holdingTorqueUnit FROM StepperMotorEntity m WHERE m.holdingTorqueUnit IS NOT NULL")
    Optional<List<String>> findHoldingTorqueUnits();
	//

	
	//detentTorque
	@Query("SELECT MIN(m.detentTorque) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMinDetentTorque();

	@Query("SELECT MAX(m.detentTorque) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMaxDetentTorque();
	
	@Query("SELECT DISTINCT m.detentTorqueUnit FROM StepperMotorEntity m WHERE m.detentTorqueUnit IS NOT NULL")
    Optional<List<String>> findDetentTorqueUnits();
	//

	
	//stepAngle
	@Query("SELECT MIN(m.stepAngle) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMinStepAngle();

	@Query("SELECT MAX(m.stepAngle) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMaxStepAngle();
	
	@Query("SELECT DISTINCT m.stepAngleUnit FROM StepperMotorEntity m WHERE m.stepAngleUnit IS NOT NULL")
    Optional<List<String>> findStepAngleUnits();
	//

	
	//maxThrust
	@Query("SELECT MIN(m.maxThrust) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMinMaxThrust();

	@Query("SELECT MAX(m.maxThrust) FROM StepperMotorEntity m")
    Optional<BigDecimal> findMaxMaxThrust();
	
	@Query("SELECT DISTINCT m.maxThrustUnit FROM StepperMotorEntity m WHERE m.maxThrustUnit IS NOT NULL")
    Optional<List<String>> findMaxThrustUnits();
	//
}
