package commons.expr;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RelationalOperator {

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

	REGEX("=~"),

	CONTAINS("CONTAINS"),
    MATCHES("MATCHES"),
    STARTWITH("STARTWITH"),
    ENDWITH("ENDWITH"),
    LIKE("LIKE"),
	
	IS("IS");
   
    private final String symbol;

    RelationalOperator(String symbol) {
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

    public static RelationalOperator fromSymbol(String symbol) {
    	if ("=".equals(symbol)) {
    		return EQ;
    	}
    	for (RelationalOperator operator : RelationalOperator.values()) { 
    		if (operator.symbol.equals(symbol)) {
    			return operator;
    		}
    	}
    	throw new IllegalArgumentException("Failed to parse RelationalOperator " + symbol);
    }
}
