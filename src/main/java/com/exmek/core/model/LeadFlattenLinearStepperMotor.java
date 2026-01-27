package com.exmek.core.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class LeadFlattenLinearStepperMotor extends StepperMotor {

	private String leadCode;

	private BigDecimal screwDiameterInch;

	private BigDecimal screwDiameterMM;

	private BigDecimal leadInch;
		
	private BigDecimal leadMM;
	
	private Integer threads;

}
