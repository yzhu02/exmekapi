package com.exmek.core.persistence.converter;

import com.exmek.core.commons.enums.ForceUnit;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ForceUnitConverter implements AttributeConverter<ForceUnit, String> {

	@Override
	public String convertToDatabaseColumn(ForceUnit type) {
		return type != null ? type.getSymbol() : null;
	}

	@Override
	public ForceUnit convertToEntityAttribute(String symbol) {
		return symbol != null ? ForceUnit.fromSymbol(symbol) : null;
	}

}
