package com.exmek.core.persistence.entity;

import java.math.BigDecimal;

import com.exmek.core.annotation.Searchable;
import com.exmek.core.commons.enums.VoltageUnit;
import com.exmek.core.model.MotorCategory;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractMotorEntity extends AbstractProductEntity {

	public static final String FIELD_NAME_CATEGORY	= MotorCategoryEntity.FIELD_NAME_CATEGORY;

	@Column(name = "CATEGORY")
	@Enumerated(EnumType.STRING)
	private MotorCategory.Category category;

	@Searchable
	@Column(name = "RATED_VOLTAGE")
	private BigDecimal ratedVoltage;

	@Column(name = "RATED_VOLTAGE_UNIT")
	@Enumerated(EnumType.STRING)
	private VoltageUnit ratedVoltageUnit;

	public MotorCategory.Category getCategory() {
		return category;
	}

	public void setCategory(MotorCategory.Category category) {
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
