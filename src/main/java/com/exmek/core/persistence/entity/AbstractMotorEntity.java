package com.exmek.core.persistence.entity;

import java.math.BigDecimal;

import com.exmek.core.annotation.Searchable;
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

}
