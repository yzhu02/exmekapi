package com.exmek.core.persistence.entity;

import java.math.BigDecimal;

import com.exmek.core.annotation.Searchable;
import com.exmek.core.commons.enums.LengthUnit;
import com.exmek.core.commons.enums.SizeMeasurementType;
import com.exmek.core.commons.enums.WeightUnit;
import com.exmek.core.persistence.converter.SizeMeasurementTypeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractProductEntity extends AbstractManagableEntity {

	public static final String FIELD_NAME_SERIES		= AbstractSeriesEntity.FIELD_NAME_SERIES;
	public static final String FIELD_NAME_MODEL			= "model";
	public static final String FIELD_NAME_NAME			= "name";
	public static final String FIELD_NAME_DESCRIPTION	= "description";
	
	public static final String UNIT_FIELD_SUFFIX	= "Unit";
	public static final String TYPE_FIELD_SUFFIX	= "Type";

	@Column(name = "SERIES")
	private String series;

	@Searchable
	@Column(name = "MODEL")
	private String model;
		
	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Searchable
	@Column(name = "LENGTH")
	private BigDecimal length;
	
	@Column(name = "LENGTH_UNIT")
	@Enumerated(EnumType.STRING)
	private LengthUnit lengthUnit;

	@Searchable
	@Column(name = "WEIGHT")
	private BigDecimal weight;
	
	@Column(name = "WEIGHT_UNIT")
	@Enumerated(EnumType.STRING)
	private WeightUnit weightUnit;

	@Searchable
	@Column(name = "FRAME_SIZE")
	private BigDecimal frameSize;

	@Column(name = "FRAME_SIZE_UNIT")
	@Enumerated(EnumType.STRING)
	private LengthUnit frameSizeUnit;
	
	@Column(name = "FRAME_SIZE_TYPE")
//	@Enumerated(EnumType.STRING)
	@Convert(converter = SizeMeasurementTypeConverter.class)
	private SizeMeasurementType frameSizeType;

	@Column(name = "NEMA_SIZE")
	private BigDecimal nemaSize;

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public LengthUnit getLengthUnit() {
		return lengthUnit;
	}

	public void setLengthUnit(LengthUnit lengthUnit) {
		this.lengthUnit = lengthUnit;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public WeightUnit getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(WeightUnit weightUnit) {
		this.weightUnit = weightUnit;
	}

	public BigDecimal getFrameSize() {
		return frameSize;
	}

	public void setFrameSize(BigDecimal frameSize) {
		this.frameSize = frameSize;
	}

	public LengthUnit getFrameSizeUnit() {
		return frameSizeUnit;
	}

	public void setFrameSizeUnit(LengthUnit frameSizeUnit) {
		this.frameSizeUnit = frameSizeUnit;
	}

	public SizeMeasurementType getFrameSizeType() {
		return frameSizeType;
	}

	public void setFrameSizeType(SizeMeasurementType frameSizeType) {
		this.frameSizeType = frameSizeType;
	}

	public BigDecimal getNemaSize() {
		return nemaSize;
	}

	public void setNemaSize(BigDecimal nemaSize) {
		this.nemaSize = nemaSize;
	}

}
