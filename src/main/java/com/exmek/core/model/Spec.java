package com.exmek.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Spec {

	private String name;
	
	private String unit;
	
	private String value;
	
	private String type;

}
