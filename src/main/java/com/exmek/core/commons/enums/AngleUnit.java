package com.exmek.core.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AngleUnit {

	deg("°");
	
	private final String symbol;
	
	AngleUnit(String symbol) {
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
    public static AngleUnit fromSymbol(String symbol) {
    	if (deg.symbol.equals(symbol)) {
    		return deg;
    	} else {
    		throw new IllegalArgumentException("Failed to parse AngleUnit " + symbol);
    	}
    }

}
