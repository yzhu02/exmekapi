package com.exmek.core.persistence.projection;

import java.math.BigDecimal;
import java.util.Set;

import com.exmek.core.commons.enums.AngleUnit;
import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.ForceUnit;
import com.exmek.core.commons.enums.InductanceUnit;
import com.exmek.core.commons.enums.LengthUnit;
import com.exmek.core.commons.enums.ResistanceUnit;
import com.exmek.core.commons.enums.SizeMeasurementType;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.commons.enums.VoltageUnit;
import com.exmek.core.commons.enums.WeightUnit;
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

@Deprecated
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

	
	/**
	 * The parameter names must match with the field names in order to be used by LeadFlattenLinearStepperMotorRepository 
	 */
	public LeadFlattenLinearStepperMotorProjection(
			String model,
			BigDecimal length,
			LengthUnit lengthUnit,
			BigDecimal weight,
			WeightUnit weightUnit,
			BigDecimal frameSize,
			LengthUnit frameSizeUnit,
			SizeMeasurementType frameSizeType,
			BigDecimal nemaSize,
			BigDecimal ratedVoltage,
			VoltageUnit ratedVoltageUnit,
			BigDecimal phaseCurrent,
			CurrentUnit phaseCurrentUnit,
			BigDecimal phaseResistance,
			ResistanceUnit phaseResistanceUnit,
			BigDecimal phaseInductance,
			InductanceUnit phaseInductanceUnit,
			BigDecimal holdingTorque,
			TorqueUnit holdingTorqueUnit,
			BigDecimal detentTorque,
			BigDecimal stepAngle,
			AngleUnit stepAngleUnit,
			BigDecimal maxThrust,
			ForceUnit maxThrustUnit,
			
			StepperMotorCategoryEntity motorCategory,
			StepperMotorSeriesEntity productSeries,
			Set<StepperMotorSpecEntity> specs,
			Set<StepperMotorPerfMeasurementEntity> perfMeasurements,
			
			String code,
			BigDecimal screwDiameterInch,
			BigDecimal screwDiameterMM,
			BigDecimal leadInch,
			BigDecimal leadMM,
			Integer threads) {
		
		setModel(model);
		setLength(length);
		setLengthUnit(lengthUnit);
		setWeight(weight);
		setWeightUnit(weightUnit);
		setFrameSize(frameSize);
		setFrameSizeUnit(frameSizeUnit);
		setFrameSizeType(frameSizeType);
		setNemaSize(nemaSize);
		setRatedVoltage(ratedVoltage);
		setRatedVoltageUnit(ratedVoltageUnit);
		setPhaseCurrent(phaseCurrent);
		setPhaseCurrentUnit(phaseCurrentUnit);
		setPhaseResistance(phaseResistance);
		setPhaseResistanceUnit(phaseResistanceUnit);
		setPhaseInductance(phaseInductance);
		setPhaseInductanceUnit(phaseInductanceUnit);
		setHoldingTorque(holdingTorque);
		setHoldingTorqueUnit(holdingTorqueUnit);
		setDetentTorque(detentTorque);
		setStepAngle(stepAngle);
		setStepAngleUnit(stepAngleUnit);
		setMaxThrust(maxThrust);
		setMaxThrustUnit(maxThrustUnit);
		
		
		this.motorCategory = motorCategory;
		this.productSeries = productSeries;
		this.specs = specs;
		this.perfMeasurements = perfMeasurements;
		
		this.code = code;
		this.screwDiameterInch = screwDiameterInch;
		this.screwDiameterMM = screwDiameterMM;
		this.leadInch = leadInch;
		this.leadMM = leadMM;
		this.threads = threads; 
	}
	
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
