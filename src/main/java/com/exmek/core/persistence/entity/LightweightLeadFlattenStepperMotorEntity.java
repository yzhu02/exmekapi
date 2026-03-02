package com.exmek.core.persistence.entity;

import java.util.Set;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * The entity of Stepper Motor, 
 * and in case of linear stepper motor then the model is represented with lead code flatten, 
 * i.e. a liner stepper motor with a lead code is considered as individual motor model.
 */
@Entity
@Table(name = "STEPPER_MOTOR")
@Access(AccessType.FIELD)
public class LightweightLeadFlattenStepperMotorEntity extends AbstractStepperMotorEntity {

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "LINEAR_STEPPER_MOTOR_LEAD", 
			joinColumns = @JoinColumn(name = "MOTOR_ID"), 
			inverseJoinColumns = @JoinColumn(name = "LEAD_ID"))
	private Set<LeadDefEntity> linearStepperMotorLeads;

	@Override
	public AbstractMotorCategoryEntity getMotorCategory() {
		StepperMotorCategoryEntity entity = new StepperMotorCategoryEntity();
		entity.setCategory(getCategory());
		return entity;
	}

	public Set<LeadDefEntity> getLinearStepperMotorLeads() {
		return linearStepperMotorLeads;
	}

	public void setLinearStepperMotorLeads(Set<LeadDefEntity> linearStepperMotorLeads) {
		this.linearStepperMotorLeads = linearStepperMotorLeads;
	}
}
