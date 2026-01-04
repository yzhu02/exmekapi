package com.exmek.core.persistence;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.NumberUtils;

import com.exmek.commons.expr.LogicalOperator;
import com.exmek.commons.expr.RelationalOperator;
import com.exmek.commons.function.HexaFunction;
import com.exmek.commons.function.QuadFunction;
import com.exmek.commons.utils.ReflectionUtils;
import com.exmek.core.commons.model.Range;
import com.exmek.core.commons.model.UnitBaseValuable;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.rest.ConditionClause;
import com.exmek.core.rest.ConditionLine;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JPAUtils {

	/**
	 * 
	 * Build {@link Predicate} for given {@link ConditionClause} input and pre-cached per-fieldname units.
	 * The pre-cached units is provided in case it's already available for performance optimization, 
	 * otherwise the units will be resolved from the Enum class and add to where clause if {@link ConditionClause} contains unit.
	 * 
	 * @param <T>
	 * @param builder
	 * @param root
	 * @param conditionClause
	 * @param dataAvailableUnitsOfFieldNames
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Predicate buildPredicate(CriteriaBuilder builder, Root<T> root, 
			ConditionClause conditionClause, 
			Map<String, Set<Object>> dataAvailableUnitsOfFieldNames) {

		if (conditionClause == null) {
			return null;
		}
		List<String> conditions = conditionClause.getConditions();
		List<ConditionClause> subConditionClauses = conditionClause.getSubConditionClauses();
		if (ObjectUtils.isEmpty(conditions) && ObjectUtils.isEmpty(subConditionClauses)) {
			return null;
		}
		if (log.isDebugEnabled()) {
			log.debug("Building query {} ", conditionClause);
		}
		List<Predicate> predicates = new ArrayList<>();
		if (conditions != null) {
			for (String condition : conditions) {
				Predicate predicate = null;
				ConditionLine cl = ConditionLine.parse(condition);
				Class<? extends Object> fieldType = root.get(cl.getFieldName()).getJavaType();
				if (Number.class.isAssignableFrom(fieldType)) {
					if (StringUtils.isNotEmpty(cl.getUnit())) {
						Enum<?>[] units = null;
						if (dataAvailableUnitsOfFieldNames != null) {
							Set<Object> unitObjects = dataAvailableUnitsOfFieldNames.get(cl.getFieldName());
							if (unitObjects != null) {
								units = unitObjects.stream().filter(u -> u.getClass().isEnum()).toArray(Enum<?>[]::new);
							}
						}
						predicate = JPAUtils.buildUnitBasedPredicateForNumber(builder, root, (Class<? extends Number>) fieldType, cl, units);
					} else {
						predicate = JPAUtils.buildPredicateForNumber(builder, root.get(cl.getFieldName()), (Class<? extends Number>) fieldType, cl);
					}
				} else if (Boolean.class == fieldType) {
					if (cl.getOperator() == RelationalOperator.EQ || cl.getOperator() == RelationalOperator.IS) {
						Boolean booleanVal = Boolean.valueOf(cl.getValue());
						if (booleanVal == true) {
							predicate = builder.isTrue(root.get(cl.getFieldName()));
						} else {
							predicate = builder.isFalse(root.get(cl.getFieldName()));
						}
					} else {
						log.error("Unable to parse boolean condition {} ", condition);
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
				Predicate subPredicate = buildPredicate(builder, root, subCond, dataAvailableUnitsOfFieldNames);
				if (subPredicate != null) {
					predicates.add(subPredicate);
				}
			}
		}
		return JPAUtils.buildConjunctPredicate(builder, predicates, conditionClause.getOperator());
	}

	public static <T> Predicate buildUnitBasedPredicateForNumber(CriteriaBuilder builder, Root<T> root, 
			Class<? extends Number> fieldType, ConditionLine cl, Enum<?>[] units) {

		Predicate originPredicate = JPAUtils.buildPredicateForNumber(builder, root.get(cl.getFieldName()), fieldType, cl);
		
		Class<? extends T> entityClass = root.getJavaType();
		String unitFieldName = cl.getFieldName() + AbstractProductEntity.UNIT_FIELD_SUFFIX;
		Field unitField = null;
		try {
			unitField = ReflectionUtils.getField(entityClass, unitFieldName);
		} catch (Exception e) {
			log.error("Unable to find unit field name {} from entity class {} ", unitField, entityClass);
		}
		if (unitField == null) {
			return originPredicate;
		}
		Class<?> unitType = unitField.getType();
		if (!unitType.isEnum()) {
			return originPredicate;
		}
		@SuppressWarnings("unchecked")
		Class<? extends Enum<?>> unitEnumClass = (Class<? extends Enum<?>>) unitType;
		Enum<?> originUnit = ReflectionUtils.readEnumConstant(unitEnumClass, cl.getUnit());
		if (units == null || units.length == 0) {
			units = unitEnumClass.getEnumConstants();
		}
		if (units == null || units.length == 0) {
			return originPredicate;
		}
		if (units.length >= 1) {
			originPredicate = builder.and(originPredicate, builder.equal(root.get(unitFieldName), originUnit));
		}
		if (units.length == 1 && units[0] == originUnit) {
			return originPredicate;
		}
		
		String baseValuePropName = "baseValue";
		double originUnitBaseValue = 1;
		if (originUnit instanceof UnitBaseValuable) {
			originUnitBaseValue = ((UnitBaseValuable) originUnit).getBaseValue();
		} else {
			try {
				originUnitBaseValue = (double) PropertyUtils.getProperty(originUnit, baseValuePropName);
			} catch (Exception ex) {
				log.warn("Unable to read {} from {} ", baseValuePropName, originUnit, ex);
			}
		}
		List<Predicate> combinedPredicates = new ArrayList<>();
		combinedPredicates.add(originPredicate);
		for (Enum<?> unit : units) {
			if (unit == originUnit) {
				continue;
			}
			double uBaseValue = 1;
			if (unit instanceof UnitBaseValuable) {
				uBaseValue = ((UnitBaseValuable) unit).getBaseValue();
			} else {
				try {
					uBaseValue = (double) PropertyUtils.getProperty(unit, baseValuePropName);
				} catch (Exception ex) {
					log.error("Unable to read {} from {} ", baseValuePropName, unit, ex);
					continue;
				}
			}
			ConditionLine clonedCL = cl.clone();
			clonedCL.setUnit(unit.name());
			double unitProportion = originUnitBaseValue / uBaseValue;
			String uNumberValue = calcNumberStringByProportion(cl.getNumberValue(), unitProportion);
			clonedCL.setNumberValue(uNumberValue);
			clonedCL.setValue(uNumberValue);
			if (StringUtils.isNotEmpty(cl.getNumberValue2())) {
				String uNumberValue2 = calcNumberStringByProportion(cl.getNumberValue2(), unitProportion);
				clonedCL.setNumberValue2(uNumberValue2);
				clonedCL.setValue2(uNumberValue2 + unit.name());
			}
			Predicate clonedPredicate = JPAUtils.buildPredicateForNumber(builder, root.get(cl.getFieldName()), fieldType, clonedCL);
			clonedPredicate = builder.and(clonedPredicate, builder.equal(root.get(unitFieldName), unit));
			combinedPredicates.add(clonedPredicate);
		}
		return JPAUtils.buildConjunctPredicate(builder, combinedPredicates, LogicalOperator.OR);
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
			// default to AND if 'operator' not specified
			return builder.and(predicates.toArray(new Predicate[] {}));
		}
	}

	
	static <N extends Number> Predicate buildPredicateForNumber(CriteriaBuilder builder,
			Path<N> attrPath, Class<? extends Number> fieldType, ConditionLine cl) {
		Number nValue = NumberUtils.parseNumber(cl.getNumberValue(), fieldType);
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
			return buildNumberBetween(builder, attrPath, fieldType, nValue, NumberUtils.parseNumber(cl.getNumberValue2(), fieldType));
		}
		return null;
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

	static String calcNumberStringByProportion(String numberString, double proportion) {
		return String.valueOf(new BigDecimal(numberString).multiply(BigDecimal.valueOf(proportion)));
	}

	static Predicate buildPredicateForString(CriteriaBuilder builder, Path<String> attrPath, ConditionLine cl) {
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
			String likeValue = cl.getValue();
			if (likeValue != null) {
				likeValue = likeValue.replace('*', '%');
			}
			return builder.like(attrPath, likeValue);
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

	
	@SuppressWarnings("unchecked")
	public static <U, N extends Number> Map<U, Range<N>> findMinMaxByUnits(MotorCategory.Type type, String category, String series,
			HexaFunction<Integer, MotorCategory.Type, Integer, String, Integer, String, List<Object[]>> delegate) {
		int ignoreType = determineIgnore(type);
		int ignoreCategory = determineIgnore(category);
		int ignoreSeries = determineIgnore(series);
		return delegate.apply(ignoreType, type, ignoreCategory, category, ignoreSeries, series).stream()
				.collect(Collectors.toMap(
						oo -> (U) oo[2],
						oo -> buildMinMaxRange(oo[0], oo[1]))
				);
	}

	@SuppressWarnings("unchecked")
	public static <U, N extends Number> Map<U, Range<N>> findMinMaxByUnits(String category, String series,
			QuadFunction<Integer, String, Integer, String, List<Object[]>> delegate) {
		int ignoreCategory = determineIgnore(category);
		int ignoreSeries = determineIgnore(series);
		return delegate.apply(ignoreCategory, category, ignoreSeries, series).stream()
				.collect(Collectors.toMap(
						oo -> (U) oo[2],
						oo -> buildMinMaxRange(oo[0], oo[1]))
				);
	}
	
	@SuppressWarnings("unchecked")
	public static <U, N extends Number> Map<U, Range<N>> findMinMaxByUnits(String series,
			BiFunction<Integer, String, List<Object[]>> delegate) {
		int ignoreSeries = determineIgnore(series);
		return delegate.apply(ignoreSeries, series).stream()
				.collect(Collectors.toMap(
						oo -> (U) oo[2],
						oo -> buildMinMaxRange(oo[0], oo[1]))
				);
	}
	
	private static int determineIgnore(Object str) {
		return ObjectUtils.isEmpty(str) ? 1 : 0;
	}

	@SuppressWarnings("unchecked")
	private static <N extends Number> Range<N> buildMinMaxRange(Object min, Object max) {
		return Range.<N>builder().min((N) min).max((N) max).build();
	}
}
