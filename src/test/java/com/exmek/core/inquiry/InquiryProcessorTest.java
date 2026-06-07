package com.exmek.core.inquiry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.exmek.core.model.Inquiry;

class InquiryProcessorTest {

	InquiryProcessor inquiryProcessor = new InquiryProcessor();

	@Test
	void testResolveInquiryContent() {
		Inquiry inquiry = new Inquiry();
		inquiry.setRefLink("http://www.exmek.com/dc-motors/MB057GA100");
		inquiry.setContactName("John Smith");
		inquiry.setContactEmail("john.smith@gmail.com");
		inquiry.setContactPhone("4081112345");
		inquiry.setRefModel("MB057GA100");
		inquiry.setMessage("Hello, I'm looking for a DC motor");
		inquiry.setClientIpAddress("10.120.13.14");
		inquiry.setClientCountryOrRegion("USA");
		String resolvedContent = inquiryProcessor.resolveInquiryContent(inquiry);
		assertTrue(resolvedContent.contains("You have received an inquiry message from [[http://www.exmek.com/dc-motors/MB057GA100]] as following:"));
		assertTrue(resolvedContent.contains("<td th:text=\"John Smith\">John Smith</td>"));
		assertTrue(resolvedContent.contains("<td th:text=\"john.smith@gmail.com\">john.smith@gmail.com</td>"));
		assertTrue(resolvedContent.contains("<td th:text=\"4081112345\">4081112345</td>"));
		assertTrue(resolvedContent.contains("<td th:text=\"MB057GA100\">MB057GA100</td>"));
		assertTrue(resolvedContent.contains("<td th:text=\"Hello, I'm looking for a DC motor\">Hello, I'm looking for a DC motor</td>"));
		assertTrue(resolvedContent.contains("<td th:text=\"10.120.13.14\">10.120.13.14</td>"));
		assertTrue(resolvedContent.contains("<td th:text=\"USA\">USA</td>"));
	}

	@Test
	void testResolveInquiryContent_exception_when_readProperty() {
		Inquiry inquiry = mock(Inquiry.class);
		when(inquiry.getRefLink()).thenThrow(new RuntimeException("Failed to read refLink property"));
		when(inquiry.getContactName()).thenReturn("John Smith");
		when(inquiry.getContactEmail()).thenReturn("john.smith@gmail.com");
		when(inquiry.getContactPhone()).thenReturn("4081112345");
		when(inquiry.getRefModel()).thenThrow(new RuntimeException("Failed to read refModel property"));
		when(inquiry.getMessage()).thenReturn("Hello, I'm looking for a DC motor");
		when(inquiry.getClientIpAddress()).thenReturn("10.120.13.14");
		when(inquiry.getClientCountryOrRegion()).thenReturn("USA");
		String resolvedContent = inquiryProcessor.resolveInquiryContent(inquiry);

		assertTrue(resolvedContent.contains("You have received an inquiry message from [[?]] as following:"));
		assertTrue(resolvedContent.contains("<td th:text=\"John Smith\">John Smith</td>"));
		assertTrue(resolvedContent.contains("<td th:text=\"john.smith@gmail.com\">john.smith@gmail.com</td>"));
		assertTrue(resolvedContent.contains("<td th:text=\"4081112345\">4081112345</td>"));
		assertTrue(resolvedContent.contains("<td th:text=\"?\">?</td>"));
		assertTrue(resolvedContent.contains("<td th:text=\"Hello, I'm looking for a DC motor\">Hello, I'm looking for a DC motor</td>"));
		assertTrue(resolvedContent.contains("<td th:text=\"10.120.13.14\">10.120.13.14</td>"));
		assertTrue(resolvedContent.contains("<td th:text=\"USA\">USA</td>"));
	}

}
