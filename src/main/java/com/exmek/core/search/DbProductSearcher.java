package com.exmek.core.search;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import com.exmek.commons.expr.LogicalOperator;
import com.exmek.core.model.AbstractProduct;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.repository.BaseProductRepository;
import com.exmek.core.rest.ConditionClause;
import com.exmek.core.rest.PageableListDataResponse;
import com.exmek.core.utils.ContentUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DbProductSearcher {

	public <T extends AbstractProductEntity, M extends AbstractProduct> PageableListDataResponse<M> search(
			BaseProductRepository<T> productRepository,
			ConditionClause conditionClause,
			BiFunction<Root<T>, CriteriaBuilder, Pair<Predicate, LogicalOperator>> fAdditionalCondition,
			Integer pageNumber, Integer pageSize,
			Function<T, M> entityToModelMapper,
			Map<String, Set<Object>> dataAvailableUnitsOfFieldNames) {

		PageableListDataResponse<M> dataResponse = new PageableListDataResponse<>();
		
		log.info("Searching product with query {} and pageNumber {}, pageSize {} ", conditionClause, pageNumber, pageSize);
		Specification<T> jpaSpec = (root, query, builder) -> {
			Predicate pConditions = JPAUtils.buildPredicate(builder, root, conditionClause, dataAvailableUnitsOfFieldNames);
			if (fAdditionalCondition != null) {
				Pair<Predicate, LogicalOperator> pAdditionalCondition = fAdditionalCondition.apply(root, builder);
				if (pAdditionalCondition != null) {
					Predicate additionalCondition = pAdditionalCondition.getFirst();
					LogicalOperator op = pAdditionalCondition.getSecond();
					if (additionalCondition != null) {
						if (pConditions == null) {
							return additionalCondition;
						}
						if (op == null || op == LogicalOperator.AND) {
							return builder.and(pConditions, additionalCondition);
						} else if (op == LogicalOperator.OR) {
							return builder.or(pConditions, additionalCondition);
						}
					}
				}
			}
			return pConditions;
		};
		List<T> entities = null;
		if (pageNumber == null || pageSize == null) {
			entities = productRepository.findAll(jpaSpec, Sort.by(AbstractProductEntity.FIELD_NAME_MODEL));
		} else {
			Page<T> page = productRepository.findAll(jpaSpec, 
					PageRequest.of(pageNumber, pageSize, Sort.by(AbstractProductEntity.FIELD_NAME_MODEL)));
			entities = page.getContent();
			ContentUtils.populatePageableListDataResponse(dataResponse, page);
		}
		if (entities != null) {
			List<M> products = entities.stream()
					.map(entity -> entityToModelMapper.apply(entity))
					.collect(Collectors.toList());
			dataResponse.setData(products);
		}
		return dataResponse;
	}

}
