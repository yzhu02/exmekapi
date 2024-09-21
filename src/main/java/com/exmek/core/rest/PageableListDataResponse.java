package com.exmek.core.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageableListDataResponse<T> {

	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalPages;
	private Integer totalElementsOfAllPages;
	private Integer totalElementsOfCurrPage;
	private List<T> data;
}
