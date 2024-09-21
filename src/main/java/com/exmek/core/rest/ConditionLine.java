package com.exmek.core.rest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commons.expr.RelationalOperator;
import lombok.Data;

@Data
public class ConditionLine {

	static final String CONDITION_LINE_REGEX = "(\\w+)\\s*(=|==|!=|>|>=|<|<=|\\sBETWEEN\\s|\\sCONTAINS\\s|\\sSTARTWITH\\s|\\sENDWITH\\s|\\sLIKE\\s|\\sIS\\s)\\s*([^=]+)";

	private String fieldName;
	
	/**
	 * The comparison operator including:
	 * =
	 * ==
	 * !=
	 * >
	 * >=
	 * <
	 * <=
	 * BETWEEN
	 * CONTAINS
	 * STARTWITH
	 * ENDWITH
	 * LIKE
	 * IS
	 */
	private RelationalOperator operator;
	
	private String value;

	private String value2;

	public static ConditionLine parse(String conditionStr) {
		if (conditionStr == null || conditionStr.length() == 0) {
			return null;
		}
		Pattern p = Pattern.compile(CONDITION_LINE_REGEX);
		Matcher m = p.matcher(conditionStr);
		if (m.matches()) {
			String fieldName = m.group(1);
			String op = m.group(2);
			String value = m.group(3);
			ConditionLine c = new ConditionLine();
			c.setFieldName(fieldName);
			c.setOperator(RelationalOperator.fromSymbol(op.trim()));
			c.setValue(value);
			if (RelationalOperator.BETWEEN == c.getOperator()) {
				String AND = "AND";
				int inx = value.indexOf(AND);
				if (inx > 1) {
					c.setValue(value.substring(0, inx - 1).trim());
					c.setValue2(value.substring(inx + AND.length() + 1, value.length()).trim());
				}
			}
			return c;
		}
		return null;
	}
}
