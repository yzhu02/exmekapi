package com.exmek.core.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.exmek.commons.expr.RelationalOperator;

public class RelationalOperatorUtils {

	public static final RelationalOperator[] NUMBER_SUPPORTED_RELATIONAL_OPERATORS =
			new RelationalOperator[] {
					RelationalOperator.EQ,
					RelationalOperator.GTE,
					RelationalOperator.LTE,
					
					RelationalOperator.GT,
					RelationalOperator.LT,
					RelationalOperator.NE,
					RelationalOperator.BETWEEN
			};

	public static final RelationalOperator[] STRING_SUPPORTED_RELATIONAL_OPERATORS =
			new RelationalOperator[] {
					RelationalOperator.EQ,
					RelationalOperator.GTE,
					RelationalOperator.LTE,
					
					RelationalOperator.GT,
					RelationalOperator.LT,
					RelationalOperator.NE,
					
					RelationalOperator.CONTAINS,
					RelationalOperator.STARTWITH,
					RelationalOperator.ENDWITH,
					RelationalOperator.LIKE,
					RelationalOperator.BETWEEN
			};
	
	public static List<RelationalOperator> getNumberSupportedRelationalOperators() {
		return Collections.unmodifiableList(Arrays.asList(NUMBER_SUPPORTED_RELATIONAL_OPERATORS));
	}
	
	public static List<RelationalOperator> getStringSupportedRelationalOperators() {
		return Collections.unmodifiableList(Arrays.asList(STRING_SUPPORTED_RELATIONAL_OPERATORS));
	}	
}
