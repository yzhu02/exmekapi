package com.exmek.core.persistence.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "STEPPER_MOTOR")
@Access(AccessType.FIELD)
public class LightweightStepperMotorEntity extends AbstractStepperMotorEntity {

	@Override
	public AbstractMotorCategoryEntity getMotorCategory() {
		DCMotorCategoryEntity entity = new DCMotorCategoryEntity();
		entity.setCategory(getCategory());
		return entity;
	}
}
