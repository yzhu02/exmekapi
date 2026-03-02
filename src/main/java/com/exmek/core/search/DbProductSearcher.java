package com.exmek.core.search;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.exmek.commons.expr.LogicalOperator;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.repository.BaseProductRepository;
import com.exmek.core.rest.ConditionClause;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DbProductSearcher {

	public <T extends AbstractProductEntity> Page<T> search(
			BaseProductRepository<T> productRepository,
			ConditionClause conditionClause,
			BiFunction<Root<T>, CriteriaBuilder, Pair<Predicate, LogicalOperator>> fAdditionalCondition,
			Integer pageNumber, Integer pageSize,
			Map<String, Set<Object>> dataAvailableUnitsOfFieldNames) {

		log.info("Searching product with query {} and pageNumber {}, pageSize {} ", conditionClause, pageNumber, pageSize);
		Specification<T> jpaSpec = buildJpaSpec(conditionClause, fAdditionalCondition, dataAvailableUnitsOfFieldNames);
		return productRepository.findAll(jpaSpec, PageRequest.of(pageNumber, pageSize, Sort.by(AbstractProductEntity.FIELD_NAME_MODEL)));
	}
	
	public <T extends AbstractProductEntity> List<T> search(
			BaseProductRepository<T> productRepository,
			ConditionClause conditionClause,
			BiFunction<Root<T>, CriteriaBuilder, Pair<Predicate, LogicalOperator>> fAdditionalCondition,
			Map<String, Set<Object>> dataAvailableUnitsOfFieldNames) {

		log.info("Searching product with query {} ", conditionClause);
		Specification<T> jpaSpec = buildJpaSpec(conditionClause, fAdditionalCondition, dataAvailableUnitsOfFieldNames);
		return productRepository.findAll(jpaSpec, Sort.by(AbstractProductEntity.FIELD_NAME_MODEL));
	}
	
	protected <T extends AbstractProductEntity> Specification<T> buildJpaSpec(
			ConditionClause conditionClause,
			BiFunction<Root<T>, CriteriaBuilder, Pair<Predicate, LogicalOperator>> fAdditionalCondition,
			Map<String, Set<Object>> dataAvailableUnitsOfFieldNames) {

		Specification<T> jpaSpec = (root, query, builder) -> {
			Predicate pConditions = JPAUtils.buildPredicate(builder, fn -> root, conditionClause, dataAvailableUnitsOfFieldNames);
			if (fAdditionalCondition != null) {
				Pair<Predicate, LogicalOperator> pAdditionalCondition = fAdditionalCondition.apply(root, builder);
				if (pAdditionalCondition != null) {
					Predicate additionalCondition = pAdditionalCondition.getLeft();
					LogicalOperator op = pAdditionalCondition.getRight();
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
		return jpaSpec;
	}

}
