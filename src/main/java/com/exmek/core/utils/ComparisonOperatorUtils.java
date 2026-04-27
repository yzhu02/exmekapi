package com.exmek.core.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.exmek.commons.expr.ComparisonOperator;

public class ComparisonOperatorUtils {

	public static final ComparisonOperator[] NUMBER_SUPPORTED_COMPARISON_OPERATORS =
			new ComparisonOperator[] {
					ComparisonOperator.EQ,
					ComparisonOperator.GTE,
					ComparisonOperator.LTE,
					
					ComparisonOperator.GT,
					ComparisonOperator.LT,
					ComparisonOperator.NE,
					ComparisonOperator.BETWEEN
			};

	public static final ComparisonOperator[] STRING_SUPPORTED_COMPARISON_OPERATORS =
			new ComparisonOperator[] {
					ComparisonOperator.EQ,
					ComparisonOperator.GTE,
					ComparisonOperator.LTE,
					
					ComparisonOperator.GT,
					ComparisonOperator.LT,
					ComparisonOperator.NE,
					
					ComparisonOperator.CONTAINS,
					ComparisonOperator.STARTWITH,
					ComparisonOperator.ENDWITH,
					ComparisonOperator.LIKE,
					ComparisonOperator.BETWEEN
			};
	
	public static List<ComparisonOperator> getNumberSupportedComparisonOperators() {
		return Collections.unmodifiableList(Arrays.asList(NUMBER_SUPPORTED_COMPARISON_OPERATORS));
	}
	
	public static List<ComparisonOperator> getStringSupportedComparisonOperators() {
		return Collections.unmodifiableList(Arrays.asList(STRING_SUPPORTED_COMPARISON_OPERATORS));
	}	
}
