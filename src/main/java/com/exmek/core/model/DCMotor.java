package com.exmek.core.model;

import java.math.BigDecimal;

import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.LinearSpeedUnit;
import com.exmek.core.commons.enums.PowerUnit;
import com.exmek.core.commons.enums.RotatingSpeedUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.commons.enums.WeightUnit;
import com.exmek.core.commons.model.MeasuredValue;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class DCMotor extends AbstractMotor {
	
	private MeasuredValue<BigDecimal, PowerUnit> ratedPower;
	
	private MeasuredValue<BigDecimal, TorqueUnit> ratedTorque;
	
	private MeasuredValue<Integer, RotatingSpeedUnit> ratedRotatingSpeed;
	
	private MeasuredValue<BigDecimal, LinearSpeedUnit> ratedLinearSpeed;

	private MeasuredValue<BigDecimal, TorqueUnit> peakTorque;
	
	private MeasuredValue<BigDecimal, WeightUnit> maxSortingWeight;
	
	private MeasuredValue<BigDecimal, CurrentUnit> noloadCurrent;
	
	private MeasuredValue<Integer, RotatingSpeedUnit> noloadRotatingSpeed;

}
