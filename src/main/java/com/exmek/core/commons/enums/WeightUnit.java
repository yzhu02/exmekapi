package com.exmek.core.commons.enums;

import com.exmek.core.commons.model.UnitBaseValuable;

public enum WeightUnit implements UnitBaseValuable {

	g(0.001), kg(1);

	private double baseValue;
	
	WeightUnit(double baseValue) {
		this.baseValue = baseValue;
	}

	@Override
	public double getBaseValue() {
		return baseValue;
	}
}
