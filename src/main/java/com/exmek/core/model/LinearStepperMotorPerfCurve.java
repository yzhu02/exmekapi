package com.exmek.core.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class LinearStepperMotorPerfCurve extends MotorPerfCurve {

	private SpeedMeasure speedMeasure;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class SpeedMeasure {
		
		private String[] units;
		
		private BigDecimal[][] values;
	}
}
