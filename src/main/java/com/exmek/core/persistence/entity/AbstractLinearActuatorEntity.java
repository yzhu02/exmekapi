package com.exmek.core.persistence.entity;

import com.exmek.core.annotation.Searchable;
import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.ForceUnit;
import com.exmek.core.commons.enums.LengthUnit;
import com.exmek.core.commons.enums.LinearSpeedUnit;
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

import java.math.BigDecimal;

@MappedSuperclass
public abstract class AbstractLinearActuatorEntity extends AbstractProductEntity {
		
	@Searchable
	@Column(name = "INPUT_VOLTAGE")
	private BigDecimal inputVoltage;
	
	@Column(name = "INPUT_VOLTAGE_UNIT")
	@Enumerated(EnumType.STRING)
	private VoltageUnit inputVoltageUnit;

  @Searchable
  @Column(name = "MAX_LOAD")
  private BigDecimal maxLoad;

  @Column(name = "MAX_LOAD_UNIT")
  @Enumerated(EnumType.STRING)
  private ForceUnit maxLoadUnit;

  @Searchable
  @Column(name = "MAX_STROKE")
  private BigDecimal maxStroke;

  @Column(name = "MAX_STROKE_UNIT")
  @Enumerated(EnumType.STRING)
  private LengthUnit maxStrokeUnit;

	@Searchable
	@Column(name = "NOLOAD_SPEED")
	private BigDecimal noloadSpeed;
	
	@Column(name = "NOLOAD_SPEED_UNIT")
	@Enumerated(EnumType.STRING)
	private LinearSpeedUnit noloadSpeedUnit;

  @Searchable
  @Column(name = "NOLOAD_CURRENT")
  private BigDecimal noloadCurrent;

  @Column(name = "NOLOAD_CURRENT_UNIT")
  @Enumerated(EnumType.STRING)
  private CurrentUnit noloadCurrentUnit;

  @Searchable
  @Column(name = "FULL_LOAD_SPEED")
  private BigDecimal fullLoadSpeed;

  @Column(name = "FULL_LOAD_SPEED_UNIT")
  @Enumerated(EnumType.STRING)
  private LinearSpeedUnit fullLoadSpeedUnit;

  @Searchable
  @Column(name = "FULL_LOAD_CURRENT")
  private BigDecimal fullLoadCurrent;

  @Column(name = "FULL_LOAD_CURRENT_UNIT")
  @Enumerated(EnumType.STRING)
  private CurrentUnit fullLoadCurrentUnit;

  @Column(name = "INSTALLATION_DISTANCE")
  private String installationDistance;

  @Column(name = "INSTALLATION_DISTANCE_UNIT")
  @Enumerated(EnumType.STRING)
  private LengthUnit installationDistanceUnit;

  public BigDecimal getInputVoltage() {
    return inputVoltage;
  }

  public void setInputVoltage(BigDecimal inputVoltage) {
    this.inputVoltage = inputVoltage;
  }

  public VoltageUnit getInputVoltageUnit() {
    return inputVoltageUnit;
  }

  public void setInputVoltageUnit(VoltageUnit inputVoltageUnit) {
    this.inputVoltageUnit = inputVoltageUnit;
  }

  public BigDecimal getMaxLoad() {
    return maxLoad;
  }

  public void setMaxLoad(BigDecimal maxLoad) {
    this.maxLoad = maxLoad;
  }

  public ForceUnit getMaxLoadUnit() {
    return maxLoadUnit;
  }

  public void setMaxLoadUnit(ForceUnit maxLoadUnit) {
    this.maxLoadUnit = maxLoadUnit;
  }

  public BigDecimal getMaxStroke() {
    return maxStroke;
  }

  public void setMaxStroke(BigDecimal maxStroke) {
    this.maxStroke = maxStroke;
  }

  public LengthUnit getMaxStrokeUnit() {
    return maxStrokeUnit;
  }

  public void setMaxStrokeUnit(LengthUnit maxStrokeUnit) {
    this.maxStrokeUnit = maxStrokeUnit;
  }

  public BigDecimal getNoloadSpeed() {
    return noloadSpeed;
  }

  public void setNoloadSpeed(BigDecimal noloadSpeed) {
    this.noloadSpeed = noloadSpeed;
  }

  public LinearSpeedUnit getNoloadSpeedUnit() {
    return noloadSpeedUnit;
  }

  public void setNoloadSpeedUnit(LinearSpeedUnit noloadSpeedUnit) {
    this.noloadSpeedUnit = noloadSpeedUnit;
  }

  public BigDecimal getNoloadCurrent() {
    return noloadCurrent;
  }

  public void setNoloadCurrent(BigDecimal noloadCurrent) {
    this.noloadCurrent = noloadCurrent;
  }

  public CurrentUnit getNoloadCurrentUnit() {
    return noloadCurrentUnit;
  }

  public void setNoloadCurrentUnit(CurrentUnit noloadCurrentUnit) {
    this.noloadCurrentUnit = noloadCurrentUnit;
  }

  public BigDecimal getFullLoadSpeed() {
    return fullLoadSpeed;
  }

  public void setFullLoadSpeed(BigDecimal fullLoadSpeed) {
    this.fullLoadSpeed = fullLoadSpeed;
  }

  public LinearSpeedUnit getFullLoadSpeedUnit() {
    return fullLoadSpeedUnit;
  }

  public void setFullLoadSpeedUnit(LinearSpeedUnit fullLoadSpeedUnit) {
    this.fullLoadSpeedUnit = fullLoadSpeedUnit;
  }

  public BigDecimal getFullLoadCurrent() {
    return fullLoadCurrent;
  }

  public void setFullLoadCurrent(BigDecimal fullLoadCurrent) {
    this.fullLoadCurrent = fullLoadCurrent;
  }

  public CurrentUnit getFullLoadCurrentUnit() {
    return fullLoadCurrentUnit;
  }

  public void setFullLoadCurrentUnit(CurrentUnit fullLoadCurrentUnit) {
    this.fullLoadCurrentUnit = fullLoadCurrentUnit;
  }

  public String getInstallationDistance() {
    return installationDistance;
  }

  public void setInstallationDistance(String installationDistance) {
    this.installationDistance = installationDistance;
  }

  public LengthUnit getInstallationDistanceUnit() {
    return installationDistanceUnit;
  }

  public void setInstallationDistanceUnit(LengthUnit installationDistanceUnit) {
    this.installationDistanceUnit = installationDistanceUnit;
  }
}
