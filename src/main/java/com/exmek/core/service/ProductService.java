package com.exmek.core.service;

import java.util.List;

import com.exmek.core.rest.ConditionClause;
import com.exmek.core.rest.SearchMetaCriteriaResponse;

public interface ProductService<T> {

	T getById(Long id);
	
	T getByModel(String model);
	
	List<T> search(ConditionClause conditionExpr);

	SearchMetaCriteriaResponse getSearchMetaCriteria();
}
