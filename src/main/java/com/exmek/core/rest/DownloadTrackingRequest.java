package com.exmek.core.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DownloadTrackingRequest {

	private String downloadLink;

	@NotNull
	private String contactEmail;

	private String contactName;
	
	private String contactPhone;
	
	private String company;
	
}
