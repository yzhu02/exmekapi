package com.exmek.core.persistence.converter;

import com.exmek.core.commons.enums.SizeMeasurementType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SizeMeasurementTypeConverter implements AttributeConverter<SizeMeasurementType, String> {

	@Override
	public String convertToDatabaseColumn(SizeMeasurementType type) {
		return type != null ? type.getSymbol() : null;
	}

	@Override
	public SizeMeasurementType convertToEntityAttribute(String symbol) {
		return symbol != null ? SizeMeasurementType.fromSymbol(symbol) : null;
	}

}
