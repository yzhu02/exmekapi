package com.exmek.core.persistence.converter;

import com.exmek.core.commons.enums.ResistanceUnit;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ResistanceUnitConverter implements AttributeConverter<ResistanceUnit, String> {

	@Override
	public String convertToDatabaseColumn(ResistanceUnit type) {
		return type != null ? type.getSymbol() : null;
	}

	@Override
	public ResistanceUnit convertToEntityAttribute(String symbol) {
		return symbol != null ? ResistanceUnit.fromSymbol(symbol) : null;
	}

}
