package com.exmek.core.model;

import java.math.BigDecimal;
import java.util.List;

import com.exmek.core.commons.model.CurveLine;
import com.exmek.core.commons.model.Range;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MotorPerfCurve {

	private String title;
	
	private List<CurveLine> curveLines;
	
	/**
	 * <pre>
	 * Presented only when multiple Y-Axis represent same measurement but with different units, 
	 * for example: Torque(oz-in) vs Torque(Ncm)
	 * yAxisEquivalentBoundaries[n].min should matches with yAxisEquivalentBoundaries[n+1].min, i.e. they should be on same horizontal line on the 2-D coordinate system.
	 * yAxisEquivalentBoundaries[n].max should matches with yAxisEquivalentBoundaries[n+1].max, i.e. they should be on same horizontal line on the 2-D coordinate system. 
	 * 
	 * If only one Y-Axis or the Y-Axis represent different measurement, then it is null.
	 * </pre> 
	 */
	private List<Range<BigDecimal>> yAxisEquivalentBoundaries;
}
