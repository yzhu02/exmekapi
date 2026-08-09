package com.exmek.core.model;

import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.ForceUnit;
import com.exmek.core.commons.enums.LengthUnit;
import com.exmek.core.commons.enums.LinearSpeedUnit;
import com.exmek.core.commons.enums.VoltageUnit;
import com.exmek.core.commons.model.MeasuredRawValue;
import com.exmek.core.commons.model.MeasuredValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class LinearActuator extends AbstractProduct {
	
	private MeasuredValue<BigDecimal, VoltageUnit> inputVoltage;
	
	private MeasuredValue<BigDecimal, ForceUnit> maxLoad;

  private MeasuredValue<BigDecimal, LengthUnit> maxStroke;

  private MeasuredValue<BigDecimal, LinearSpeedUnit> noloadSpeed;

	private MeasuredValue<BigDecimal, CurrentUnit> noloadCurrent;

  private MeasuredValue<BigDecimal, LinearSpeedUnit> fullLoadSpeed;

  private MeasuredValue<BigDecimal, CurrentUnit> fullLoadCurrent;

  private MeasuredRawValue<String, LengthUnit> installationDistance;
}
