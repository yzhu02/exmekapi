package com.exmek.core.commons.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CurveLine {

	private String name;
	
	@JsonProperty("xAxisName")
	private String xAxisName;
	
	@JsonProperty("yAxisName")
	private String yAxisName;
	
	private List<Point<BigDecimal>> points;
	
	public CurveLine addPoint(Point<BigDecimal> p) {
		if (this.points == null) {
			this.points = new ArrayList<>();
		}
		this.points.add(p);
		return this;
	}
}
