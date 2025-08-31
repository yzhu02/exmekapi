package com.exmek.core.persistence.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DC_MOTOR")
@Access(AccessType.FIELD)
public class LightweightDCMotorEntity extends AbstractDCMotorEntity {

	//TODO: This is required only for searchMotorsByCategoryType() to have DC_MOTOR join with DC_MOTOR_CATEGORY to query by DC_MOTOR_CATEGORY.TYPE
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", nullable = false, insertable = false, updatable = false)
    private DCMotorCategoryEntity motorCategory;

	@Override
	public DCMotorCategoryEntity getMotorCategory() {
		return motorCategory;
	}
}
