package com.exmek.core.utils;

public class MotorUtils {

	private MotorUtils() {
	}

	public static boolean isLinearStepperMotorSeries(String series) {
		return series != null && series.startsWith("LS");
	}
	
	public static boolean isLeadFlattenLinearStepperMotor(String model) {
		return model != null && model.startsWith("LS") && model.contains("-");
	}

	public static String makeLinearStepperMotorLeadFlattenModel(String baseModel, String leadCode) {
		return baseModel + "-" + leadCode;
	}
}
