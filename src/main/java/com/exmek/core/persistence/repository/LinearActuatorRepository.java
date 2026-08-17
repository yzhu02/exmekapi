package com.exmek.core.persistence.repository;

import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.ForceUnit;
import com.exmek.core.commons.enums.LengthUnit;
import com.exmek.core.commons.enums.LinearSpeedUnit;
import com.exmek.core.commons.enums.VoltageUnit;
import com.exmek.core.commons.enums.WeightUnit;
import com.exmek.core.commons.model.Range;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.LinearActuatorEntity;
import com.exmek.core.persistence.projection.TimestampOfSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LinearActuatorRepository extends BaseNonCategoryRepository<LinearActuatorEntity>, JpaRepository<LinearActuatorEntity, Long>, JpaSpecificationExecutor<LinearActuatorEntity> {

  // TODO Currently linear actuator doesn't have series
  @Override
  default List<TimestampOfSeries> findLastCreatedPerSeries() {
    throw new UnsupportedOperationException("Linear actuator doesn't have series");
  }

  // TODO Currently linear actuator doesn't have series
  @Override
  default Date findLastCreatedBySeries(@Param("series") String series) {
    throw new UnsupportedOperationException("Linear actuator doesn't have series");
  }

	//length
	@Query("""
			SELECT MIN(e.length), MAX(e.length), e.lengthUnit 
			FROM LinearActuatorEntity e 
			WHERE e.length IS NOT NULL AND e.lengthUnit IS NOT NULL 
			GROUP BY e.lengthUnit 
			"""
	)
	List<Object[]> _findLengthMinMaxByUnits();
	
	default Map<LengthUnit, Range<BigDecimal>> findLengthMinMaxByUnits() {
		return JPAUtils.findMinMaxByUnits(this::_findLengthMinMaxByUnits);
	}
	//

	//weight
	@Query("""
			SELECT MIN(e.weight), MAX(e.weight), e.weightUnit 
			FROM LinearActuatorEntity e 
			WHERE e.weight IS NOT NULL AND e.weightUnit IS NOT NULL 
			GROUP BY e.weightUnit 
			"""
	)
	List<Object[]> _findWeightMinMaxByUnits();
	
	default Map<WeightUnit, Range<BigDecimal>> findWeightMinMaxByUnits() {
		return JPAUtils.findMinMaxByUnits(this::_findWeightMinMaxByUnits);
	}
	//

	//frameSize
	@Query("""
			SELECT MIN(e.frameSize), MAX(e.frameSize), e.frameSizeUnit 
			FROM LinearActuatorEntity e 
			WHERE e.frameSize IS NOT NULL AND e.frameSizeUnit IS NOT NULL 
			GROUP BY e.frameSizeUnit 
			"""
	)
	List<Object[]> _findFrameSizeMinMaxByUnits();
	
	default Map<LengthUnit, Range<BigDecimal>> findFrameSizeMinMaxByUnits() {
		return JPAUtils.findMinMaxByUnits(this::_findFrameSizeMinMaxByUnits);
	}
	//

	//nemaSize
	@Query("""
			SELECT MIN(e.nemaSize), MAX(e.nemaSize), '' 
			FROM LinearActuatorEntity e 
			WHERE e.nemaSize IS NOT NULL
			"""
	)
	List<Object[]> _findNemaSizeMinMaxByUnits();
	
	default Map<String, Range<BigDecimal>> findNemaSizeMinMaxByUnits() {
		return JPAUtils.findMinMaxByUnits(this::_findNemaSizeMinMaxByUnits);
	}
	//
	
	//inputVoltage
	@Query("""
			SELECT MIN(e.inputVoltage), MAX(e.inputVoltage), e.inputVoltageUnit 
			FROM LinearActuatorEntity e 
			WHERE e.inputVoltage IS NOT NULL AND e.inputVoltageUnit IS NOT NULL 
			GROUP BY e.inputVoltageUnit 
			"""
	)
	List<Object[]> _findInputVoltageMinMaxByUnits();
	
	default Map<VoltageUnit, Range<BigDecimal>> findInputVoltageMinMaxByUnits() {
		return JPAUtils.findMinMaxByUnits(this::_findInputVoltageMinMaxByUnits);
	}
	//

  //maxLoad
  @Query("""
			SELECT MIN(e.maxLoad), MAX(e.maxLoad), e.maxLoadUnit 
			FROM LinearActuatorEntity e 
			WHERE e.maxLoad IS NOT NULL AND e.maxLoadUnit IS NOT NULL 
			GROUP BY e.maxLoadUnit 
			"""
  )
  List<Object[]> _findMaxLoadMinMaxByUnits();

  default Map<ForceUnit, Range<BigDecimal>> findMaxLoadMinMaxByUnits() {
    return JPAUtils.findMinMaxByUnits(this::_findMaxLoadMinMaxByUnits);
  }
  //

  //maxLoad
  @Query("""
			SELECT MIN(e.maxStroke), MAX(e.maxStroke), e.maxStrokeUnit 
			FROM LinearActuatorEntity e 
			WHERE e.maxStroke IS NOT NULL AND e.maxStrokeUnit IS NOT NULL 
			GROUP BY e.maxStrokeUnit 
			"""
  )
  List<Object[]> _findMaxStrokeMinMaxByUnits();

  default Map<LengthUnit, Range<BigDecimal>> findMaxStrokeMinMaxByUnits() {
    return JPAUtils.findMinMaxByUnits(this::_findMaxStrokeMinMaxByUnits);
  }
  //

  //noloadSpeed
  @Query("""
			SELECT MIN(e.noloadSpeed), MAX(e.noloadSpeed), e.noloadSpeedUnit 
			FROM LinearActuatorEntity e 
			WHERE e.noloadSpeed IS NOT NULL AND e.noloadSpeedUnit IS NOT NULL 
			GROUP BY e.noloadSpeedUnit 
			"""
  )
  List<Object[]> _findNoloadSpeedMinMaxByUnits();

  default Map<LinearSpeedUnit, Range<BigDecimal>> findNoloadSpeedMinMaxByUnits() {
    return JPAUtils.findMinMaxByUnits(this::_findNoloadSpeedMinMaxByUnits);
  }
  //

  //noloadCurrent
  @Query("""
			SELECT MIN(e.noloadCurrent), MAX(e.noloadCurrent), e.noloadCurrentUnit 
			FROM LinearActuatorEntity e 
			WHERE e.noloadCurrent IS NOT NULL AND e.noloadCurrentUnit IS NOT NULL 
			GROUP BY e.noloadCurrentUnit 
			"""
  )
  List<Object[]> _findNoloadCurrentMinMaxByUnits();

  default Map<CurrentUnit, Range<BigDecimal>> findNoloadCurrentMinMaxByUnits() {
    return JPAUtils.findMinMaxByUnits(this::_findNoloadCurrentMinMaxByUnits);
  }
  //

  //fullLoadSpeed
  @Query("""
			SELECT MIN(e.fullLoadSpeed), MAX(e.fullLoadSpeed), e.fullLoadSpeedUnit 
			FROM LinearActuatorEntity e 
			WHERE e.fullLoadSpeed IS NOT NULL AND e.fullLoadSpeedUnit IS NOT NULL 
			GROUP BY e.fullLoadSpeedUnit 
			"""
  )
  List<Object[]> _findFullLoadSpeedMinMaxByUnits();

  default Map<LinearSpeedUnit, Range<BigDecimal>> findFullLoadSpeedMinMaxByUnits() {
    return JPAUtils.findMinMaxByUnits(this::_findFullLoadSpeedMinMaxByUnits);
  }
  //

  //fullLoadCurrent
  @Query("""
			SELECT MIN(e.fullLoadCurrent), MAX(e.fullLoadCurrent), e.fullLoadCurrentUnit 
			FROM LinearActuatorEntity e 
			WHERE e.fullLoadCurrent IS NOT NULL AND e.fullLoadCurrentUnit IS NOT NULL 
			GROUP BY e.fullLoadCurrentUnit 
			"""
  )
  List<Object[]> _findFullLoadCurrentMinMaxByUnits();

  default Map<CurrentUnit, Range<BigDecimal>> findFullLoadCurrentMinMaxByUnits() {
    return JPAUtils.findMinMaxByUnits(this::_findFullLoadCurrentMinMaxByUnits);
  }
  //
}
