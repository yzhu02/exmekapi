package com.exmek.core.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import commons.expr.RelationalOperator;


class ConditionLineTest {

	@Test
	void testParseConditionLine() {
		String s = "ratedVoltage=24";
		ConditionLine c = ConditionLine.parse(s);
		assertEquals("ratedVoltage", c.getFieldName());
		assertSame(RelationalOperator.EQ, c.getOperator());
		assertEquals("24", c.getValue());
		
		s = "ratedVoltage = 24";
		c = ConditionLine.parse(s);
		assertEquals("ratedVoltage", c.getFieldName());
		assertSame(RelationalOperator.EQ, c.getOperator());
		assertEquals("24", c.getValue());
		
		s = "ratedVoltage==24";
		c = ConditionLine.parse(s);
		assertEquals("ratedVoltage", c.getFieldName());
		assertSame(RelationalOperator.EQ, c.getOperator());
		assertEquals("24", c.getValue());
		
		s = "ratedVoltage == 24";
		c = ConditionLine.parse(s);
		assertEquals("ratedVoltage", c.getFieldName());
		assertSame(RelationalOperator.EQ, c.getOperator());
		assertEquals("24", c.getValue());
		
		s = "ratedTorque>0.1";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(RelationalOperator.GT, c.getOperator());
		assertEquals("0.1", c.getValue());
		
		s = "ratedTorque > 0.1";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(RelationalOperator.GT, c.getOperator());
		assertEquals("0.1", c.getValue());
		
		s = "ratedRotatingSpeed>=2000";
		c = ConditionLine.parse(s);
		assertEquals("ratedRotatingSpeed", c.getFieldName());
		assertSame(RelationalOperator.GTE, c.getOperator());
		assertEquals("2000", c.getValue());
		
		s = "ratedRotatingSpeed  >= 2000";
		c = ConditionLine.parse(s);
		assertEquals("ratedRotatingSpeed", c.getFieldName());
		assertSame(RelationalOperator.GTE, c.getOperator());
		assertEquals("2000", c.getValue());
		
		s = "ratedTorque<18.1";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(RelationalOperator.LT, c.getOperator());
		assertEquals("18.1", c.getValue());
		
		s = "ratedTorque < 18.1";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(RelationalOperator.LT, c.getOperator());
		assertEquals("18.1", c.getValue());
		
		s = "ratedRotatingSpeed<=4000";
		c = ConditionLine.parse(s);
		assertEquals("ratedRotatingSpeed", c.getFieldName());
		assertSame(RelationalOperator.LTE, c.getOperator());
		assertEquals("4000", c.getValue());
		
		s = "ratedRotatingSpeed <=  4000";
		c = ConditionLine.parse(s);
		assertEquals("ratedRotatingSpeed", c.getFieldName());
		assertSame(RelationalOperator.LTE, c.getOperator());
		assertEquals("4000", c.getValue());
		
		s = "rotorType!=EXTERNAL";
		c = ConditionLine.parse(s);
		assertEquals("rotorType", c.getFieldName());
		assertSame(RelationalOperator.NE, c.getOperator());
		assertEquals("EXTERNAL", c.getValue());
		
		s = "rotorType != EXTERNAL";
		c = ConditionLine.parse(s);
		assertEquals("rotorType", c.getFieldName());
		assertSame(RelationalOperator.NE, c.getOperator());
		assertEquals("EXTERNAL", c.getValue());
		
		s = "name CONTAINS BLDC";
		c = ConditionLine.parse(s);
		assertEquals("name", c.getFieldName());
		assertSame(RelationalOperator.CONTAINS, c.getOperator());
		assertEquals("BLDC", c.getValue());
		
		s = "productNo STARTWITH   ME";
		c = ConditionLine.parse(s);
		assertEquals("productNo", c.getFieldName());
		assertSame(RelationalOperator.STARTWITH, c.getOperator());
		assertEquals("ME", c.getValue());
		
		s = "productNo   ENDWITH YS100";
		c = ConditionLine.parse(s);
		assertEquals("productNo", c.getFieldName());
		assertSame(RelationalOperator.ENDWITH, c.getOperator());
		assertEquals("YS100", c.getValue());
		
		s = "productNo   LIKE YS";
		c = ConditionLine.parse(s);
		assertEquals("productNo", c.getFieldName());
		assertSame(RelationalOperator.LIKE, c.getOperator());
		assertEquals("YS", c.getValue());
		
		s = "deleted IS true";
		c = ConditionLine.parse(s);
		assertEquals("deleted", c.getFieldName());
		assertSame(RelationalOperator.IS, c.getOperator());
		assertEquals("true", c.getValue());
		
		s = "ratedTorque BETWEEN 0.55 AND 9.80";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(RelationalOperator.BETWEEN, c.getOperator());
		assertEquals("0.55", c.getValue());
		assertEquals("9.80", c.getValue2());
	}

}
