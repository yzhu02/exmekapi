package com.exmek.core.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class LeadDef extends AbstractModel {

	private String code;
	
	private BigDecimal screwDiameterInch;

	private BigDecimal screwDiameterMM;

	private BigDecimal leadInch;
		
	private BigDecimal leadMM;
	
	private Integer threads;

}
