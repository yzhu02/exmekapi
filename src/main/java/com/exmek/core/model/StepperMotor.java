package com.exmek.core.model;

import java.math.BigDecimal;

import com.exmek.core.commons.enums.AngleUnit;
import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.ForceUnit;
import com.exmek.core.commons.enums.InductanceUnit;
import com.exmek.core.commons.enums.ResistanceUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.commons.model.MeasuredValue;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class StepperMotor extends AbstractMotor {
	
	private MeasuredValue<BigDecimal, CurrentUnit> phaseCurrent;
	
	private MeasuredValue<BigDecimal, ResistanceUnit> phaseResistance;
	
	private MeasuredValue<BigDecimal, InductanceUnit> phaseInductance;
		
	private MeasuredValue<BigDecimal, TorqueUnit> holdingTorque;
	
	private MeasuredValue<BigDecimal, TorqueUnit> detentTorque;

	private MeasuredValue<BigDecimal, AngleUnit> stepAngle;
	
	private MeasuredValue<BigDecimal, ForceUnit> maxThrust;

}
