package com.exmek.core.commons.model;

import lombok.Data;

@Data
public class MeasuredRangeValue<V, U> {
	
	private V min;
	
	private V max;

	private U unit;

	@Override
	public String toString() {
		return "(" + min + " ~ " + max + ") " + unit;
	}
}
