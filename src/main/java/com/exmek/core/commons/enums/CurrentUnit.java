package com.exmek.core.commons.enums;

import com.exmek.core.commons.model.UnitBaseValuable;

public enum CurrentUnit implements UnitBaseValuable {

	A(1), mA(0.001);
	
	private double baseValue;
	
	CurrentUnit(double baseValue) {
		this.baseValue = baseValue;
	}

	@Override
	public double getBaseValue() {
		return baseValue;
	}
}
