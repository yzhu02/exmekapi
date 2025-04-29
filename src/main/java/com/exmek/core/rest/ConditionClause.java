package com.exmek.core.rest;

import java.util.List;

import com.exmek.commons.expr.LogicalOperator;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ConditionClause {

	private LogicalOperator operator;

	/**
	 * The condition is in form of [field][op][value]
	 * Example:
	 * 	ratedVoltage=24
	 * 	ratedRotatingSpeed>=2000
	 * 	ratedRotatingSpeed<=4000
	 * 
	 */
	private List<String> conditions;
	
	private List<ConditionClause> subConditionClauses;

}
