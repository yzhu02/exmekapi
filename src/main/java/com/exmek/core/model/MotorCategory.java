package com.exmek.core.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class MotorCategory extends AbstractModel {
		
	public static enum Type {
		BLDC, BRUSH
	}

	public static final String BLDC_INTERNAL_ROTOR			= "BLDC_INTERNAL_ROTOR"; 
	public static final String BLDC_EXTERNAL_ROTOR			= "BLDC_EXTERNAL_ROTOR";
	public static final String BLDC_FRAMELESS				= "BLDC_FRAMELESS";
	public static final String BLDC_CORELESS				= "BLDC_CORELESS";
	public static final String BLDC_SERVO					= "BLDC_SERVO";
	public static final String BLDC_WITH_GEARBOX			= "BLDC_WITH_GEARBOX";
	public static final String BLDC_INTEGRATED				= "BLDC_INTEGRATED";
	public static final String PERMANENT_MAGNET_BRUSH		= "PERMANENT_MAGNET_BRUSH";
	public static final String BRUSH_WITH_GEARBOX			= "BRUSH_WITH_GEARBOX";
	public static final String SOLAR_TRACKING_APPLICATION	= "SOLAR_TRACKING_APPLICATION";
	public static final String MATERIAL_HANDLING_SOLUTION	= "MATERIAL_HANDLING_SOLUTION";
	
	public static final String STEPPER_HYBRID				= "STEPPER_HYBRID";
	public static final String STEPPER_FLAT					= "STEPPER_FLAT";
	public static final String STEPPER_INTEGRATED			= "STEPPER_INTEGRATED";
	public static final String STEPPER_LINEAR				= "STEPPER_LINEAR";

	private String category;
	
	private Type type;
	
	private String displayName;
	
	private String description;
	
	private Map<String, String> technicalData;
	
	private Boolean hasNew;
}
