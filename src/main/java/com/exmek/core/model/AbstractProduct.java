package com.exmek.core.model;

import java.math.BigDecimal;
import java.util.List;

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

	private String model;

	private String description;

	private MeasuredValue<BigDecimal, LengthUnit> length;

	private MeasuredValue<BigDecimal, WeightUnit> weight;

	private MeasuredValue.Typed<BigDecimal, LengthUnit, SizeMeasurementType> frameSize;

	private BigDecimal nemaSize;

	private List<String> mechanicalImagePaths;
}
