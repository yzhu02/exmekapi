package com.exmek.core.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SizeMeasurementType implements Symbolizable {

	DIAMETER("φ"),

	SIDE("□");

	private final String symbol;
	
	SizeMeasurementType(String symbol) {
		this.symbol = symbol;
	}

    @JsonValue
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    @JsonCreator
    public static SizeMeasurementType fromSymbol(String symbol) {
    	if (DIAMETER.symbol.equals(symbol) || "Φ".equals(symbol)) {
    		return DIAMETER;
    	} else if (SIDE.symbol.equals(symbol))  {
    		return SIDE;
    	} else {
    		throw new IllegalArgumentException("Failed to parse SizeMeasurementType " + symbol);
    	}
    }
}
