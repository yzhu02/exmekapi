package com.exmek.core.commons.model;

import lombok.Data;

@Data
public class Point<T extends Number> {

	private T x;

	private T y;

	public static <T extends Number> Point<T> of(T x, T y) {
		Point<T> p = new Point<>();
		p.setX(x);
		p.setY(y);
		return p;
	}
}
