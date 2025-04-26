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


}
