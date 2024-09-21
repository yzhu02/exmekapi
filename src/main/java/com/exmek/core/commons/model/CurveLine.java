package com.exmek.core.commons.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CurveLine {

	private String name;
	
	private List<Point<BigDecimal>> points;
	
	public CurveLine addPoint(Point<BigDecimal> p) {
		if (this.points == null) {
			this.points = new ArrayList<>();
		}
		this.points.add(p);
		return this;
	}
}
