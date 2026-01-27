package com.exmek.core.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableListDataResponse<T> {

	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalPages;
	private Integer totalElementsOfAllPages;
	private Integer totalElementsOfCurrPage;
	private List<T> data;
}
