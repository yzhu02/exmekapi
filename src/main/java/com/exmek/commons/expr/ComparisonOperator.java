package com.exmek.commons.expr;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ComparisonOperator {

	EQ("=="),
	GTE(">="),
	LTE("<="),
	
	GT(">"),
	LT("<"),
	NE("!="),
	
	/**
	 * Type safe equals
	 */
	TSEQ("==="),

	/**
	 * Type safe not equals
	 */
	TSNE("!=="),

	BETWEEN("BETWEEN"),

	REGEX("=~"), //TODO: Not supported currently

	CONTAINS("CONTAINS"),
    MATCHES("MATCHES"), //TODO: Not supported currently
    STARTWITH("STARTWITH"),
    ENDWITH("ENDWITH"),
    LIKE("LIKE"),
	
	IS("IS");
   
    private final String symbol;

    ComparisonOperator(String symbol) {
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

    public static ComparisonOperator fromSymbol(String symbol) {
    	if ("=".equals(symbol)) {
    		return EQ;
    	}
    	for (ComparisonOperator operator : ComparisonOperator.values()) {
    		if (operator.symbol.equals(symbol)) {
    			return operator;
    		}
    	}
    	throw new IllegalArgumentException("Failed to parse ComparisonOperator " + symbol);
    }
}
