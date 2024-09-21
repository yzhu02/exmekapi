package com.exmek.core.model;

import java.math.BigDecimal;

import com.exmek.core.commons.enums.CurrentUnit;
import com.exmek.core.commons.enums.PowerUnit;
import com.exmek.core.commons.enums.ResistanceUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.commons.enums.VoltageUnit;
import com.exmek.core.commons.model.MeasuredValue;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class Brake extends AbstractProduct {
	
	private MeasuredValue<BigDecimal, VoltageUnit> ratedVoltage;
	
	private MeasuredValue<BigDecimal, ResistanceUnit> resistance;

	private MeasuredValue<BigDecimal, CurrentUnit> current;
	
	private MeasuredValue<BigDecimal, TorqueUnit> staticTorque;
	
	private MeasuredValue<BigDecimal, PowerUnit> ratedPower;

	private MeasuredValue<BigDecimal, VoltageUnit> startVoltage;

}
