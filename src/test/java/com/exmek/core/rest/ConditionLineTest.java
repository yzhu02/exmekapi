package com.exmek.core.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.exmek.commons.expr.ComparisonOperator;


class ConditionLineTest {

	@Test
	void testParseConditionLine_without_unit() {
		String s = "ratedVoltage=24";
		ConditionLine c = ConditionLine.parse(s);
		assertEquals("ratedVoltage", c.getFieldName());
		assertSame(ComparisonOperator.EQ, c.getOperator());
		assertEquals("24", c.getValue());
		assertEquals("24", c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedVoltage = 24";
		c = ConditionLine.parse(s);
		assertEquals("ratedVoltage", c.getFieldName());
		assertSame(ComparisonOperator.EQ, c.getOperator());
		assertEquals("24", c.getValue());
		assertEquals("24", c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedVoltage==24";
		c = ConditionLine.parse(s);
		assertEquals("ratedVoltage", c.getFieldName());
		assertSame(ComparisonOperator.EQ, c.getOperator());
		assertEquals("24", c.getValue());
		assertEquals("24", c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedVoltage == 24";
		c = ConditionLine.parse(s);
		assertEquals("ratedVoltage", c.getFieldName());
		assertSame(ComparisonOperator.EQ, c.getOperator());
		assertEquals("24", c.getValue());
		assertEquals("24", c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedTorque>0.1";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(ComparisonOperator.GT, c.getOperator());
		assertEquals("0.1", c.getValue());
		assertEquals("0.1", c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedTorque > 0.1";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(ComparisonOperator.GT, c.getOperator());
		assertEquals("0.1", c.getValue());
		assertEquals("0.1", c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		
		s = "ratedRotatingSpeed>=2000";
		c = ConditionLine.parse(s);
		assertEquals("ratedRotatingSpeed", c.getFieldName());
		assertSame(ComparisonOperator.GTE, c.getOperator());
		assertEquals("2000", c.getValue());
		assertEquals("2000", c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedRotatingSpeed  >= 2000";
		c = ConditionLine.parse(s);
		assertEquals("ratedRotatingSpeed", c.getFieldName());
		assertSame(ComparisonOperator.GTE, c.getOperator());
		assertEquals("2000", c.getValue());
		assertEquals("2000", c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedTorque<18.1";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(ComparisonOperator.LT, c.getOperator());
		assertEquals("18.1", c.getValue());
		assertEquals("18.1", c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedTorque < 18.1";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(ComparisonOperator.LT, c.getOperator());
		assertEquals("18.1", c.getValue());
		assertEquals("18.1", c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedRotatingSpeed<=4000";
		c = ConditionLine.parse(s);
		assertEquals("ratedRotatingSpeed", c.getFieldName());
		assertSame(ComparisonOperator.LTE, c.getOperator());
		assertEquals("4000", c.getValue());
		assertEquals("4000", c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedRotatingSpeed <=  4000";
		c = ConditionLine.parse(s);
		assertEquals("ratedRotatingSpeed", c.getFieldName());
		assertSame(ComparisonOperator.LTE, c.getOperator());
		assertEquals("4000", c.getValue());
		assertEquals("4000", c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "rotorType!=EXTERNAL";
		c = ConditionLine.parse(s);
		assertEquals("rotorType", c.getFieldName());
		assertSame(ComparisonOperator.NE, c.getOperator());
		assertEquals("EXTERNAL", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "rotorType != EXTERNAL";
		c = ConditionLine.parse(s);
		assertEquals("rotorType", c.getFieldName());
		assertSame(ComparisonOperator.NE, c.getOperator());
		assertEquals("EXTERNAL", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "name CONTAINS BLDC";
		c = ConditionLine.parse(s);
		assertEquals("name", c.getFieldName());
		assertSame(ComparisonOperator.CONTAINS, c.getOperator());
		assertEquals("BLDC", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "productNo STARTWITH   ME";
		c = ConditionLine.parse(s);
		assertEquals("productNo", c.getFieldName());
		assertSame(ComparisonOperator.STARTWITH, c.getOperator());
		assertEquals("ME", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "productNo   ENDWITH YS100";
		c = ConditionLine.parse(s);
		assertEquals("productNo", c.getFieldName());
		assertSame(ComparisonOperator.ENDWITH, c.getOperator());
		assertEquals("YS100", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "productNo   LIKE YS";
		c = ConditionLine.parse(s);
		assertEquals("productNo", c.getFieldName());
		assertSame(ComparisonOperator.LIKE, c.getOperator());
		assertEquals("YS", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "deleted IS true";
		c = ConditionLine.parse(s);
		assertEquals("deleted", c.getFieldName());
		assertSame(ComparisonOperator.IS, c.getOperator());
		assertEquals("true", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedTorque BETWEEN 0.55 AND 9.80";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(ComparisonOperator.BETWEEN, c.getOperator());
		assertEquals("0.55", c.getValue());
		assertEquals("0.55", c.getNumberValue());
		assertEquals("9.80", c.getValue2());
		assertEquals("9.80", c.getNumberValue2());
		assertNull(c.getUnit());
	}

	@Test
	void testParseConditionLine_with_unit() {
		String s = "ratedVoltage=24V";
		ConditionLine c = ConditionLine.parse(s);
		assertEquals("ratedVoltage", c.getFieldName());
		assertSame(ComparisonOperator.EQ, c.getOperator());
		assertEquals("24V", c.getValue());
		assertEquals("24", c.getNumberValue());
		assertEquals("V", c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedVoltage = 24 V";
		c = ConditionLine.parse(s);
		assertEquals("ratedVoltage", c.getFieldName());
		assertSame(ComparisonOperator.EQ, c.getOperator());
		assertEquals("24 V", c.getValue());
		assertEquals("24", c.getNumberValue());
		assertEquals("V", c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedVoltage==24V";
		c = ConditionLine.parse(s);
		assertEquals("ratedVoltage", c.getFieldName());
		assertSame(ComparisonOperator.EQ, c.getOperator());
		assertEquals("24V", c.getValue());
		assertEquals("24", c.getNumberValue());
		assertEquals("V", c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedVoltage == 24V";
		c = ConditionLine.parse(s);
		assertEquals("ratedVoltage", c.getFieldName());
		assertSame(ComparisonOperator.EQ, c.getOperator());
		assertEquals("24V", c.getValue());
		assertEquals("24", c.getNumberValue());
		assertEquals("V", c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedTorque>0.1Nm";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(ComparisonOperator.GT, c.getOperator());
		assertEquals("0.1Nm", c.getValue());
		assertEquals("0.1", c.getNumberValue());
		assertEquals("Nm", c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedTorque > 0.1 Nm";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(ComparisonOperator.GT, c.getOperator());
		assertEquals("0.1 Nm", c.getValue());
		assertEquals("0.1", c.getNumberValue());
		assertEquals("Nm", c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		
		s = "ratedRotatingSpeed>=2000rpm";
		c = ConditionLine.parse(s);
		assertEquals("ratedRotatingSpeed", c.getFieldName());
		assertSame(ComparisonOperator.GTE, c.getOperator());
		assertEquals("2000rpm", c.getValue());
		assertEquals("2000", c.getNumberValue());
		assertEquals("rpm", c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedRotatingSpeed  >= 2000 rpm";
		c = ConditionLine.parse(s);
		assertEquals("ratedRotatingSpeed", c.getFieldName());
		assertSame(ComparisonOperator.GTE, c.getOperator());
		assertEquals("2000 rpm", c.getValue());
		assertEquals("2000", c.getNumberValue());
		assertEquals("rpm", c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedTorque<18.1Ncm";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(ComparisonOperator.LT, c.getOperator());
		assertEquals("18.1Ncm", c.getValue());
		assertEquals("18.1", c.getNumberValue());
		assertEquals("Ncm", c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedTorque < 18.1Nm";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(ComparisonOperator.LT, c.getOperator());
		assertEquals("18.1Nm", c.getValue());
		assertEquals("18.1", c.getNumberValue());
		assertEquals("Nm", c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedRotatingSpeed<=4000rpm";
		c = ConditionLine.parse(s);
		assertEquals("ratedRotatingSpeed", c.getFieldName());
		assertSame(ComparisonOperator.LTE, c.getOperator());
		assertEquals("4000rpm", c.getValue());
		assertEquals("4000", c.getNumberValue());
		assertEquals("rpm", c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedRotatingSpeed <=  4000  rpm";
		c = ConditionLine.parse(s);
		assertEquals("ratedRotatingSpeed", c.getFieldName());
		assertSame(ComparisonOperator.LTE, c.getOperator());
		assertEquals("4000  rpm", c.getValue());
		assertEquals("4000", c.getNumberValue());
		assertEquals("rpm", c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "rotorType!=EXTERNAL";
		c = ConditionLine.parse(s);
		assertEquals("rotorType", c.getFieldName());
		assertSame(ComparisonOperator.NE, c.getOperator());
		assertEquals("EXTERNAL", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "rotorType != EXTERNAL";
		c = ConditionLine.parse(s);
		assertEquals("rotorType", c.getFieldName());
		assertSame(ComparisonOperator.NE, c.getOperator());
		assertEquals("EXTERNAL", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "name CONTAINS BLDC";
		c = ConditionLine.parse(s);
		assertEquals("name", c.getFieldName());
		assertSame(ComparisonOperator.CONTAINS, c.getOperator());
		assertEquals("BLDC", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "productNo STARTWITH   ME";
		c = ConditionLine.parse(s);
		assertEquals("productNo", c.getFieldName());
		assertSame(ComparisonOperator.STARTWITH, c.getOperator());
		assertEquals("ME", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "productNo   ENDWITH YS100";
		c = ConditionLine.parse(s);
		assertEquals("productNo", c.getFieldName());
		assertSame(ComparisonOperator.ENDWITH, c.getOperator());
		assertEquals("YS100", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "productNo   LIKE YS";
		c = ConditionLine.parse(s);
		assertEquals("productNo", c.getFieldName());
		assertSame(ComparisonOperator.LIKE, c.getOperator());
		assertEquals("YS", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "deleted IS true";
		c = ConditionLine.parse(s);
		assertEquals("deleted", c.getFieldName());
		assertSame(ComparisonOperator.IS, c.getOperator());
		assertEquals("true", c.getValue());
		assertNull(c.getNumberValue());
		assertNull(c.getUnit());
		assertNull(c.getValue2());
		assertNull(c.getNumberValue2());
		
		s = "ratedTorque BETWEEN 0.55 AND 9.80Nm";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(ComparisonOperator.BETWEEN, c.getOperator());
		assertEquals("0.55", c.getValue());
		assertEquals("0.55", c.getNumberValue());
		assertEquals("9.80Nm", c.getValue2());
		assertEquals("9.80", c.getNumberValue2());
		assertEquals("Nm", c.getUnit());
		
		s = "ratedTorque BETWEEN 0.55 AND 9.80 Nm ";
		c = ConditionLine.parse(s);
		assertEquals("ratedTorque", c.getFieldName());
		assertSame(ComparisonOperator.BETWEEN, c.getOperator());
		assertEquals("0.55", c.getValue());
		assertEquals("0.55", c.getNumberValue());
		assertEquals("9.80 Nm", c.getValue2());
		assertEquals("9.80", c.getNumberValue2());
		assertEquals("Nm", c.getUnit());
	}

}
