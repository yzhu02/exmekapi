package com.exmek.core.persistence.entity;

import java.math.BigDecimal;

import com.exmek.core.annotation.Searchable;
import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.LinearSpeedUnit;
import com.exmek.core.commons.enums.PowerUnit;
import com.exmek.core.commons.enums.RotatingSpeedUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.commons.enums.WeightUnit;
import com.exmek.core.persistence.converter.LinearSpeedUnitConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractDCMotorEntity extends AbstractMotorEntity {
	
	@Searchable
	@Column(name = "RATED_CURRENT")
	private BigDecimal ratedCurrent;
	
	@Column(name = "RATED_CURRENT_UNIT")
	@Enumerated(EnumType.STRING)
	private CurrentUnit ratedCurrentUnit;

	@Searchable
	@Column(name = "RATED_POWER")
	private BigDecimal ratedPower;
	
	@Column(name = "RATED_POWER_UNIT")
	@Enumerated(EnumType.STRING)
	private PowerUnit ratedPowerUnit;

	@Searchable
	@Column(name = "RATED_TORQUE")
	private BigDecimal ratedTorque;
	
	@Column(name = "RATED_TORQUE_UNIT")
	@Enumerated(EnumType.STRING)
	private TorqueUnit ratedTorqueUnit;
	
	@Searchable
	@Column(name = "RATED_ROTATING_SPEED")
	private Integer ratedRotatingSpeed;
	
	@Column(name = "RATED_ROTATING_SPEED_UNIT")
	@Enumerated(EnumType.STRING)
	private RotatingSpeedUnit ratedRotatingSpeedUnit;

	@Searchable
	@Column(name = "RATED_LINEAR_SPEED")
	private BigDecimal ratedLinearSpeed;
		
	@Column(name = "RATED_LINEAR_SPEED_UNIT")
//	@Enumerated(EnumType.STRING)
	@Convert(converter = LinearSpeedUnitConverter.class)
	private LinearSpeedUnit ratedLinearSpeedUnit;

	@Searchable
	@Column(name = "PEAK_CURRENT")
	private BigDecimal peakCurrent;
	
	@Column(name = "PEAK_CURRENT_UNIT")
	@Enumerated(EnumType.STRING)
	private CurrentUnit peakCurrentUnit;
	
	@Searchable
	@Column(name = "PEAK_TORQUE")
	private BigDecimal peakTorque;
	
	@Column(name = "PEAK_TORQUE_UNIT")
	@Enumerated(EnumType.STRING)
	private TorqueUnit peakTorqueUnit;

	@Searchable
	@Column(name = "MAX_SORTING_WEIGHT")
	private BigDecimal maxSortingWeight;
	
	@Column(name = "MAX_SORTING_WEIGHT_UNIT")
	@Enumerated(EnumType.STRING)
	private WeightUnit maxSortingWeightUnit;

	@Column(name = "NOLOAD_CURRENT")
	private BigDecimal noloadCurrent;
	
	@Column(name = "NOLOAD_CURRENT_UNIT")
	@Enumerated(EnumType.STRING)
	private CurrentUnit noloadCurrentUnit;

	@Column(name = "NOLOAD_ROTATING_SPEED")
	private Integer noloadRotatingSpeed;
	
	@Column(name = "NOLOAD_ROTATING_SPEED_UNIT")
	@Enumerated(EnumType.STRING)
	private RotatingSpeedUnit noloadRotatingSpeedUnit;

	public BigDecimal getRatedCurrent() {
		return ratedCurrent;
	}

	public void setRatedCurrent(BigDecimal ratedCurrent) {
		this.ratedCurrent = ratedCurrent;
	}

	public CurrentUnit getRatedCurrentUnit() {
		return ratedCurrentUnit;
	}

	public void setRatedCurrentUnit(CurrentUnit ratedCurrentUnit) {
		this.ratedCurrentUnit = ratedCurrentUnit;
	}

	public BigDecimal getRatedPower() {
		return ratedPower;
	}

	public void setRatedPower(BigDecimal ratedPower) {
		this.ratedPower = ratedPower;
	}

	public PowerUnit getRatedPowerUnit() {
		return ratedPowerUnit;
	}

	public void setRatedPowerUnit(PowerUnit ratedPowerUnit) {
		this.ratedPowerUnit = ratedPowerUnit;
	}

	public BigDecimal getRatedTorque() {
		return ratedTorque;
	}

	public void setRatedTorque(BigDecimal ratedTorque) {
		this.ratedTorque = ratedTorque;
	}

	public TorqueUnit getRatedTorqueUnit() {
		return ratedTorqueUnit;
	}

	public void setRatedTorqueUnit(TorqueUnit ratedTorqueUnit) {
		this.ratedTorqueUnit = ratedTorqueUnit;
	}

	public Integer getRatedRotatingSpeed() {
		return ratedRotatingSpeed;
	}

	public void setRatedRotatingSpeed(Integer ratedRotatingSpeed) {
		this.ratedRotatingSpeed = ratedRotatingSpeed;
	}

	public RotatingSpeedUnit getRatedRotatingSpeedUnit() {
		return ratedRotatingSpeedUnit;
	}

	public void setRatedRotatingSpeedUnit(RotatingSpeedUnit ratedRotatingSpeedUnit) {
		this.ratedRotatingSpeedUnit = ratedRotatingSpeedUnit;
	}

	public BigDecimal getRatedLinearSpeed() {
		return ratedLinearSpeed;
	}

	public void setRatedLinearSpeed(BigDecimal ratedLinearSpeed) {
		this.ratedLinearSpeed = ratedLinearSpeed;
	}

	public LinearSpeedUnit getRatedLinearSpeedUnit() {
		return ratedLinearSpeedUnit;
	}

	public void setRatedLinearSpeedUnit(LinearSpeedUnit ratedLinearSpeedUnit) {
		this.ratedLinearSpeedUnit = ratedLinearSpeedUnit;
	}

	public BigDecimal getPeakCurrent() {
		return peakCurrent;
	}

	public void setPeakCurrent(BigDecimal peakCurrent) {
		this.peakCurrent = peakCurrent;
	}

	public CurrentUnit getPeakCurrentUnit() {
		return peakCurrentUnit;
	}

	public void setPeakCurrentUnit(CurrentUnit peakCurrentUnit) {
		this.peakCurrentUnit = peakCurrentUnit;
	}

	public BigDecimal getPeakTorque() {
		return peakTorque;
	}

	public void setPeakTorque(BigDecimal peakTorque) {
		this.peakTorque = peakTorque;
	}

	public TorqueUnit getPeakTorqueUnit() {
		return peakTorqueUnit;
	}

	public void setPeakTorqueUnit(TorqueUnit peakTorqueUnit) {
		this.peakTorqueUnit = peakTorqueUnit;
	}

	public BigDecimal getMaxSortingWeight() {
		return maxSortingWeight;
	}

	public void setMaxSortingWeight(BigDecimal maxSortingWeight) {
		this.maxSortingWeight = maxSortingWeight;
	}

	public WeightUnit getMaxSortingWeightUnit() {
		return maxSortingWeightUnit;
	}

	public void setMaxSortingWeightUnit(WeightUnit maxSortingWeightUnit) {
		this.maxSortingWeightUnit = maxSortingWeightUnit;
	}

	public BigDecimal getNoloadCurrent() {
		return noloadCurrent;
	}

	public void setNoloadCurrent(BigDecimal noloadCurrent) {
		this.noloadCurrent = noloadCurrent;
	}

	public CurrentUnit getNoloadCurrentUnit() {
		return noloadCurrentUnit;
	}

	public void setNoloadCurrentUnit(CurrentUnit noloadCurrentUnit) {
		this.noloadCurrentUnit = noloadCurrentUnit;
	}

	public Integer getNoloadRotatingSpeed() {
		return noloadRotatingSpeed;
	}

	public void setNoloadRotatingSpeed(Integer noloadRotatingSpeed) {
		this.noloadRotatingSpeed = noloadRotatingSpeed;
	}

	public RotatingSpeedUnit getNoloadRotatingSpeedUnit() {
		return noloadRotatingSpeedUnit;
	}

	public void setNoloadRotatingSpeedUnit(RotatingSpeedUnit noloadRotatingSpeedUnit) {
		this.noloadRotatingSpeedUnit = noloadRotatingSpeedUnit;
	}
}
