package com.exmek.core.commons.enums;

import com.exmek.core.commons.model.UnitBaseValuable;

public enum PowerUnit implements UnitBaseValuable {

	W(1), KW(1000);
	
	private double baseValue;
	
	PowerUnit(double baseValue) {
		this.baseValue = baseValue;
	}

	@Override
	public double getBaseValue() {
		return baseValue;
	}
}
