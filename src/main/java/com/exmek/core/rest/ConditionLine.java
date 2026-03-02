package com.exmek.core.rest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.exmek.commons.expr.LogicalOperator;
import com.exmek.commons.expr.RelationalOperator;
import com.exmek.commons.utils.MiscUtils;

import lombok.Data;

@Data
public class ConditionLine implements Cloneable {

	static final String CONDITION_LINE_REGEX = "(\\w+)\\s*(=|==|!=|>|>=|<|<=|\\sBETWEEN\\s|\\sCONTAINS\\s|\\sSTARTWITH\\s|\\sENDWITH\\s|\\sLIKE\\s|\\sIS\\s)\\s*((\\-?\\d+\\.?\\d*)?\\s*([^=]*))";

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
	private String numberValue;
	
	private String value2;
	private String numberValue2;

	private String unit;
	
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
			c.setNumberValue(m.group(4));
			if (StringUtils.isNotEmpty(c.getNumberValue()) && !StringUtils.equals(c.getValue(), c.getNumberValue()) && RelationalOperator.BETWEEN != c.getOperator()) {
				c.setUnit(m.group(5));
			}
			if (RelationalOperator.BETWEEN == c.getOperator()) {
				int inx = value.indexOf(LogicalOperator.AND.name());
				if (inx > 1) {
					c.setValue(value.substring(0, inx - 1).trim());
					String value2 = value.substring(inx + LogicalOperator.AND.name().length() + 1, value.length()).trim();
					c.setValue2(value2);
					int lastDigitInx = MiscUtils.findLastDigitIndexBackward(value2);
					if (lastDigitInx > -1) {
						c.setNumberValue2(value2.substring(0, lastDigitInx + 1));
						if (lastDigitInx < value2.length() - 1) {
							c.setUnit(value2.substring(lastDigitInx + 1).trim());
						}
					}
				}
			}
			return c;
		}
		return null;
	}
		
	@Override
	public ConditionLine clone() {
		ConditionLine cl = new ConditionLine();
		cl.fieldName = this.fieldName;
		cl.operator = this.operator;
		cl.value = this.value;
		cl.numberValue = this.numberValue;
		cl.unit = this.unit;
		cl.value2 = this.value2;
		cl.numberValue2 = this.numberValue2;
		return cl;
	}
}
