package com.exmek.core.model;

import java.util.List;

import com.exmek.core.commons.model.CurveLine;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MotorPerfCurve {

	private String title;
	
	private List<CurveLine> curveLines;
}
