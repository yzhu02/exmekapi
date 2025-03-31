package com.exmek.core.gensearch;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralSearchItem {

	public static enum Type {
		DC_MOTOR, STEPPER_MOTOR, PLANETARY_GEARBOX, BRAKE
	}

	private Type type;

	private String model;
	
	private String description;
	
}
