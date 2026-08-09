package com.exmek.core.commons.model;

import lombok.Data;

@Data
public class MeasuredRawValue<V, U> implements UnitableValue<V, U> {
	
	private V value;

	private U unit;

	@Override
	public String toString() {
		return value + " " + unit;
	}

	public static <V, U> MeasuredRawValue<V, U> of(V value, U unit) {
		if (value == null) {
			return null;
		}
		MeasuredRawValue<V, U> mv = new MeasuredRawValue<>();
		mv.setValue(value);
		mv.setUnit(unit);
		return mv;
	}
}
