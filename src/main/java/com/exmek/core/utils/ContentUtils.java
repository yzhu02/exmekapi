package com.exmek.core.utils;

import org.springframework.data.domain.Page;

import com.exmek.core.rest.PageableListDataResponse;

public class ContentUtils {

	private ContentUtils() {
	}

	public static <M, T> void populatePageableListDataResponse(PageableListDataResponse<M> dataResponse, Page<T> page) {
		dataResponse.setPageNumber(page.getNumber());
		dataResponse.setPageSize(page.getSize());
		dataResponse.setTotalPages(page.getTotalPages());
		dataResponse.setTotalElementsOfAllPages(Long.valueOf(page.getTotalElements()).intValue());
		dataResponse.setTotalElementsOfCurrPage(page.getNumberOfElements());
	}

}
