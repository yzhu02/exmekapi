package com.exmek.core.persistence.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DC_MOTOR_SERIES")
@Access(AccessType.FIELD)
public class DCMotorSeriesEntity extends AbstractMotorSeriesEntity {

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", nullable = false, insertable = false, updatable = false)
    private DCMotorCategoryEntity motorCategory;

	public DCMotorCategoryEntity getMotorCategory() {
		return motorCategory;
	}

	public void setMotorCategory(DCMotorCategoryEntity motorCategory) {
		this.motorCategory = motorCategory;
	}

}
