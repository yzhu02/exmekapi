package com.exmek.core.persistence.projection;

import java.math.BigDecimal;
import java.util.Set;

import com.exmek.core.persistence.entity.AbstractStepperMotorEntity;
import com.exmek.core.persistence.entity.StepperMotorCategoryEntity;
import com.exmek.core.persistence.entity.StepperMotorPerfMeasurementEntity;
import com.exmek.core.persistence.entity.StepperMotorSeriesEntity;
import com.exmek.core.persistence.entity.StepperMotorSpecEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class LeadFlattenLinearStepperMotorProjection extends AbstractStepperMotorEntity {

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
	
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "SCREW_DIAMETER_INCH")
	private BigDecimal screwDiameterInch;
		
	@Column(name = "SCREW_DIAMETER_MM")
	private BigDecimal screwDiameterMM;

	@Column(name = "LEAD_INCH")
	private BigDecimal leadInch;
		
	@Column(name = "LEAD_MM")
	private BigDecimal leadMM;
	
	@Column(name = "THREADS")
	private Integer threads;

	
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
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getScrewDiameterInch() {
		return screwDiameterInch;
	}

	public void setScrewDiameterInch(BigDecimal screwDiameterInch) {
		this.screwDiameterInch = screwDiameterInch;
	}

	public BigDecimal getScrewDiameterMM() {
		return screwDiameterMM;
	}

	public void setScrewDiameterMM(BigDecimal screwDiameterMM) {
		this.screwDiameterMM = screwDiameterMM;
	}

	public BigDecimal getLeadInch() {
		return leadInch;
	}

	public void setLeadInch(BigDecimal leadInch) {
		this.leadInch = leadInch;
	}

	public BigDecimal getLeadMM() {
		return leadMM;
	}

	public void setLeadMM(BigDecimal leadMM) {
		this.leadMM = leadMM;
	}

	public Integer getThreads() {
		return threads;
	}

	public void setThreads(Integer threads) {
		this.threads = threads;
	}
}
