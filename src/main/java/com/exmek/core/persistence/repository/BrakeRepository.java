package com.exmek.core.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.exmek.core.persistence.entity.BrakeEntity;

public interface BrakeRepository extends BaseProductRepository<BrakeEntity>, JpaRepository<BrakeEntity, Long>, JpaSpecificationExecutor<BrakeEntity> {

	//length
	@Query("SELECT MIN(b.length) FROM BrakeEntity b")
    Optional<BigDecimal> findMinLength();

	@Query("SELECT MAX(b.length) FROM BrakeEntity b")
    Optional<BigDecimal> findMaxLength();
	
	@Query("SELECT DISTINCT b.lengthUnit FROM BrakeEntity b WHERE b.lengthUnit IS NOT NULL")
    Optional<List<String>> findLengthUnits();
	//


	//weight
	@Query("SELECT MIN(b.weight) FROM BrakeEntity b")
    Optional<BigDecimal> findMinWeight();

	@Query("SELECT MAX(b.weight) FROM BrakeEntity b")
    Optional<BigDecimal> findMaxWeight();
	
	@Query("SELECT DISTINCT b.weightUnit FROM BrakeEntity b WHERE b.weightUnit IS NOT NULL")
    Optional<List<String>> findWeightUnits();
	//

	
	//frameSize
	@Query("SELECT MIN(b.frameSize) FROM BrakeEntity b")
	Optional<BigDecimal> findMinFrameSize();

	@Query("SELECT MAX(b.frameSize) FROM BrakeEntity b")
	Optional<BigDecimal> findMaxFrameSize();
	
	@Query("SELECT DISTINCT b.frameSizeUnit FROM BrakeEntity b WHERE b.frameSizeUnit IS NOT NULL")
    Optional<List<String>> findFrameSizeUnits();
	//

	
	//nemaSize
	@Query("SELECT MIN(b.nemaSize) FROM BrakeEntity b")
	Optional<BigDecimal> findMinNemaSize();

	@Query("SELECT MAX(b.nemaSize) FROM BrakeEntity b")
	Optional<BigDecimal> findMaxNemaSize();
	//
	
	
	//ratedVoltage
	@Query("SELECT MIN(b.ratedVoltage) FROM BrakeEntity b")
	Optional<BigDecimal> findMinRatedVoltage();

	@Query("SELECT MAX(b.ratedVoltage) FROM BrakeEntity b")
	Optional<BigDecimal> findMaxRatedVoltage();
	
	@Query("SELECT DISTINCT b.ratedVoltageUnit FROM BrakeEntity b WHERE b.ratedVoltageUnit IS NOT NULL")
    Optional<List<String>> findRatedVoltageUnits();
	//

	
	//resistance
	@Query("SELECT MIN(b.resistance) FROM BrakeEntity b")
	Optional<BigDecimal> findMinResistance();

	@Query("SELECT MAX(b.resistance) FROM BrakeEntity b")
	Optional<BigDecimal> findMaxResistance();
	
	@Query("SELECT DISTINCT b.resistanceUnit FROM BrakeEntity b WHERE b.resistanceUnit IS NOT NULL")
    Optional<List<String>> findResistanceUnits();
	//

	
	//current
	@Query("SELECT MIN(b.current) FROM BrakeEntity b")
	Optional<BigDecimal> findMinCurrent();

	@Query("SELECT MAX(b.current) FROM BrakeEntity b")
	Optional<BigDecimal> findMaxCurrent();
	
	@Query("SELECT DISTINCT b.currentUnit FROM BrakeEntity b WHERE b.currentUnit IS NOT NULL")
    Optional<List<String>> findCurrentUnits();
	//

	
	//staticTorque
	@Query("SELECT MIN(b.staticTorque) FROM BrakeEntity b")
	Optional<BigDecimal> findMinStaticTorque();

	@Query("SELECT MAX(b.staticTorque) FROM BrakeEntity b")
	Optional<BigDecimal> findMaxStaticTorque();
	
	@Query("SELECT DISTINCT b.staticTorqueUnit FROM BrakeEntity b WHERE b.staticTorqueUnit IS NOT NULL")
    Optional<List<String>> findStaticTorqueUnits();
	//

	
	//ratedPower
	@Query("SELECT MIN(b.ratedPower) FROM BrakeEntity b")
	Optional<BigDecimal> findMinRatedPower();

	@Query("SELECT MAX(b.ratedPower) FROM BrakeEntity b")
	Optional<BigDecimal> findMaxRatedPower();
	
	@Query("SELECT DISTINCT b.ratedPowerUnit FROM BrakeEntity b WHERE b.ratedPowerUnit IS NOT NULL")
    Optional<List<String>> findRatedPowerUnits();
	//

	
	@Query("SELECT MIN(b.startVoltage) FROM BrakeEntity b")
	Optional<BigDecimal> findMinStartVoltage();

	@Query("SELECT MAX(b.startVoltage) FROM BrakeEntity b")
	Optional<BigDecimal> findMaxStartVoltage();
	
	@Query("SELECT DISTINCT b.startVoltageUnit FROM BrakeEntity b WHERE b.startVoltageUnit IS NOT NULL")
    Optional<List<String>> findStartVoltageUnits();
	//
}
