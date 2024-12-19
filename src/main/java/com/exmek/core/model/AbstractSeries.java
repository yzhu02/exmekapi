package com.exmek.core.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractSeries extends AbstractModel {

	private String series;
	
	private String displayName;
	
	private String description;
	
	private Map<String, String> technicalData;
}
