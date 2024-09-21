package com.exmek.core.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ForceUnit {

	N("N");
	
	private String symbol;
	
	ForceUnit(String symbol) {
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
    public static ForceUnit fromSymbol(String symbol) {
    	if (N.symbol.equals(symbol)) {
    		return N;
    	} else {
    		throw new IllegalArgumentException("Failed to parse ForceUnit " + symbol);
    	}
    }
}
