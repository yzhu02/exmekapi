package com.exmek.core.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchMetaCriteriaResponse {

	private String domain;
	
	private List<FieldMetaCriterion> fieldMetaCriteria;
}
