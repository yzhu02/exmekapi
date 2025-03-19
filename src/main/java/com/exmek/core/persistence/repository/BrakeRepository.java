package com.exmek.core.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.LengthUnit;
import com.exmek.core.commons.enums.PowerUnit;
import com.exmek.core.commons.enums.ResistanceUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.commons.enums.VoltageUnit;
import com.exmek.core.commons.enums.WeightUnit;
import com.exmek.core.commons.model.Range;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.BrakeEntity;

public interface BrakeRepository extends BaseProductRepository<BrakeEntity>, JpaRepository<BrakeEntity, Long>, JpaSpecificationExecutor<BrakeEntity> {

	//length
//	@Query("SELECT MIN(b.length) FROM BrakeEntity b")
//    Optional<BigDecimal> findMinLength();
//
//	@Query("SELECT MAX(b.length) FROM BrakeEntity b")
//    Optional<BigDecimal> findMaxLength();
//	
//	@Query("SELECT DISTINCT b.lengthUnit FROM BrakeEntity b WHERE b.lengthUnit IS NOT NULL")
//    Optional<List<String>> findLengthUnits();

	@Query("""
			SELECT MIN(m.length), MAX(m.length), m.lengthUnit 
			FROM BrakeEntity m 
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
//	@Query("SELECT MIN(b.weight) FROM BrakeEntity b")
//    Optional<BigDecimal> findMinWeight();
//
//	@Query("SELECT MAX(b.weight) FROM BrakeEntity b")
//    Optional<BigDecimal> findMaxWeight();
//	
//	@Query("SELECT DISTINCT b.weightUnit FROM BrakeEntity b WHERE b.weightUnit IS NOT NULL")
//    Optional<List<String>> findWeightUnits();

	@Query("""
			SELECT MIN(m.weight), MAX(m.weight), m.weightUnit 
			FROM BrakeEntity m 
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
//	@Query("SELECT MIN(b.frameSize) FROM BrakeEntity b")
//	Optional<BigDecimal> findMinFrameSize();
//
//	@Query("SELECT MAX(b.frameSize) FROM BrakeEntity b")
//	Optional<BigDecimal> findMaxFrameSize();
//	
//	@Query("SELECT DISTINCT b.frameSizeUnit FROM BrakeEntity b WHERE b.frameSizeUnit IS NOT NULL")
//    Optional<List<String>> findFrameSizeUnits();

	@Query("""
			SELECT MIN(m.frameSize), MAX(m.frameSize), m.frameSizeUnit 
			FROM BrakeEntity m 
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
//	@Query("SELECT MIN(b.nemaSize) FROM BrakeEntity b")
//	Optional<BigDecimal> findMinNemaSize();
//
//	@Query("SELECT MAX(b.nemaSize) FROM BrakeEntity b")
//	Optional<BigDecimal> findMaxNemaSize();

	@Query("""
			SELECT MIN(m.nemaSize), MAX(m.nemaSize), '' 
			FROM BrakeEntity m 
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
	
	
	//ratedVoltage
//	@Query("SELECT MIN(b.ratedVoltage) FROM BrakeEntity b")
//	Optional<BigDecimal> findMinRatedVoltage();
//
//	@Query("SELECT MAX(b.ratedVoltage) FROM BrakeEntity b")
//	Optional<BigDecimal> findMaxRatedVoltage();
//	
//	@Query("SELECT DISTINCT b.ratedVoltageUnit FROM BrakeEntity b WHERE b.ratedVoltageUnit IS NOT NULL")
//    Optional<List<String>> findRatedVoltageUnits();

	@Query("""
			SELECT MIN(m.ratedVoltage), MAX(m.ratedVoltage), m.ratedVoltageUnit 
			FROM BrakeEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.ratedVoltage IS NOT NULL AND m.ratedVoltageUnit IS NOT NULL 
			GROUP BY m.ratedVoltageUnit 
			"""
	)
	List<Object[]> findRatedVoltageMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<VoltageUnit, Range<BigDecimal>> findRatedVoltageMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findRatedVoltageMinMaxByUnits);
	}
	//

	
	//resistance
//	@Query("SELECT MIN(b.resistance) FROM BrakeEntity b")
//	Optional<BigDecimal> findMinResistance();
//
//	@Query("SELECT MAX(b.resistance) FROM BrakeEntity b")
//	Optional<BigDecimal> findMaxResistance();
//	
//	@Query("SELECT DISTINCT b.resistanceUnit FROM BrakeEntity b WHERE b.resistanceUnit IS NOT NULL")
//    Optional<List<String>> findResistanceUnits();

	@Query("""
			SELECT MIN(m.resistance), MAX(m.resistance), m.resistanceUnit 
			FROM BrakeEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.resistance IS NOT NULL AND m.resistanceUnit IS NOT NULL 
			GROUP BY m.resistanceUnit 
			"""
	)
	List<Object[]> findResistanceMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<ResistanceUnit, Range<BigDecimal>> findResistanceMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findResistanceMinMaxByUnits);
	}
	//

	
	//current
//	@Query("SELECT MIN(b.current) FROM BrakeEntity b")
//	Optional<BigDecimal> findMinCurrent();
//
//	@Query("SELECT MAX(b.current) FROM BrakeEntity b")
//	Optional<BigDecimal> findMaxCurrent();
//	
//	@Query("SELECT DISTINCT b.currentUnit FROM BrakeEntity b WHERE b.currentUnit IS NOT NULL")
//    Optional<List<String>> findCurrentUnits();

	@Query("""
			SELECT MIN(m.current), MAX(m.current), m.currentUnit 
			FROM BrakeEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.current IS NOT NULL AND m.currentUnit IS NOT NULL 
			GROUP BY m.currentUnit 
			"""
	)
	List<Object[]> findCurrentMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<CurrentUnit, Range<BigDecimal>> findCurrentMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findCurrentMinMaxByUnits);
	}
	//

	
	//staticTorque
//	@Query("SELECT MIN(b.staticTorque) FROM BrakeEntity b")
//	Optional<BigDecimal> findMinStaticTorque();
//
//	@Query("SELECT MAX(b.staticTorque) FROM BrakeEntity b")
//	Optional<BigDecimal> findMaxStaticTorque();
//	
//	@Query("SELECT DISTINCT b.staticTorqueUnit FROM BrakeEntity b WHERE b.staticTorqueUnit IS NOT NULL")
//    Optional<List<String>> findStaticTorqueUnits();

	@Query("""
			SELECT MIN(m.staticTorque), MAX(m.staticTorque), m.staticTorqueUnit 
			FROM BrakeEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.staticTorque IS NOT NULL AND m.staticTorqueUnit IS NOT NULL 
			GROUP BY m.staticTorqueUnit 
			"""
	)
	List<Object[]> findStaticTorqueMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<TorqueUnit, Range<BigDecimal>> findStaticTorqueMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findStaticTorqueMinMaxByUnits);
	}
	//

	
	//ratedPower
//	@Query("SELECT MIN(b.ratedPower) FROM BrakeEntity b")
//	Optional<BigDecimal> findMinRatedPower();
//
//	@Query("SELECT MAX(b.ratedPower) FROM BrakeEntity b")
//	Optional<BigDecimal> findMaxRatedPower();
//	
//	@Query("SELECT DISTINCT b.ratedPowerUnit FROM BrakeEntity b WHERE b.ratedPowerUnit IS NOT NULL")
//    Optional<List<String>> findRatedPowerUnits();

	@Query("""
			SELECT MIN(m.ratedPower), MAX(m.ratedPower), m.ratedPowerUnit 
			FROM BrakeEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.ratedPower IS NOT NULL AND m.ratedPowerUnit IS NOT NULL 
			GROUP BY m.ratedPowerUnit 
			"""
	)
	List<Object[]> findRatedPowerMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<PowerUnit, Range<BigDecimal>> findRatedPowerMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findRatedPowerMinMaxByUnits);
	}
	//


	//startVoltage
//	@Query("SELECT MIN(b.startVoltage) FROM BrakeEntity b")
//	Optional<BigDecimal> findMinStartVoltage();
//
//	@Query("SELECT MAX(b.startVoltage) FROM BrakeEntity b")
//	Optional<BigDecimal> findMaxStartVoltage();
//	
//	@Query("SELECT DISTINCT b.startVoltageUnit FROM BrakeEntity b WHERE b.startVoltageUnit IS NOT NULL")
//    Optional<List<String>> findStartVoltageUnits();

	@Query("""
			SELECT MIN(m.startVoltage), MAX(m.startVoltage), m.startVoltageUnit 
			FROM BrakeEntity m 
			WHERE (1 = :ignoreSeries OR m.series = :series)
				AND m.startVoltage IS NOT NULL AND m.startVoltageUnit IS NOT NULL 
			GROUP BY m.startVoltageUnit 
			"""
	)
	List<Object[]> findStartVoltageMinMaxByUnits( 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<VoltageUnit, Range<BigDecimal>> findStartVoltageMinMaxByUnits(String series) {
		return JPAUtils.findMinMaxByUnits(series, this::findStartVoltageMinMaxByUnits);
	}
	//
}
