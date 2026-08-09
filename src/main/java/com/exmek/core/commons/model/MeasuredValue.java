package com.exmek.core.commons.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class MeasuredValue<V extends Number, U> implements UnitableValue<V, U> {
	
	private V value;

	private U unit;

	@Override
	public String toString() {
		return value + " " + unit;
	}

	public static <V extends Number, U> MeasuredValue<V, U> of(V value, U unit) {
		if (value == null) {
			return null;
		}
		MeasuredValue<V, U> mv = new MeasuredValue<>();
		mv.setValue(value);
		mv.setUnit(unit);
		return mv;
	}
	
	@Data
	@EqualsAndHashCode(callSuper = true)
	public static class Typed<V extends Number, U, T> extends MeasuredValue<V, U> {
		
		private T type;
	}
	
	public static <V extends Number, U, T> Typed<V, U, T> of(V value, U unit, T type) {
		if (value == null) {
			return null;
		}
		Typed<V, U, T> mv = new Typed<>();
		mv.setValue(value);
		mv.setUnit(unit);
		mv.setType(type);
		return mv;
	}
}
