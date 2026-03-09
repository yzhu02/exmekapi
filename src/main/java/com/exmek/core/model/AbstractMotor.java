package com.exmek.core.model;

import java.math.BigDecimal;
import java.util.List;

import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.VoltageUnit;
import com.exmek.core.commons.model.MeasuredValue;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractMotor extends AbstractProduct {

	private String category;

	private MotorCategory motorCategory;
	
	private MeasuredValue<BigDecimal, VoltageUnit> ratedVoltage;
	
	private MeasuredValue<BigDecimal, CurrentUnit> ratedCurrent;
	
	private MeasuredValue<BigDecimal, CurrentUnit> peakCurrent;

	private List<? extends MotorPerfCurve> perfCurves;

}
