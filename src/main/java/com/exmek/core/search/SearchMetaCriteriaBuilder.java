package com.exmek.core.search;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.apache.commons.lang3.ObjectUtils;

import com.exmek.commons.utils.MiscUtils;
import com.exmek.core.annotation.Searchable;
import com.exmek.core.commons.model.Range;
import com.exmek.core.helper.MetaCriteriaKey;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.rest.FieldMetaCriterion;
import com.exmek.core.utils.RelationalOperatorUtils;

public class SearchMetaCriteriaBuilder {

	public List<FieldMetaCriterion> createFieldMetaCriteria(MetaCriteriaKey criteriaKey, 
			List<String> searchMetaFieldNames, Class<?> entityClass, 
			BiFunction<String, MetaCriteriaKey, Map<?, Range<? extends Number>>> minMaxByUnitsLoader) {

		if (ObjectUtils.isNotEmpty(searchMetaFieldNames)) {
			return createFieldMetaCriteriaByConfig(criteriaKey, searchMetaFieldNames, entityClass, minMaxByUnitsLoader);
		} else {
			return createFieldMetaCriteriaByAnnotation(criteriaKey, entityClass, minMaxByUnitsLoader);
		}
	}

	List<FieldMetaCriterion> createFieldMetaCriteriaByConfig(MetaCriteriaKey criteriaKey, 
			List<String> searchMetaFieldNames, Class<?> entityClass,
			BiFunction<String, MetaCriteriaKey, Map<?, Range<? extends Number>>> minMaxByUnitsLoader) {

		List<FieldMetaCriterion> fieldMetaCriteria = new ArrayList<>();
		if (entityClass == null) {
			throw new RuntimeException("Unable to initialize 'fieldMetaCriteria' as 'entityClass' is not defined.");
		}
		Map<String, Field> fieldsMap = collectFields(entityClass);
		for (String searchMetaFieldName : searchMetaFieldNames) {
			if (!fieldsMap.containsKey(searchMetaFieldName)) {
				continue;
			}
			FieldMetaCriterion c = createFieldMetaCriterion(searchMetaFieldName, fieldsMap, criteriaKey, minMaxByUnitsLoader);
			fieldMetaCriteria.add(c);
		}
		
		return fieldMetaCriteria;
	}
	
	List<FieldMetaCriterion> createFieldMetaCriteriaByAnnotation(MetaCriteriaKey criteriaKey, 
			Class<?> entityClass,
			BiFunction<String, MetaCriteriaKey, Map<?, Range<? extends Number>>> minMaxByUnitsLoader) {

		List<FieldMetaCriterion> fieldMetaCriteria = new ArrayList<>();
		if (entityClass == null) {
			throw new RuntimeException("Unable to initialize 'fieldMetaCriteria' as 'entityClass' is not defined.");
		}
		Map<String, Field> fieldsMap = collectFields(entityClass);
		Class<?> clazz = entityClass;
		while (clazz != null && clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (!field.isAnnotationPresent(Searchable.class)) {
					continue;
				}
				FieldMetaCriterion c = createFieldMetaCriterion(field.getName(), fieldsMap, criteriaKey, minMaxByUnitsLoader);
				fieldMetaCriteria.add(c);
			}
			clazz = clazz.getSuperclass();
		}
		return fieldMetaCriteria;
	}

	Map<String, Field> collectFields(Class<?> clazz) {
		Map<String, Field> fieldsMap = new HashMap<>();
		while (clazz != null && clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isStatic(field.getModifiers()) 
						|| Modifier.isTransient(field.getModifiers()) 
						|| Modifier.isVolatile(field.getModifiers())) {
					continue;
				}
				fieldsMap.put(field.getName(), field);
			}
			clazz = clazz.getSuperclass();
		}
		return fieldsMap;
	}

	private FieldMetaCriterion createFieldMetaCriterion(String searchMetaFieldName, 
			Map<String, Field> fieldsMap, 
			MetaCriteriaKey criteriaKey, 
			BiFunction<String, MetaCriteriaKey, Map<?, Range<? extends Number>>> minMaxByUnitsLoader) {

		Field field = fieldsMap.get(searchMetaFieldName);
		FieldMetaCriterion c = new FieldMetaCriterion();
		String fieldName = field.getName();
		c.setFieldName(fieldName);
		c.setDisplayName(MiscUtils.fieldNameToDisplayName(field.getName()));
		c.setType(field.getType().getSimpleName());
		String unitFieldName = field.getName() + AbstractProductEntity.UNIT_FIELD_SUFFIX;
		if (fieldsMap.containsKey(unitFieldName)) {
			c.setUnitFieldName(unitFieldName);
		}
		boolean isNumber = Number.class.isAssignableFrom(field.getType());
		c.setIsNumber(isNumber);
		if (isNumber) {
			c.setMinMaxByUnits(minMaxByUnitsLoader.apply(fieldName, criteriaKey));
			c.setSupportedOperators(RelationalOperatorUtils.getNumberSupportedRelationalOperators());
		}
		return c;
	}

}
