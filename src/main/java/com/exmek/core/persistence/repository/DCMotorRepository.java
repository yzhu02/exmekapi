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

	//noloadCurrent
	@Query("""
			SELECT MIN(m.noloadCurrent), MAX(m.noloadCurrent), m.noloadCurrentUnit 
			FROM DCMotorEntity m 
			WHERE m.noloadCurrent IS NOT NULL AND m.noloadCurrentUnit IS NOT NULL
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type))
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.noloadCurrentUnit 
			"""
			)
	List<Object[]> findNoloadCurrentMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);

	default Map<CurrentUnit, Range<BigDecimal>> findNoloadCurrentMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findNoloadCurrentMinMaxByUnits);
	}
	//

	//noloadRotatingSpeed
	@Query("""
			SELECT MIN(m.noloadRotatingSpeed), MAX(m.noloadRotatingSpeed), m.noloadRotatingSpeedUnit 
			FROM DCMotorEntity m 
			WHERE m.noloadRotatingSpeed IS NOT NULL AND m.noloadRotatingSpeedUnit IS NOT NULL
				AND (1 = :ignoreType OR m.category IN (SELECT category FROM DCMotorCategoryEntity WHERE type = :type))
				AND (1 = :ignoreCategory OR m.category = :category) 
				AND (1 = :ignoreSeries OR m.series = :series)
			GROUP BY m.noloadRotatingSpeedUnit 
			"""
			)
	List<Object[]> findNoloadRotatingSpeedMinMaxByUnits(
			@Param("ignoreType") int ignoreType, @Param("type") MotorCategory.Type type,
			@Param("ignoreCategory") int ignoreCategory, @Param("category") String category, 
			@Param("ignoreSeries") int ignoreSeries, @Param("series") String series);

	default Map<RotatingSpeedUnit, Range<BigDecimal>> findNoloadRotatingSpeedMinMaxByUnits(MotorCategory.Type type, String category, String series) {
		return JPAUtils.findMinMaxByUnits(type, category, series, this::findNoloadRotatingSpeedMinMaxByUnits);
	}
	//
}
