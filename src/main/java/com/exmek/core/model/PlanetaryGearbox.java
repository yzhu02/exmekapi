package com.exmek.core.model;

import java.math.BigDecimal;
import java.util.List;

import com.exmek.core.commons.enums.ForceUnit;
import com.exmek.core.commons.enums.TorqueUnit;
import com.exmek.core.commons.model.MeasuredValue;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class PlanetaryGearbox extends AbstractProduct {

	private Integer numOfStages;
	
	private List<String> reductionRatios;

	private MeasuredValue<BigDecimal, String> efficiency;
	
	private MeasuredValue<BigDecimal, TorqueUnit> ratedContinuousTorque;

	private MeasuredValue<BigDecimal, TorqueUnit> maxMomentaryTorque;

	private MeasuredValue<BigDecimal, ForceUnit> maxRadialLoad;

	private MeasuredValue<BigDecimal, ForceUnit> maxAxialLoad;

	private MeasuredValue<BigDecimal, ForceUnit> maxShaftPress;

	private String operatingTemperature;
	
	private String recommendInputSpeed;
}
