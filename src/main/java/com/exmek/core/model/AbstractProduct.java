package com.exmek.core.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.exmek.core.commons.enums.LengthUnit;
import com.exmek.core.commons.enums.SizeMeasurementType;
import com.exmek.core.commons.enums.WeightUnit;
import com.exmek.core.commons.model.MeasuredValue;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractProduct extends AbstractModel.Namable {

	private String series;

	private AbstractSeries productSeries;
	
	private String model;
	// By default it's same as model. 
	// Different only when used to use different key rather than model to locate the resources like images.
	private String resourceKey;

	private String description;

	private MeasuredValue<BigDecimal, LengthUnit> length;

	private MeasuredValue<BigDecimal, WeightUnit> weight;

	private MeasuredValue.Typed<BigDecimal, LengthUnit, SizeMeasurementType> frameSize;

	private BigDecimal nemaSize;

	private List<Spec> allSpecs;

	private List<String> mechanicalImagePaths;
	
	private List<String> threeDModelPaths;
	
	private Map<String, List<String>> threeDViewPaths;
	
	private List<String> techDocPaths;

	private Map<String, List<String>> additionalImagePaths;
	
	private Boolean isNew;
}
