package com.exmek.core.config;

import lombok.Data;

@Data
public class CurveCoordinate {

	//name of curve line
	private String name;
	
	//column name of x-axis
	private String x;
	
	//column name of y-axis
	private String y;
}
