package com.exmek.core.commons.enums;

import com.exmek.core.commons.model.UnitBaseValuable;

public enum TorqueUnit implements UnitBaseValuable {

	Nm(1), Ncm(0.01), mNm(0.001), Nmm(0.001);

	private double baseValue;
	
	TorqueUnit(double baseValue) {
		this.baseValue = baseValue;
	}

	@Override
	public double getBaseValue() {
		return baseValue;
	}
}
