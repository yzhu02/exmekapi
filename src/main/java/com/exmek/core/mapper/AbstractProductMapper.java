package com.exmek.core.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import com.exmek.commons.utils.MiscUtils;
import com.exmek.commons.utils.ReflectionUtils;
import com.exmek.core.commons.model.MeasuredValue;
import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.model.AbstractProduct;
import com.exmek.core.model.Spec;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.resource.ResourceContext;

public abstract class AbstractProductMapper {

	@Autowired
	protected AppConfigProvider appConfigProvider;

	@Autowired
	protected ResourceContext resourceContext;

	protected <T extends AbstractProduct, E extends AbstractProductEntity> T mapProduct(E entity, Supplier<T> modelCreator) {
		T model = modelCreator.get();
//		model.setId(entity.getId());
		model.setSeries(entity.getSeries());
		model.setModel(entity.getModel());
		model.setName(entity.getName());
		model.setDescription(entity.getDescription());
		model.setLength(MeasuredValue.of(entity.getLength(), entity.getLengthUnit()));
		model.setWeight(MeasuredValue.of(entity.getWeight(), entity.getWeightUnit()));
		model.setFrameSize(MeasuredValue.Typed.of(entity.getFrameSize(), entity.getFrameSizeUnit(), entity.getFrameSizeType()));
		model.setNemaSize(entity.getNemaSize());
		return model;
	}

	protected List<Spec> mapAllCombinedSpecs(AbstractProductEntity entity, List<String> configuredFields, Set<String> excludedFieldNames) {
		if (entity == null) {
			return null;
		}
		List<Spec> models = new ArrayList<>();
		Set<String> allFieldNames = new HashSet<>();
		Class<?> entityClass = entity.getClass();
		while (AbstractProductEntity.class.isAssignableFrom(entityClass)) {
			Field[] declaredFields = entityClass.getDeclaredFields();
			for (Field f : declaredFields) {
				if (Modifier.isStatic(f.getModifiers()) || Modifier.isTransient(f.getModifiers()) || Modifier.isVolatile(f.getModifiers())) {
					continue;
				}
				String fieldName = f.getName();
				allFieldNames.add(fieldName);
			}
			entityClass = entityClass.getSuperclass();
		}
		
		List<Triple<String, String, String>> determinedSpecFields = new ArrayList<>();
		for (String baseFieldName : configuredFields) {
			if (excludedFieldNames.contains(baseFieldName)) {
				continue;
			}
			determinedSpecFields.add(combineSpecFieldNames(baseFieldName, allFieldNames));
		}
		
		List<String> baseFieldNames = new ArrayList<>();
		for (String fName : allFieldNames) {
			if (excludedFieldNames.contains(fName)) {
				continue;
			}
			if (!fName.endsWith(AbstractProductEntity.UNIT_FIELD_SUFFIX) && !fName.endsWith(AbstractProductEntity.TYPE_FIELD_SUFFIX)) {
				baseFieldNames.add(fName);
			}
		}
		for (String baseFieldName : baseFieldNames) {
			determinedSpecFields.add(combineSpecFieldNames(baseFieldName, allFieldNames));
		}
		
		for (String fName : allFieldNames) {
			if (excludedFieldNames.contains(fName)) {
				continue;
			}
			determinedSpecFields.add(Triple.of(fName, null, null));
		}
		
		for (Triple<String, String, String> tSpecField : determinedSpecFields) {
			String baseFieldName = tSpecField.getLeft();
			Object specValue = null;
			try {
				specValue = PropertyUtils.getProperty(entity, baseFieldName);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			}
			if (specValue == null) {
				continue;
			}
			String unitValue = readSuffixedFieldValue(entity, tSpecField.getMiddle());
			String typeValue = readSuffixedFieldValue(entity, tSpecField.getRight());
			models.add(createSpec(MiscUtils.fieldNameToDisplayName(baseFieldName), specValue, unitValue, typeValue));
		}
				
		return models;
	}
	
	private Triple<String, String, String> combineSpecFieldNames(String baseFieldName, Set<String> allFieldNames) {
		allFieldNames.remove(baseFieldName);
		String unitFieldName = baseFieldName + AbstractProductEntity.UNIT_FIELD_SUFFIX;
		if (!allFieldNames.remove(unitFieldName)) {
			unitFieldName = null;
		}
		String typeFieldName = baseFieldName + AbstractProductEntity.TYPE_FIELD_SUFFIX;
		if (!allFieldNames.remove(typeFieldName)) {
			typeFieldName = null;
		}
		return Triple.of(baseFieldName, unitFieldName, typeFieldName);
	}
	
	private String readSuffixedFieldValue(AbstractProductEntity entity, String suffixFieldName) {
		if (ObjectUtils.isEmpty(suffixFieldName)) {
			return null;
		}
		Object suffixedObjVal = null;
		try {
			suffixedObjVal = PropertyUtils.getProperty(entity, suffixFieldName);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
		}
		if (suffixedObjVal == null) {
			return null;
		}
		if (suffixedObjVal instanceof Enum) {
			return ReflectionUtils.getEnumJsonValue((Enum<?>) suffixedObjVal);
		} else {
			return String.valueOf(suffixedObjVal);
		}
	}

	private Spec createSpec(String name, Object value, String unit, String type) {
		if (value == null) {
			return null;
		}
		Spec spec = new Spec();
		spec.setName(name);
		if (unit != null) {
			spec.setUnit(unit);
		}
		spec.setValue(String.valueOf(value));
		spec.setType(type);
		return spec;
	}

}
