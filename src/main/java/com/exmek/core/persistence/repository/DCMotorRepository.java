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
import com.exmek.core.commons.enums.LinearSpeedUnit;
import com.exmek.core.commons.enums.PowerUnit;
import com.exmek.core.commons.enums.RotatingSpeedUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.commons.enums.VoltageUnit;
import com.exmek.core.commons.enums.WeightUnit;
import com.exmek.core.commons.model.Range;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.DCMotorEntity;

public interface DCMotorRepository extends BaseProductRepository<DCMotorEntity>, JpaRepository<DCMotorEntity, Long>, JpaSpecificationExecutor<DCMotorEntity> {

	
	//length
//	@Query("SELECT MIN(m.length) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMinLength();
//
//	@Query("SELECT MAX(m.length) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMaxLength();
//
//	@Query("SELECT DISTINCT m.lengthUnit FROM DCMotorEntity m WHERE m.lengthUnit IS NOT NULL")
//    Optional<List<String>> findLengthUnits();
	
	@Query("""
			SELECT MIN(m.length), MAX(m.length), m.lengthUnit 
			FROM DCMotorEntity m 
			WHERE m.length IS NOT NULL AND m.lengthUnit IS NOT NULL
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type))
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series) 
			GROUP BY m.lengthUnit 
			"""
	)
	List<Object[]> findLengthMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<LengthUnit, Range<BigDecimal>> findLengthMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findLengthMinMaxByUnits);
	}
	//

	//weight
//	@Query("SELECT MIN(m.weight) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMinWeight();
//
//	@Query("SELECT MAX(m.weight) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMaxWeight();
//
//	@Query("SELECT DISTINCT m.weightUnit FROM DCMotorEntity m WHERE m.weightUnit IS NOT NULL")
//    Optional<List<String>> findWeightUnits();

	@Query("""
			SELECT MIN(m.weight), MAX(m.weight), m.weightUnit 
			FROM DCMotorEntity m 
			WHERE m.weight IS NOT NULL AND m.weightUnit IS NOT NULL 
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type))
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series) 
			GROUP BY m.weightUnit 
			"""
	)
	List<Object[]> findWeightMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<WeightUnit, Range<BigDecimal>> findWeightMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findWeightMinMaxByUnits);
	}
	//
	

	//frameSize
//	@Query("SELECT MIN(m.frameSize) FROM DCMotorEntity m")
//	Optional<BigDecimal> findMinFrameSize();
//
//	@Query("SELECT MAX(m.frameSize) FROM DCMotorEntity m")
//	Optional<BigDecimal> findMaxFrameSize();
//
//	@Query("SELECT DISTINCT m.frameSizeUnit FROM DCMotorEntity m WHERE m.frameSizeUnit IS NOT NULL")
//    Optional<List<String>> findFrameSizeUnits();

	@Query("""
			SELECT MIN(m.frameSize), MAX(m.frameSize), m.frameSizeUnit 
			FROM DCMotorEntity m 
			WHERE m.frameSize IS NOT NULL AND m.frameSizeUnit IS NOT NULL 
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type))
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series) 
			GROUP BY m.frameSizeUnit 
			"""
	)
	List<Object[]> findFrameSizeMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<LengthUnit, Range<BigDecimal>> findFrameSizeMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findFrameSizeMinMaxByUnits);
	}
	//
	

	//nemaSize
//	@Query("SELECT MIN(m.nemaSize) FROM DCMotorEntity m")
//	Optional<BigDecimal> findMinNemaSize();
//
//	@Query("SELECT MAX(m.nemaSize) FROM DCMotorEntity m")
//	Optional<BigDecimal> findMaxNemaSize();

	@Query("""
			SELECT MIN(m.nemaSize), MAX(m.nemaSize), '' 
			FROM DCMotorEntity m 
			WHERE m.nemaSize IS NOT NULL 
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type))
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series) 
			"""
	)
	List<Object[]> findNemaSizeMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<String, Range<BigDecimal>> findNemaSizeMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findNemaSizeMinMaxByUnits);
	}
	//
	
	
	//ratedVoltage
//	@Query("SELECT MIN(m.ratedVoltage) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMinRatedVoltage();
//
//	@Query("SELECT MAX(m.ratedVoltage) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMaxRatedVoltage();
//	
//	@Query("SELECT DISTINCT m.ratedVoltageUnit FROM DCMotorEntity m WHERE m.ratedVoltageUnit IS NOT NULL")
//    Optional<List<String>> findRatedVoltageUnits();

	@Query("""
			SELECT MIN(m.ratedVoltage), MAX(m.ratedVoltage), m.ratedVoltageUnit 
			FROM DCMotorEntity m 
			WHERE m.ratedVoltage IS NOT NULL AND m.ratedVoltageUnit IS NOT NULL 
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type)) 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series) 
			GROUP BY m.ratedVoltageUnit 
			"""
	)
	List<Object[]> findRatedVoltageMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<VoltageUnit, Range<BigDecimal>> findRatedVoltageMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findRatedVoltageMinMaxByUnits);
	}
	//

	
	//ratedCurrent
//	@Query("SELECT MIN(m.ratedCurrent) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMinRatedCurrent();
//
//	@Query("SELECT MAX(m.ratedCurrent) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMaxRatedCurrent();
//	
//	@Query("SELECT DISTINCT m.ratedCurrentUnit FROM DCMotorEntity m WHERE m.ratedCurrentUnit IS NOT NULL")
//    Optional<List<String>> findRatedCurrentUnits();

	@Query("""
			SELECT MIN(m.ratedCurrent), MAX(m.ratedCurrent), m.ratedCurrentUnit 
			FROM DCMotorEntity m 
			WHERE m.ratedCurrent IS NOT NULL AND m.ratedCurrentUnit IS NOT NULL 
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type))
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series) 
			GROUP BY m.ratedCurrentUnit 
			"""
	)
	List<Object[]> findRatedCurrentMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<CurrentUnit, Range<BigDecimal>> findRatedCurrentMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findRatedCurrentMinMaxByUnits);
	}
	//


	//ratedPower
//	@Query("SELECT MIN(m.ratedPower) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMinRatedPower();
//
//	@Query("SELECT MAX(m.ratedPower) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMaxRatedPower();
//	
//	@Query("SELECT DISTINCT m.ratedPowerUnit FROM DCMotorEntity m WHERE m.ratedPowerUnit IS NOT NULL")
//    Optional<List<String>> findRatedPowerUnits();

	@Query("""
			SELECT MIN(m.ratedPower), MAX(m.ratedPower), m.ratedPowerUnit 
			FROM DCMotorEntity m 
			WHERE m.ratedPower IS NOT NULL AND m.ratedPowerUnit IS NOT NULL 
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type)) 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.ratedPowerUnit 
			"""
	)
	List<Object[]> findRatedPowerMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<PowerUnit, Range<BigDecimal>> findRatedPowerMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findRatedPowerMinMaxByUnits);
	}
	//

	
	//ratedTorque
//	@Query("SELECT MIN(m.ratedTorque) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMinRatedTorque();
//
//	@Query("SELECT MAX(m.ratedTorque) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMaxRatedTorque();
//	
//	@Query("SELECT DISTINCT m.ratedTorqueUnit FROM DCMotorEntity m WHERE m.ratedTorqueUnit IS NOT NULL")
//    Optional<List<String>> findRatedTorqueUnits();
	
	
	@Query("""
			SELECT MIN(m.ratedTorque), MAX(m.ratedTorque), m.ratedTorqueUnit 
			FROM DCMotorEntity m 
			WHERE m.ratedTorque IS NOT NULL AND m.ratedTorqueUnit IS NOT NULL 
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type)) 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series) 
			GROUP BY m.ratedTorqueUnit 
			"""
	)
	List<Object[]> findRatedTorqueMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<TorqueUnit, Range<BigDecimal>> findRatedTorqueMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findRatedTorqueMinMaxByUnits);
	}
	//

	
	//ratedRotatingSpeed
//	@Query("SELECT MIN(m.ratedRotatingSpeed) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMinRatedRotatingSpeed();
//
//	@Query("SELECT MAX(m.ratedRotatingSpeed) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMaxRatedRotatingSpeed();
//	
//	@Query("SELECT DISTINCT m.ratedRotatingSpeedUnit FROM DCMotorEntity m WHERE m.ratedRotatingSpeedUnit IS NOT NULL")
//    Optional<List<String>> findRatedRotatingSpeedUnits();

	@Query("""
			SELECT MIN(m.ratedRotatingSpeed), MAX(m.ratedRotatingSpeed), m.ratedRotatingSpeedUnit 
			FROM DCMotorEntity m 
			WHERE m.ratedRotatingSpeed IS NOT NULL AND m.ratedRotatingSpeedUnit IS NOT NULL 
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type)) 
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.ratedRotatingSpeedUnit 
			"""
	)
	List<Object[]> findRatedRotatingSpeedMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<RotatingSpeedUnit, Range<Integer>> findRatedRotatingSpeedMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findRatedRotatingSpeedMinMaxByUnits);
	}
	//

	
	//ratedLinearSpeed
//	@Query("SELECT MIN(m.ratedLinearSpeed) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMinRatedLinearSpeed();
//
//	@Query("SELECT MAX(m.ratedLinearSpeed) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMaxRatedLinearSpeed();
//	
//	@Query("SELECT DISTINCT m.ratedLinearSpeedUnit FROM DCMotorEntity m WHERE m.ratedLinearSpeedUnit IS NOT NULL")
//    Optional<List<String>> findRatedLinearSpeedUnits();

	@Query("""
			SELECT MIN(m.ratedLinearSpeed), MAX(m.ratedLinearSpeed), m.ratedLinearSpeedUnit 
			FROM DCMotorEntity m 
			WHERE m.ratedLinearSpeed IS NOT NULL AND m.ratedLinearSpeedUnit IS NOT NULL
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type))
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.ratedLinearSpeedUnit 
			"""
	)
	List<Object[]> findRatedLinearSpeedMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<LinearSpeedUnit, Range<BigDecimal>> findRatedLinearSpeedMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findRatedLinearSpeedMinMaxByUnits);
	}
	//

	
	//peakCurrent
//	@Query("SELECT MIN(m.peakCurrent) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMinPeakCurrent();
//
//	@Query("SELECT MAX(m.peakCurrent) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMaxPeakCurrent();
//	
//	@Query("SELECT DISTINCT m.peakCurrentUnit FROM DCMotorEntity m WHERE m.peakCurrentUnit IS NOT NULL")
//    Optional<List<String>> findPeakCurrentUnits();

	@Query("""
			SELECT MIN(m.peakCurrent), MAX(m.peakCurrent), m.peakCurrentUnit 
			FROM DCMotorEntity m 
			WHERE m.peakCurrent IS NOT NULL AND m.peakCurrentUnit IS NOT NULL 
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type))
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.peakCurrentUnit 
			"""
	)
	List<Object[]> findPeakCurrentMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<CurrentUnit, Range<BigDecimal>> findPeakCurrentMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findPeakCurrentMinMaxByUnits);
	}
	//

	
	//peakTorque
//	@Query("SELECT MIN(m.peakTorque) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMinPeakTorque();
//
//	@Query("SELECT MAX(m.peakTorque) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMaxPeakTorque();
//	
//	@Query("SELECT DISTINCT m.peakTorqueUnit FROM DCMotorEntity m WHERE m.peakTorqueUnit IS NOT NULL")
//    Optional<List<String>> findPeakTorqueUnits();

	@Query("""
			SELECT MIN(m.peakTorque), MAX(m.peakTorque), m.peakTorqueUnit 
			FROM DCMotorEntity m 
			WHERE m.peakTorque IS NOT NULL AND m.peakTorqueUnit IS NOT NULL 
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type))
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.peakTorqueUnit 
			"""
	)
	List<Object[]> findPeakTorqueMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<TorqueUnit, Range<BigDecimal>> findPeakTorqueMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findPeakTorqueMinMaxByUnits);
	}
	//

	
	//maxSortingWeight
//	@Query("SELECT MIN(m.maxSortingWeight) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMinMaxSortingWeight();
//
//	@Query("SELECT MAX(m.maxSortingWeight) FROM DCMotorEntity m")
//    Optional<BigDecimal> findMaxMaxSortingWeight();
//	
//	@Query("SELECT DISTINCT m.maxSortingWeightUnit FROM DCMotorEntity m WHERE m.maxSortingWeightUnit IS NOT NULL")
//    Optional<List<String>> findMaxSortingWeightUnits();

	@Query("""
			SELECT MIN(m.maxSortingWeight), MAX(m.maxSortingWeight), m.maxSortingWeightUnit 
			FROM DCMotorEntity m 
			WHERE m.maxSortingWeight IS NOT NULL AND m.maxSortingWeightUnit IS NOT NULL
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type))
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.maxSortingWeightUnit 
			"""
	)
	List<Object[]> findMaxSortingWeightMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);
	
	default Map<WeightUnit, Range<BigDecimal>> findMaxSortingWeightMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findMaxSortingWeightMinMaxByUnits);
	}
	//
}
