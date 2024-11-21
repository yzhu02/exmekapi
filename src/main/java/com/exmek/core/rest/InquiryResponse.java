package com.exmek.core.rest;

import com.exmek.core.model.Inquiry;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class InquiryResponse {

	private Inquiry inquiry;

	private String status;
}
