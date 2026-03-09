package com.exmek.core.persistence.entity;

import java.math.BigDecimal;

import com.exmek.core.annotation.Searchable;
import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.VoltageUnit;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractMotorEntity extends AbstractProductEntity {

	public static final String FIELD_NAME_CATEGORY			= AbstractMotorCategoryEntity.FIELD_NAME_CATEGORY;
	
	public static final String FIELD_NAME_MOTOR_CATEGORY	= "motorCategory";
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Searchable
	@Column(name = "RATED_VOLTAGE")
	private BigDecimal ratedVoltage;

	@Column(name = "RATED_VOLTAGE_UNIT")
	@Enumerated(EnumType.STRING)
	private VoltageUnit ratedVoltageUnit;

	@Searchable
	@Column(name = "RATED_CURRENT")
	private BigDecimal ratedCurrent;
	
	@Column(name = "RATED_CURRENT_UNIT")
	@Enumerated(EnumType.STRING)
	private CurrentUnit ratedCurrentUnit;

	@Searchable
	@Column(name = "PEAK_CURRENT")
	private BigDecimal peakCurrent;
	
	@Column(name = "PEAK_CURRENT_UNIT")
	@Enumerated(EnumType.STRING)
	private CurrentUnit peakCurrentUnit;

	public abstract AbstractMotorCategoryEntity getMotorCategory();

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getRatedVoltage() {
		return ratedVoltage;
	}

	public void setRatedVoltage(BigDecimal ratedVoltage) {
		this.ratedVoltage = ratedVoltage;
	}

	public VoltageUnit getRatedVoltageUnit() {
		return ratedVoltageUnit;
	}

	public void setRatedVoltageUnit(VoltageUnit ratedVoltageUnit) {
		this.ratedVoltageUnit = ratedVoltageUnit;
	}

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
}
