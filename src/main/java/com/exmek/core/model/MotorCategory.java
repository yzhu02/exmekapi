package com.exmek.core.model;

import java.util.Arrays;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class MotorCategory extends AbstractModel {

	public static enum Category {
		BLDC_INTERNAL_ROTOR(Supertype.DC, Type.BLDC),
		BLDC_EXTERNAL_ROTOR(Supertype.DC, Type.BLDC),
		BLDC_FRAMELESS(Supertype.DC, Type.BLDC),
		BLDC_SERVO(Supertype.DC, Type.BLDC),
		BLDC_CORELESS(Supertype.DC, Type.BLDC),
		BLDC_DIRECT_DRIVE(Supertype.DC, Type.BLDC),
		BLDC_WITH_GEARBOX(Supertype.DC, Type.BLDC),

		PERMANENT_MAGNET_BRUSH(Supertype.DC, Type.BRUSH),
		BRUSH_WITH_GEARBOX(Supertype.DC, Type.BRUSH),
		
		INTEGRATED(Supertype.DC, null),
		
		SOLAR_TRACKING_APPLICATION(Supertype.DC, null),
		MATERIAL_HANDLING_SOLUTION(Supertype.DC, null),
		
		STEPPER_STANDARD_TORQUE(Supertype.STEPPER, null),
		STEPPER_FLAT(Supertype.STEPPER, null),
		STEPPER_WITH_CONTROL(Supertype.STEPPER, null),
		STEPPER_LINEAR(Supertype.STEPPER, null);
		
		private Supertype supertype;
		private Type type;
		
		Category(Supertype supertype, Type type) {
			this.supertype = supertype;
			this.type = type;
		}
		
		public Supertype getSupertype() {
			return this.supertype;
		}
		
		public Type getType() {
			return this.type;
		}
		
		public static Category[] getCategories(Supertype supertype, Type type) {
			return Arrays.stream(Category.values())
					.filter(c -> (supertype == null || c.getSupertype() == supertype) && (type == null || c.getType() == type))
					.toArray(Category[]::new);
		}
	}
	
	public static enum Supertype {
		DC, STEPPER
	}
	
	public static enum Type {
		BLDC, BRUSH
	}

	private Category category;
	
	private String displayName;
	
	private String description;
	
	private Map<String, String> technicalData;
}
