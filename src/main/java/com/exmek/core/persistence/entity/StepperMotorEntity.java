package com.exmek.core.persistence.entity;

import java.util.Set;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "STEPPER_MOTOR")
@Access(AccessType.FIELD)
public class StepperMotorEntity extends AbstractStepperMotorEntity {
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", nullable = false, insertable = false, updatable = false)
    private StepperMotorCategoryEntity motorCategory;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERIES", referencedColumnName = "SERIES", nullable = false, insertable = false, updatable = false)
    private StepperMotorSeriesEntity productSeries;
		
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "MOTOR_ID")
	private Set<StepperMotorSpecEntity> specs;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "MOTOR_ID")
	private Set<StepperMotorPerfMeasurementEntity> perfMeasurements;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "LINEAR_STEPPER_MOTOR_LEAD", 
			joinColumns = @JoinColumn(name = "MOTOR_ID"), 
			inverseJoinColumns = @JoinColumn(name = "LEAD_ID"))
	private Set<LeadDefEntity> linearStepperMotorLeads;

	@Override
	public StepperMotorCategoryEntity getMotorCategory() {
		return motorCategory;
	}

	public void setMotorCategory(StepperMotorCategoryEntity motorCategory) {
		this.motorCategory = motorCategory;
	}

	public StepperMotorSeriesEntity getProductSeries() {
		return productSeries;
	}

	public void setProductSeries(StepperMotorSeriesEntity productSeries) {
		this.productSeries = productSeries;
	}

	public Set<StepperMotorSpecEntity> getSpecs() {
		return specs;
	}

	public void setSpecs(Set<StepperMotorSpecEntity> specs) {
		this.specs = specs;
	}

	public Set<StepperMotorPerfMeasurementEntity> getPerfMeasurements() {
		return perfMeasurements;
	}

	public void setPerfMeasurements(Set<StepperMotorPerfMeasurementEntity> perfMeasurements) {
		this.perfMeasurements = perfMeasurements;
	}

	public Set<LeadDefEntity> getLinearStepperMotorLeads() {
		return linearStepperMotorLeads;
	}

	public void setLinearStepperMotorLeads(Set<LeadDefEntity> linearStepperMotorLeads) {
		this.linearStepperMotorLeads = linearStepperMotorLeads;
	}

}
