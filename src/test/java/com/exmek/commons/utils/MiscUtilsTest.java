package com.exmek.commons.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MiscUtilsTest {

	@Test
	void testFieldNameToDisplayName() {
		Assertions.assertEquals("Rated Voltage", MiscUtils.fieldNameToDisplayName("ratedVoltage", null));
		Assertions.assertEquals("Rated Voltage", MiscUtils.fieldNameToDisplayName("ratedVoltage", null));
		Assertions.assertEquals("Rated Rotating Speed", MiscUtils.fieldNameToDisplayName("ratedRotatingSpeed", null));
		Assertions.assertEquals("Rated DC Voltage", MiscUtils.fieldNameToDisplayName("ratedDCVoltage", null));
		Assertions.assertEquals("Rated VDC", MiscUtils.fieldNameToDisplayName("ratedVDC", null));
	}

	@Test
	void testExtractFirstNumber() {
		Assertions.assertEquals(57, MiscUtils.extractFirstNumber("MB057GA"));
		Assertions.assertEquals(32, MiscUtils.extractFirstNumber("ME032RS100-SI0020"));
		Assertions.assertEquals(23, MiscUtils.extractFirstNumber("MPC023"));
		Assertions.assertEquals(66, MiscUtils.extractFirstNumber("66-FRAME-PMDC_BLDC-GEAR-MOTOR"));
		Assertions.assertNull(MiscUtils.extractFirstNumber(null));
		Assertions.assertNull(MiscUtils.extractFirstNumber(""));
		Assertions.assertNull(MiscUtils.extractFirstNumber("SLS"));
	}
}
