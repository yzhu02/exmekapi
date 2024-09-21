package com.exmek.core.persistence.converter;

import com.exmek.core.commons.enums.LinearSpeedUnit;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LinearSpeedUnitConverter implements AttributeConverter<LinearSpeedUnit, String> {

	@Override
	public String convertToDatabaseColumn(LinearSpeedUnit type) {
		return type != null ? type.getSymbol() : null;
	}

	@Override
	public LinearSpeedUnit convertToEntityAttribute(String symbol) {
		return symbol != null ? LinearSpeedUnit.fromSymbol(symbol) : null;
	}

}
