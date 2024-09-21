package com.exmek.core.persistence.converter;

import com.exmek.core.commons.enums.AngleUnit;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AngleUnitConverter implements AttributeConverter<AngleUnit, String> {

	@Override
	public String convertToDatabaseColumn(AngleUnit type) {
		return type != null ? type.getSymbol() : null;
	}

	@Override
	public AngleUnit convertToEntityAttribute(String symbol) {
		return symbol != null ? AngleUnit.fromSymbol(symbol) : null;
	}

}
