package com.exmek.core.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exmek.commons.expr.LogicalOperator;
import com.exmek.commons.expr.RelationalOperator;
import com.exmek.core.rest.ConditionClause;
import com.exmek.core.rest.ConditionLine;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class JPAUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JPAUtils.class);

	public static Number getNumberValue(Class<? extends Object> fieldType, String strValue) {
		Number nValue = null;
		if (Long.class == fieldType) {
			nValue = Long.valueOf(strValue);
		} else if (Integer.class == fieldType) {
			nValue = Integer.valueOf(strValue);
		} else if (Double.class == fieldType)  {
			nValue = Double.valueOf(strValue);
		} else if (Float.class == fieldType) {
			nValue = Float.valueOf(strValue);
		} else {
			nValue = new BigDecimal(strValue);
		}
		return nValue;
	}

	@SuppressWarnings("unchecked")
	static <N extends Number> Predicate buildNumberBetween(CriteriaBuilder builder, Path<N> attrPath, Class<? extends Object> fieldType,
			Number nValue1, Number nValue2) {
		
		if (Long.class == fieldType) {
			return builder.between((Path<Long>) attrPath, (Long) nValue1, (Long) nValue2);
		} else if (Integer.class == fieldType) {
			return builder.between((Path<Integer>) attrPath, (Integer) nValue1, (Integer) nValue2);
		} else if (Double.class == fieldType)  {
			return builder.between((Path<Double>) attrPath, (Double) nValue1, (Double) nValue2);
		} else if (Float.class == fieldType) {
			return builder.between((Path<Float>) attrPath, (Float) nValue1, (Float) nValue2);
		} else {
			return builder.between((Path<BigDecimal>) attrPath, (BigDecimal) nValue1, (BigDecimal) nValue2);
		}
	}

	public static <N extends Number> Predicate buildPredicateForNumber(CriteriaBuilder builder,
			Path<N> attrPath, Class<? extends Object> fieldType, ConditionLine cl) {
		Number nValue = getNumberValue(fieldType, cl.getValue());
		if (RelationalOperator.EQ == cl.getOperator()) {
			return builder.equal(attrPath, nValue);
		} else if (RelationalOperator.GT == cl.getOperator()) {
			return builder.gt(attrPath, nValue);
		} else if (RelationalOperator.GTE == cl.getOperator()) {
			return builder.ge(attrPath, nValue);
		} else if (RelationalOperator.LT == cl.getOperator()) {
			return builder.lt(attrPath, nValue);
		} else if (RelationalOperator.LTE == cl.getOperator()) {
			return builder.le(attrPath, nValue);
		} else if (RelationalOperator.NE == cl.getOperator()) {
			return builder.notEqual(attrPath, nValue);
		} else if (RelationalOperator.BETWEEN == cl.getOperator()) {
			return buildNumberBetween(builder, attrPath, fieldType, nValue, getNumberValue(fieldType, cl.getValue2()));
		}
		return null;
	}

	public static Predicate buildPredicateForString(CriteriaBuilder builder, Path<String> attrPath, ConditionLine cl) {
		if (RelationalOperator.EQ == cl.getOperator()) {
			return builder.equal(attrPath, cl.getValue());
		} else if (RelationalOperator.GT == cl.getOperator()) {
			return builder.greaterThan(attrPath, cl.getValue());
		} else if (RelationalOperator.GTE == cl.getOperator()) {
			return builder.greaterThanOrEqualTo(attrPath, cl.getValue());
		} else if (RelationalOperator.LT == cl.getOperator()) {
			return builder.lessThan(attrPath, cl.getValue());
		} else if (RelationalOperator.LTE == cl.getOperator()) {
			return builder.lessThanOrEqualTo(attrPath, cl.getValue());
		} else if (RelationalOperator.NE == cl.getOperator()) {
			return builder.notEqual(attrPath, cl.getValue());
		} else if (RelationalOperator.LIKE == cl.getOperator()) {
			return builder.like(attrPath, cl.getValue());
		} else if (RelationalOperator.CONTAINS == cl.getOperator()) {
			return builder.like(attrPath, "%" + cl.getValue() + "%");
		} else if (RelationalOperator.STARTWITH == cl.getOperator()) {
			return builder.like(attrPath, cl.getValue() + "%");
		} else if (RelationalOperator.ENDWITH == cl.getOperator()) {
			return builder.like(attrPath, "%" + cl.getValue());
		} else if (RelationalOperator.BETWEEN == cl.getOperator()) {
			return builder.between(attrPath, cl.getValue(), cl.getValue2());
		}
		return null;
	}

	public static Predicate buildConjunctPredicate(CriteriaBuilder builder,
			List<Predicate> predicates, LogicalOperator operator) {
		if (operator == LogicalOperator.AND) {
			return builder.and(predicates.toArray(new Predicate[] {}));
		} else if (operator == LogicalOperator.OR) {
			return builder.or(predicates.toArray(new Predicate[] {}));
		} else if (operator == LogicalOperator.NOT) {
			List<Predicate> notPredicates = new ArrayList<>();
			for (Predicate p : predicates) {
				notPredicates.add(p.not());
			}
			return builder.and(notPredicates.toArray(new Predicate[] {}));
		} else {
			//default to AND if 'operator' not specified
			return builder.and(predicates.toArray(new Predicate[] {}));
		}
	}

	public static <T> Predicate buildPredicate(CriteriaBuilder builder, Root<T> root, ConditionClause conditionClause) {
		if (conditionClause == null) {
			return null;
		}
		List<String> conditions = conditionClause.getConditions();
		List<ConditionClause> subConditionClauses = conditionClause.getSubConditionClauses();
		if ((conditions == null || conditions.isEmpty()) && (subConditionClauses == null || subConditionClauses.isEmpty())) {
			return null;
		}
		List<Predicate> predicates = new ArrayList<>();
		if (conditions != null) {
			for (String condition : conditions) {
				Predicate predicate = null;
				ConditionLine cl = ConditionLine.parse(condition);
				Class<? extends Object> fieldType = root.get(cl.getFieldName()).getJavaType();
				if (Number.class.isAssignableFrom(fieldType)) {
					predicate = JPAUtils.buildPredicateForNumber(builder, root.get(cl.getFieldName()), fieldType, cl);
				} else if (Boolean.class == fieldType) {
					if (cl.getOperator() == RelationalOperator.EQ || cl.getOperator() == RelationalOperator.IS) {
						Boolean booleanVal = Boolean.valueOf(cl.getValue());
						if (booleanVal == true) {
							predicate = builder.isTrue(root.get(cl.getFieldName()));
						} else {
							predicate = builder.isFalse(root.get(cl.getFieldName()));
						}
					} else {
						logger.error("Unable to parse boolean condition {} ", condition);
					}
				} else {
					predicate = JPAUtils.buildPredicateForString(builder, root.get(cl.getFieldName()), cl);
				}
				if (predicate != null) {
					predicates.add(predicate);
				}
			}
		}
		if (subConditionClauses != null) {
			for (ConditionClause subCond : subConditionClauses) {
				Predicate subPredicate = buildPredicate(builder, root, subCond);
				if (subPredicate != null) {
					predicates.add(subPredicate);
				}
			}
		}
		return JPAUtils.buildConjunctPredicate(builder, predicates, conditionClause.getOperator());
	}
}
