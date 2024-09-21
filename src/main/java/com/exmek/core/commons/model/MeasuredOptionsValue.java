package com.exmek.core.commons.model;

import java.util.List;

import lombok.Data;

@Data
public class MeasuredOptionsValue<V, U> {
	
	private List<V> options;

	private U unit;

	@Override
	public String toString() {
		return options + " " + unit;
	}
}
