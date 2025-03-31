package com.exmek.core.model;

import com.exmek.core.commons.enums.InquiryStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class Inquiry extends AbstractModel {

	private String contactName;
	
	private String contactEmail;
	
	private String contactPhone;
		
	private String refModel;
	
	private Integer quantity;
	
	private String message;
	
	private String refLink;
	
	private String clientIpAddress;
	
	private String clientCountryOrRegion;

	private InquiryStatus status;

}
