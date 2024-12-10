package com.exmek.core.rest;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.exmek.commons.expr.LogicalOperator;
import com.exmek.commons.utils.ReflectionUtils;
import com.exmek.core.annotation.Searchable;
import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.model.AbstractProduct;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.repository.BaseProductRepository;
import com.exmek.core.resource.ResourceContext;
import com.exmek.core.service.ProductService;
import com.exmek.core.utils.ExmekUtils;
import com.exmek.core.utils.RelationalOperatorUtils;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class BaseProductRestController<T extends AbstractProductEntity, M extends AbstractProduct> implements ProductService<M> {

	public static final String PARAM_NAME_SERIES			= "series";

	public static final String QRY_PARAM_NAME_PAGE_NUMBER	= "pageNumber";
	
	public static final String QRY_PARAM_NAME_PAGE_SIZE		= "pageSize";

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected ResourceContext resourceContext;
	
	@Autowired
	protected AppConfigProvider appConfigProvider;

	protected List<FieldMetaCriterion> fieldMetaCriteria;
	
	protected abstract Class<T> getEntityClass();

	protected abstract BaseProductRepository<T> getProductRepository();
	
	protected abstract M mapEntityToModel(T entity, boolean comprehensiveMapping);

	protected abstract List<String> getSearchMetaCriteriaFields();

	@PostConstruct
	protected void initFieldMetaCriteria() {
		List<String> searchMetaFieldNames = getSearchMetaCriteriaFields();
		if (!ObjectUtils.isEmpty(searchMetaFieldNames)) {
			this.fieldMetaCriteria = createFieldMetaCriteriaByConfig(searchMetaFieldNames);
		} else {
			this.fieldMetaCriteria = createFieldMetaCriteriaByAnnotation();
		}
	}

	List<FieldMetaCriterion> createFieldMetaCriteriaByConfig(List<String> searchMetaFieldNames) {
		List<FieldMetaCriterion> fieldMetaCriteria = new ArrayList<>();
		
		Class<?> clazz = getEntityClass();
		if (clazz == null) {
			throw new RuntimeException("Unable to initialize 'fieldMetaCriteria' as 'entityClass' is not defined.");
		}
		Map<String, Field> fieldsMap = collectFields(clazz);
		for (String searchMetaFieldName : searchMetaFieldNames) {
			if (!fieldsMap.containsKey(searchMetaFieldName)) {
				continue;
			}
			FieldMetaCriterion c = createFieldMetaCriterion(searchMetaFieldName, fieldsMap);
			fieldMetaCriteria.add(c);
		}
		
		return fieldMetaCriteria;
	}
	
	List<FieldMetaCriterion> createFieldMetaCriteriaByAnnotation() {
		List<FieldMetaCriterion> fieldMetaCriteria = new ArrayList<>();
		
		Class<?> clazz = getEntityClass();
		if (clazz == null) {
			throw new RuntimeException("Unable to initialize 'fieldMetaCriteria' as 'entityClass' is not defined.");
		}
		Map<String, Field> fieldsMap = collectFields(clazz);
		
		clazz = getEntityClass();
		while (clazz != null && clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (!field.isAnnotationPresent(Searchable.class)) {
					continue;
				}
				FieldMetaCriterion c = createFieldMetaCriterion(field.getName(), fieldsMap);
				fieldMetaCriteria.add(c);
			}
			clazz = clazz.getSuperclass();
		}
		return fieldMetaCriteria;
	}

	private Map<String, Field> collectFields(Class<?> clazz) {
		Map<String, Field> fieldsMap = new HashMap<>();
		while (clazz != null && clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers()) && !Modifier.isVolatile(field.getModifiers())) {
					fieldsMap.put(field.getName(), field);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return fieldsMap;
	}

	private FieldMetaCriterion createFieldMetaCriterion(String searchMetaFieldName, Map<String, Field> fieldsMap) {
		Field field = fieldsMap.get(searchMetaFieldName);
		FieldMetaCriterion c = new FieldMetaCriterion();
		String fieldName = field.getName();
		c.setFieldName(fieldName);
		c.setDisplayName(ExmekUtils.fieldNameToDisplayName(field.getName()));
		c.setType(field.getType().getSimpleName());
		String unitFieldName = field.getName() + "Unit";
		if (fieldsMap.containsKey(unitFieldName)) {
			c.setUnitFieldName(unitFieldName);
			@SuppressWarnings("unchecked")
			List<String> units = (List<String>) ReflectionUtils.readValueFromMethod("find" + StringUtils.capitalize(unitFieldName) + "s", getProductRepository());
			if (!ObjectUtils.isEmpty(units)) {
				if (units.size() > 1) {
					logger.warn("There are multiple different units used for unit field {} ", unitFieldName);
				}
				c.setUnit(units.get(0)); //supposed to have only one unit
			}
		}
		boolean isNumber = Number.class.isAssignableFrom(field.getType());
		c.setIsNumber(isNumber);
		if (isNumber) {
			c.setSupportedOperators(RelationalOperatorUtils.getNumberSupportedRelationalOperators());
			c.setMinValue((Number) ReflectionUtils.readValueFromMethod("findMin" + StringUtils.capitalize(fieldName), getProductRepository()));
			c.setMaxValue((Number) ReflectionUtils.readValueFromMethod("findMax" + StringUtils.capitalize(fieldName), getProductRepository()));
		}
		return c;
	}

	@Override
	public SearchMetaCriteriaResponse getSearchMetaCriteria() {
		SearchMetaCriteriaResponse response = new SearchMetaCriteriaResponse();
		response.setDomain(getEntityClass().getSimpleName());
		response.setFieldMetaCriteria(fieldMetaCriteria);
		return response;
	}

	protected M mapEntityToModel(T entity) {
		return mapEntityToModel(entity, true);
	}

	public M getProduct(String idOrModel) {
		if (idOrModel == null || idOrModel.isBlank()) {
			return null;
		}
		if (idOrModel.matches("[0-9]+")) {
			return getById(Long.valueOf(idOrModel));
		} else {
			return getByModel(idOrModel);
		}
	}
	
	@Override
	public M getById(Long id) {
		Optional<T> opEntity = getProductRepository().findById(id);
		if (opEntity.isPresent()) {
			return mapEntityToModel(opEntity.get());
		} else {
			return null;
		}
	}

	@Override
	public M getByModel(String model) {
		Optional<T> opEntity = getProductRepository().findByModel(model);
		if (opEntity.isPresent()) {
			return mapEntityToModel(opEntity.get());
		} else {
			return null;
		}
	}

	/**
	 * <pre>
	 * Search with given list of criteria that act as AND.
	 * The format of criteria is "fieldName logicalOperator comparingValue."
	 * Criteria examples:
	 * 	ratedVoltage=24
	 * 	ratedRotatingSpeed>=2000
	 * 	ratedRotatingSpeed<=4000
	 * </pre>
	 */
	@Override
	public List<M> search(ConditionClause conditionClause) {
		return searchWith(conditionClause, null, null).getData();
	}

	protected PageableListDataResponse<M> searchWith(ConditionClause conditionClause,
			Integer pageNumber, Integer pageSize) {
		return searchWith(conditionClause, null, pageNumber, pageSize);
	}

	protected PageableListDataResponse<M> searchWith(ConditionClause conditionClause,
			List<Pair<String, Object>> additionalFieldMatching,
			Integer pageNumber, Integer pageSize) {
		return searchBy(conditionClause, (root, builder) -> {
			if (!ObjectUtils.isEmpty(additionalFieldMatching)) {
				List<Predicate> predicates = additionalFieldMatching.stream()
						.filter(p -> !ObjectUtils.isEmpty(p.getFirst()) && !ObjectUtils.isEmpty(p.getSecond()))
						.map(p -> builder.equal(root.get(p.getFirst()), p.getSecond()))
						.collect(Collectors.toList());
				Predicate combinedPredicate = JPAUtils.buildConjunctPredicate(builder, predicates, LogicalOperator.AND);
				return Pair.of(combinedPredicate, LogicalOperator.AND);
			} else {
				return null;
			}
		}, pageNumber, pageSize);
	}

	protected PageableListDataResponse<M> searchBy(ConditionClause conditionClause,
			BiFunction<Root<T>, CriteriaBuilder, Pair<Predicate, LogicalOperator>> funcAdditionalCondition,
			Integer pageNumber, Integer pageSize) {

		PageableListDataResponse<M> dataResponse = new PageableListDataResponse<>();
		
		Specification<T> jpaSpec = (root, query, builder) -> {
			Predicate pConditions = JPAUtils.buildPredicate(builder, root, conditionClause);
			if (funcAdditionalCondition != null) {
				Pair<Predicate, LogicalOperator> pAdditionalCondition = funcAdditionalCondition.apply(root, builder);
				if (pAdditionalCondition != null) {
					Predicate additionalCondition = pAdditionalCondition.getFirst();
					LogicalOperator op = pAdditionalCondition.getSecond();
					if (additionalCondition != null) {
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
			entities = getProductRepository().findAll(jpaSpec, Sort.by(AbstractProductEntity.FIELD_NAME_MODEL));
		} else {
			Page<T> page = getProductRepository().findAll(jpaSpec, PageRequest.of(pageNumber, pageSize, Sort.by(AbstractProductEntity.FIELD_NAME_MODEL)));
			entities = page.getContent();
			populatePageableListDataResponse(dataResponse, page);
		}
		if (entities != null) {
			List<M> products = entities.stream()
					.map(entity -> mapEntityToModel(entity, false))
					.collect(Collectors.toList());
			dataResponse.setData(products);
		}
		return dataResponse;
	}
	
	protected <MM, TT> void populatePageableListDataResponse(PageableListDataResponse<MM> dataResponse, Page<TT> page) {
		dataResponse.setPageNumber(page.getNumber());
		dataResponse.setPageSize(page.getSize());
		dataResponse.setTotalPages(page.getTotalPages());
		dataResponse.setTotalElementsOfAllPages(Long.valueOf(page.getTotalElements()).intValue());
		dataResponse.setTotalElementsOfCurrPage(page.getNumberOfElements());
	}

	protected String getModelDisplayName() {
		return "Model";
	}
}
