package com.exmek.core.commons.enums;

import com.exmek.core.commons.model.UnitBaseValuable;

public enum LengthUnit implements UnitBaseValuable {

	mm(0.001), cm(0.01), dm(0.1), m(1);
	
	private double baseValue;
	
	LengthUnit(double baseValue) {
		this.baseValue = baseValue;
	}

	@Override
	public double getBaseValue() {
		return baseValue;
	}
}
