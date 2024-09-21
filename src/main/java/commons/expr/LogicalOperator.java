package commons.expr;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LogicalOperator {

    AND("&&"),
    OR("||"),
    NOT("!");

    private final String symbol;

    LogicalOperator(String symbol) {
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

    public static LogicalOperator from(String nameOrSymbol) {
    	if (AND.symbol.equals(nameOrSymbol) || AND.name().equals(nameOrSymbol)) {
    		return AND;
    	} else if (OR.symbol.equals(nameOrSymbol) || OR.name().equals(nameOrSymbol)) {
    		return OR;
    	} else if (NOT.symbol.equals(nameOrSymbol) || NOT.name().endsWith(nameOrSymbol)) {
    		return NOT;
    	} else {
    		throw new IllegalArgumentException("Failed to parse LogicalOperator " + nameOrSymbol);
    	}
    }
}