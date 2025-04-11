package com.exmek.core.persistence.entity;

import java.math.BigDecimal;
import java.util.Set;

import com.exmek.core.annotation.Searchable;
import com.exmek.core.commons.enums.AngleUnit;
import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.ForceUnit;
import com.exmek.core.commons.enums.InductanceUnit;
import com.exmek.core.commons.enums.ResistanceUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.persistence.converter.AngleUnitConverter;
import com.exmek.core.persistence.converter.ResistanceUnitConverter;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class StepperMotorEntity extends AbstractMotorEntity {

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", nullable = false, insertable = false, updatable = false)
    private StepperMotorCategoryEntity motorCategory;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SERIES", referencedColumnName = "SERIES", nullable = false, insertable = false, updatable = false)
    private StepperMotorSeriesEntity productSeries;
	
	@Searchable
	@Column(name = "PHASE_CURRENT")
	private BigDecimal phaseCurrent;
	
	@Column(name = "PHASE_CURRENT_UNIT")
	@Enumerated(EnumType.STRING)
	private CurrentUnit phaseCurrentUnit;

	@Searchable
	@Column(name = "PHASE_RESISTANCE")
	private BigDecimal phaseResistance;
	
	@Column(name = "PHASE_RESISTANCE_UNIT")
//	@Enumerated(EnumType.STRING)
	@Convert(converter = ResistanceUnitConverter.class)
	private ResistanceUnit phaseResistanceUnit;

	@Column(name = "PHASE_INDUCTANCE")
	private BigDecimal phaseInductance;
	
	@Column(name = "PHASE_INDUCTANCE_UNIT")
	@Enumerated(EnumType.STRING)
	private InductanceUnit phaseInductanceUnit;
	
	@Searchable
	@Column(name = "HOLDING_TORQUE")
	private BigDecimal holdingTorque;
	
	@Column(name = "HOLDING_TORQUE_UNIT")
	@Enumerated(EnumType.STRING)
	private TorqueUnit holdingTorqueUnit;

	@Searchable
	@Column(name = "DETENT_TORQUE")
	private BigDecimal detentTorque;
	
	@Column(name = "DETENT_TORQUE_UNIT")
	@Enumerated(EnumType.STRING)
	private TorqueUnit detentTorqueUnit;
	
	@Searchable
	@Column(name = "STEP_ANGLE")
	private BigDecimal stepAngle;
	
	@Column(name = "STEP_ANGLE_UNIT")
//	@Enumerated(EnumType.STRING)
	@Convert(converter = AngleUnitConverter.class)
	private AngleUnit stepAngleUnit;
	
	@Searchable
	@Column(name = "MAX_THRUST")
	private BigDecimal maxThrust;
	
	@Column(name = "MAX_THRUST_UNIT")
	@Enumerated(EnumType.STRING)
	private ForceUnit maxThrustUnit;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "MOTOR_ID")
	private Set<StepperMotorSpecEntity> specs;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "MOTOR_ID")
	private Set<StepperMotorPerfMeasurementEntity> perfMeasurements;

	@ManyToMany
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

	public BigDecimal getPhaseCurrent() {
		return phaseCurrent;
	}

	public void setPhaseCurrent(BigDecimal phaseCurrent) {
		this.phaseCurrent = phaseCurrent;
	}

	public CurrentUnit getPhaseCurrentUnit() {
		return phaseCurrentUnit;
	}

	public void setPhaseCurrentUnit(CurrentUnit phaseCurrentUnit) {
		this.phaseCurrentUnit = phaseCurrentUnit;
	}

	public BigDecimal getPhaseResistance() {
		return phaseResistance;
	}

	public void setPhaseResistance(BigDecimal phaseResistance) {
		this.phaseResistance = phaseResistance;
	}

	public ResistanceUnit getPhaseResistanceUnit() {
		return phaseResistanceUnit;
	}

	public void setPhaseResistanceUnit(ResistanceUnit phaseResistanceUnit) {
		this.phaseResistanceUnit = phaseResistanceUnit;
	}

	public BigDecimal getPhaseInductance() {
		return phaseInductance;
	}

	public void setPhaseInductance(BigDecimal phaseInductance) {
		this.phaseInductance = phaseInductance;
	}

	public InductanceUnit getPhaseInductanceUnit() {
		return phaseInductanceUnit;
	}

	public void setPhaseInductanceUnit(InductanceUnit phaseInductanceUnit) {
		this.phaseInductanceUnit = phaseInductanceUnit;
	}

	public BigDecimal getHoldingTorque() {
		return holdingTorque;
	}

	public void setHoldingTorque(BigDecimal holdingTorque) {
		this.holdingTorque = holdingTorque;
	}

	public TorqueUnit getHoldingTorqueUnit() {
		return holdingTorqueUnit;
	}

	public void setHoldingTorqueUnit(TorqueUnit holdingTorqueUnit) {
		this.holdingTorqueUnit = holdingTorqueUnit;
	}

	public BigDecimal getDetentTorque() {
		return detentTorque;
	}

	public void setDetentTorque(BigDecimal detentTorque) {
		this.detentTorque = detentTorque;
	}

	public TorqueUnit getDetentTorqueUnit() {
		return detentTorqueUnit;
	}

	public void setDetentTorqueUnit(TorqueUnit detentTorqueUnit) {
		this.detentTorqueUnit = detentTorqueUnit;
	}

	public BigDecimal getStepAngle() {
		return stepAngle;
	}

	public void setStepAngle(BigDecimal stepAngle) {
		this.stepAngle = stepAngle;
	}

	public AngleUnit getStepAngleUnit() {
		return stepAngleUnit;
	}

	public void setStepAngleUnit(AngleUnit stepAngleUnit) {
		this.stepAngleUnit = stepAngleUnit;
	}

	public BigDecimal getMaxThrust() {
		return maxThrust;
	}

	public void setMaxThrust(BigDecimal maxThrust) {
		this.maxThrust = maxThrust;
	}

	public ForceUnit getMaxThrustUnit() {
		return maxThrustUnit;
	}

	public void setMaxThrustUnit(ForceUnit maxThrustUnit) {
		this.maxThrustUnit = maxThrustUnit;
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
