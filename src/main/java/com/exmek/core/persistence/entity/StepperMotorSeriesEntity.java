package com.exmek.core.persistence.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "STEPPER_MOTOR_SERIES")
@Access(AccessType.FIELD)
public class StepperMotorSeriesEntity extends AbstractMotorSeriesEntity {

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", nullable = false, insertable = false, updatable = false)
    private StepperMotorCategoryEntity motorCategory;

	public StepperMotorCategoryEntity getMotorCategory() {
		return motorCategory;
	}

	public void setMotorCategory(StepperMotorCategoryEntity motorCategory) {
		this.motorCategory = motorCategory;
	}
	
}
