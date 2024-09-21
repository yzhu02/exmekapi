package com.exmek.core.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResistanceUnit {

	ohm("Ω");
	
	private String symbol;
	
	ResistanceUnit(String symbol) {
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
    public static ResistanceUnit fromSymbol(String symbol) {
    	if (ohm.symbol.equals(symbol)) {
    		return ohm;
    	} else {
    		throw new IllegalArgumentException("Failed to parse ResistanceUnit " + symbol);
    	}
    }
}
