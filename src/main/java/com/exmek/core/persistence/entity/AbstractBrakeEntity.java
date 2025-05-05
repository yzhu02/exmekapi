package com.exmek.core.persistence.entity;

import java.math.BigDecimal;

import com.exmek.core.annotation.Searchable;
import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.PowerUnit;
import com.exmek.core.commons.enums.ResistanceUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.commons.enums.VoltageUnit;
import com.exmek.core.persistence.converter.ResistanceUnitConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractBrakeEntity extends AbstractProductEntity {
		
	@Searchable
	@Column(name = "RATED_VOLTAGE")
	private BigDecimal ratedVoltage;
	
	@Column(name = "RATED_VOLTAGE_UNIT")
	@Enumerated(EnumType.STRING)
	private VoltageUnit ratedVoltageUnit;
	
	@Searchable
	@Column(name = "RESISTANCE")
	private BigDecimal resistance;
	
	@Column(name = "RESISTANCE_UNIT")
//	@Enumerated(EnumType.STRING)
	@Convert(converter = ResistanceUnitConverter.class)
	private ResistanceUnit resistanceUnit;
	
	@Searchable
	@Column(name = "CURRENT")
	private BigDecimal current;
	
	@Column(name = "CURRENT_UNIT")
	@Enumerated(EnumType.STRING)
	private CurrentUnit currentUnit;

	@Searchable
	@Column(name = "STATIC_TORQUE")
	private BigDecimal staticTorque;
	
	@Column(name = "STATIC_TORQUE_UNIT")
	@Enumerated(EnumType.STRING)
	private TorqueUnit staticTorqueUnit;
	
	@Searchable
	@Column(name = "RATED_POWER")
	private BigDecimal ratedPower;
	
	@Column(name = "RATED_POWER_UNIT")
	@Enumerated(EnumType.STRING)
	private PowerUnit ratedPowerUnit;
	
	@Searchable
	@Column(name = "START_VOLTAGE")
	private BigDecimal startVoltage;
	
	@Column(name = "START_VOLTAGE_UNIT")
	@Enumerated(EnumType.STRING)
	private VoltageUnit startVoltageUnit;

	public BigDecimal getRatedVoltage() {
		return ratedVoltage;
	}

	public void setRatedVoltage(BigDecimal ratedVoltage) {
		this.ratedVoltage = ratedVoltage;
	}

	public VoltageUnit getRatedVoltageUnit() {
		return ratedVoltageUnit;
	}

	public void setRatedVoltageUnit(VoltageUnit ratedVoltageUnit) {
		this.ratedVoltageUnit = ratedVoltageUnit;
	}

	public BigDecimal getResistance() {
		return resistance;
	}

	public void setResistance(BigDecimal resistance) {
		this.resistance = resistance;
	}

	public ResistanceUnit getResistanceUnit() {
		return resistanceUnit;
	}

	public void setResistanceUnit(ResistanceUnit resistanceUnit) {
		this.resistanceUnit = resistanceUnit;
	}

	public BigDecimal getCurrent() {
		return current;
	}

	public void setCurrent(BigDecimal current) {
		this.current = current;
	}

	public CurrentUnit getCurrentUnit() {
		return currentUnit;
	}

	public void setCurrentUnit(CurrentUnit currentUnit) {
		this.currentUnit = currentUnit;
	}

	public BigDecimal getStaticTorque() {
		return staticTorque;
	}

	public void setStaticTorque(BigDecimal staticTorque) {
		this.staticTorque = staticTorque;
	}

	public TorqueUnit getStaticTorqueUnit() {
		return staticTorqueUnit;
	}

	public void setStaticTorqueUnit(TorqueUnit staticTorqueUnit) {
		this.staticTorqueUnit = staticTorqueUnit;
	}

	public BigDecimal getRatedPower() {
		return ratedPower;
	}

	public void setRatedPower(BigDecimal ratedPower) {
		this.ratedPower = ratedPower;
	}

	public PowerUnit getRatedPowerUnit() {
		return ratedPowerUnit;
	}

	public void setRatedPowerUnit(PowerUnit ratedPowerUnit) {
		this.ratedPowerUnit = ratedPowerUnit;
	}

	public BigDecimal getStartVoltage() {
		return startVoltage;
	}

	public void setStartVoltage(BigDecimal startVoltage) {
		this.startVoltage = startVoltage;
	}

	public VoltageUnit getStartVoltageUnit() {
		return startVoltageUnit;
	}

	public void setStartVoltageUnit(VoltageUnit startVoltageUnit) {
		this.startVoltageUnit = startVoltageUnit;
	}

}
