package com.exmek.core.persistence.entity;

import java.math.BigDecimal;

import com.exmek.core.annotation.Searchable;
import com.exmek.core.commons.enums.ForceUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.persistence.converter.ForceUnitConverter;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "PLANETARY_GEARBOX")
@Access(AccessType.FIELD)
public class PlanetaryGearboxEntity extends AbstractProductEntity {
	
	@Searchable
	@Column(name = "NUM_OF_STAGES")
	private Integer numOfStages;
	
	@Column(name = "REDUCTION_RATIOS")
	private String reductionRatios;

	@Searchable
	@Column(name = "EFFICIENCY")
	private BigDecimal efficiency;

	@Column(name = "EFFICIENCY_UNIT")
	private String efficiencyUnit;
	
	@Searchable
	@Column(name = "RATED_CONTINUOUS_TORQUE")
	private BigDecimal ratedContinuousTorque;
	
	@Column(name = "RATED_CONTINUOUS_TORQUE_UNIT")
	@Enumerated(EnumType.STRING)
	private TorqueUnit ratedContinuousTorqueUnit;
	
	@Searchable
	@Column(name = "MAX_MOMENTARY_TORQUE")
	private BigDecimal maxMomentaryTorque;
	
	@Column(name = "MAX_MOMENTARY_TORQUE_UNIT")
	@Enumerated(EnumType.STRING)
	private TorqueUnit maxMomentaryTorqueUnit;
	
	@Column(name = "MAX_RADIAL_LOAD")
	private BigDecimal maxRadialLoad;
	
	@Column(name = "MAX_RADIAL_LOAD_UNIT")
	@Convert(converter = ForceUnitConverter.class)
	private ForceUnit maxRadialLoadUnit;
	
	@Column(name = "MAX_AXIAL_LOAD")
	private BigDecimal maxAxialLoad;
	
	@Column(name = "MAX_AXIAL_LOAD_UNIT")
	@Convert(converter = ForceUnitConverter.class)
	private ForceUnit maxAxialLoadUnit;
	
	@Column(name = "MAX_SHAFT_PRESS")
	private BigDecimal maxShaftPress;
	
	@Column(name = "MAX_SHAFT_PRESS_UNIT")
	@Convert(converter = ForceUnitConverter.class)
	private ForceUnit maxShaftPressUnit;
	
	@Column(name = "OPERATING_TEMPERATURE")
	private String operatingTemperature;
	
	@Column(name = "RECOMMEND_INPUT_SPEED")
	private String recommendInputSpeed;
	
	public Integer getNumOfStages() {
		return numOfStages;
	}

	public void setNumOfStages(Integer numOfStages) {
		this.numOfStages = numOfStages;
	}

	public String getReductionRatios() {
		return reductionRatios;
	}

	public void setReductionRatios(String reductionRatios) {
		this.reductionRatios = reductionRatios;
	}

	public BigDecimal getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(BigDecimal efficiency) {
		this.efficiency = efficiency;
	}

	public String getEfficiencyUnit() {
		return efficiencyUnit;
	}

	public void setEfficiencyUnit(String efficiencyUnit) {
		this.efficiencyUnit = efficiencyUnit;
	}

	public BigDecimal getRatedContinuousTorque() {
		return ratedContinuousTorque;
	}

	public void setRatedContinuousTorque(BigDecimal ratedContinuousTorque) {
		this.ratedContinuousTorque = ratedContinuousTorque;
	}

	public TorqueUnit getRatedContinuousTorqueUnit() {
		return ratedContinuousTorqueUnit;
	}

	public void setRatedContinuousTorqueUnit(TorqueUnit ratedContinuousTorqueUnit) {
		this.ratedContinuousTorqueUnit = ratedContinuousTorqueUnit;
	}

	public BigDecimal getMaxMomentaryTorque() {
		return maxMomentaryTorque;
	}

	public void setMaxMomentaryTorque(BigDecimal maxMomentaryTorque) {
		this.maxMomentaryTorque = maxMomentaryTorque;
	}

	public TorqueUnit getMaxMomentaryTorqueUnit() {
		return maxMomentaryTorqueUnit;
	}

	public void setMaxMomentaryTorqueUnit(TorqueUnit maxMomentaryTorqueUnit) {
		this.maxMomentaryTorqueUnit = maxMomentaryTorqueUnit;
	}

	public BigDecimal getMaxRadialLoad() {
		return maxRadialLoad;
	}

	public void setMaxRadialLoad(BigDecimal maxRadialLoad) {
		this.maxRadialLoad = maxRadialLoad;
	}

	public ForceUnit getMaxRadialLoadUnit() {
		return maxRadialLoadUnit;
	}

	public void setMaxRadialLoadUnit(ForceUnit maxRadialLoadUnit) {
		this.maxRadialLoadUnit = maxRadialLoadUnit;
	}

	public BigDecimal getMaxAxialLoad() {
		return maxAxialLoad;
	}

	public void setMaxAxialLoad(BigDecimal maxAxialLoad) {
		this.maxAxialLoad = maxAxialLoad;
	}

	public ForceUnit getMaxAxialLoadUnit() {
		return maxAxialLoadUnit;
	}

	public void setMaxAxialLoadUnit(ForceUnit maxAxialLoadUnit) {
		this.maxAxialLoadUnit = maxAxialLoadUnit;
	}

	public BigDecimal getMaxShaftPress() {
		return maxShaftPress;
	}

	public void setMaxShaftPress(BigDecimal maxShaftPress) {
		this.maxShaftPress = maxShaftPress;
	}

	public ForceUnit getMaxShaftPressUnit() {
		return maxShaftPressUnit;
	}

	public void setMaxShaftPressUnit(ForceUnit maxShaftPressUnit) {
		this.maxShaftPressUnit = maxShaftPressUnit;
	}

	public String getOperatingTemperature() {
		return operatingTemperature;
	}

	public void setOperatingTemperature(String operatingTemperature) {
		this.operatingTemperature = operatingTemperature;
	}

	public String getRecommendInputSpeed() {
		return recommendInputSpeed;
	}

	public void setRecommendInputSpeed(String recommendInputSpeed) {
		this.recommendInputSpeed = recommendInputSpeed;
	}

}
