package com.exmek.core.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class InquiryRequest {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	public static class Inquiry {

		@NotNull
		private String contactName;
		
		@NotNull
		private String contactEmail;
		
		private String contactPhone;
		
		private String refModel;
		
		private Integer quantity;
		
		private String content;
		
		private String refLink;
	}

	@NotNull
	private Inquiry inquiry;
}
