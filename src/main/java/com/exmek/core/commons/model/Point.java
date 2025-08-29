package com.exmek.core.commons.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
