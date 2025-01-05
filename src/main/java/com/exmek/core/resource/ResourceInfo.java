package com.exmek.core.resource;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResourceInfo {

	private String name;

	private String path;
	
	private Long size;
}
