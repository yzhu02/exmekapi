package com.exmek.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExmekUtilsTest {

	@Test
	void testFieldNameToDisplayName() {
		Assertions.assertEquals("Rated Voltage", ExmekUtils.fieldNameToDisplayName("ratedVoltage"));
		Assertions.assertEquals("Rated Voltage", ExmekUtils.fieldNameToDisplayName("ratedVoltage"));
		Assertions.assertEquals("Rated Rotating Speed", ExmekUtils.fieldNameToDisplayName("ratedRotatingSpeed"));
		Assertions.assertEquals("Rated DC Voltage", ExmekUtils.fieldNameToDisplayName("ratedDCVoltage"));
		Assertions.assertEquals("Rated VDC", ExmekUtils.fieldNameToDisplayName("ratedVDC"));
	}

}
