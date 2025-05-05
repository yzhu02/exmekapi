package com.exmek.core.persistence.entity;

import java.util.Set;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "DC_MOTOR")
@Access(AccessType.FIELD)
public class DCMotorEntity extends AbstractDCMotorEntity {

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", nullable = false, insertable = false, updatable = false)
    private DCMotorCategoryEntity motorCategory;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERIES", referencedColumnName = "SERIES", nullable = false, insertable = false, updatable = false)
    private DCMotorSeriesEntity productSeries;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "MOTOR_ID")
	private Set<DCMotorSpecEntity> specs;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "MOTOR_ID")
	private Set<DCMotorPerfMeasurementEntity> perfMeasurements;

	@Override
	public DCMotorCategoryEntity getMotorCategory() {
		return motorCategory;
	}

	public void setMotorCategory(DCMotorCategoryEntity motorCategory) {
		this.motorCategory = motorCategory;
	}
	
	public DCMotorSeriesEntity getProductSeries() {
		return productSeries;
	}

	public void setProductSeries(DCMotorSeriesEntity productSeries) {
		this.productSeries = productSeries;
	}

	public Set<DCMotorSpecEntity> getSpecs() {
		return specs;
	}

	public void setSpecs(Set<DCMotorSpecEntity> specs) {
		this.specs = specs;
	}

	public Set<DCMotorPerfMeasurementEntity> getPerfMeasurements() {
		return perfMeasurements;
	}

	public void setPerfMeasurements(Set<DCMotorPerfMeasurementEntity> perfMeasurements) {
		this.perfMeasurements = perfMeasurements;
	}

}
