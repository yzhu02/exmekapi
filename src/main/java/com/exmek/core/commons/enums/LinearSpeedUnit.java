package com.exmek.core.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LinearSpeedUnit implements Symbolizable {

	mps("m/s");
	
	private final String symbol;
	
	LinearSpeedUnit(String symbol) {
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
    public static LinearSpeedUnit fromSymbol(String symbol) {
    	if (mps.symbol.equals(symbol)) {
    		return mps;
    	} else {
    		throw new IllegalArgumentException("Failed to parse LinearSpeedUnit " + symbol);
    	}
    }
}
