package com.exmek.core.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

import com.exmek.commons.expr.LogicalOperator;
import com.exmek.core.commons.model.Range;
import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.error.ErrorCode;
import com.exmek.core.error.ValidationException;
import com.exmek.core.helper.MetaCriteriaKey;
import com.exmek.core.mapper.AbstractSeriesMapper;
import com.exmek.core.model.AbstractProduct;
import com.exmek.core.model.AbstractSeries;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.entity.AbstractSeriesEntity;
import com.exmek.core.persistence.repository.BaseProductRepository;
import com.exmek.core.persistence.repository.BaseSeriesRepository;
import com.exmek.core.resource.ResourceContext;
import com.exmek.core.search.DbProductSearcher;
import com.exmek.core.search.SearchMetaCriteriaBuilder;
import com.exmek.core.service.ProductService;
import com.exmek.core.utils.ContentUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class BaseProductRestController<T extends AbstractProductEntity, M extends AbstractProduct, SE extends AbstractSeriesEntity, S extends AbstractSeries> 
implements ProductService<M> {

	public static final String PARAM_NAME_SERIES			= "series";

	public static final String QRY_PARAM_NAME_PAGE_NUMBER	= "pageNumber";
	
	public static final String QRY_PARAM_NAME_PAGE_SIZE		= "pageSize";
	
	public static final String QRY_PARAM_NAME_FETCH_ALL		= "fetchAll";

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected ResourceContext resourceContext;
	
	@Autowired
	protected AppConfigProvider appConfigProvider;
	
	protected Map<MetaCriteriaKey, List<FieldMetaCriterion>> fieldMetaCriteriaMap = new ConcurrentHashMap<>();
	
	protected abstract Class<T> getEntityClass();

	protected abstract BaseSeriesRepository<SE> getSeriesRepository();
	
	protected abstract BaseProductRepository<T> getProductRepository();
	
	protected abstract <SM extends AbstractSeriesMapper<S, SE>> SM getSeriesMapper();
	
	protected abstract M mapEntityToModel(T entity, boolean comprehensiveMapping);

	protected abstract List<String> getSearchMetaCriteriaFields();

	protected abstract Map<?, Range<? extends Number>> getMinMaxRangeByUnit(String fieldName, MetaCriteriaKey criteriaKey);

	@Override
	public SearchMetaCriteriaResponse getSearchMetaCriteria(MetaCriteriaKey criteriaKey) {
		List<FieldMetaCriterion> fieldMetaCriteria = 
				fieldMetaCriteriaMap.computeIfAbsent(criteriaKey, this::createFieldMetaCriteria);
		return SearchMetaCriteriaResponse.builder()
				.domain(getEntityClass().getSimpleName())
				.fieldMetaCriteria(fieldMetaCriteria)
				.build();
		
	}

	protected List<FieldMetaCriterion> createFieldMetaCriteria(MetaCriteriaKey criteriaKey) {
		SearchMetaCriteriaBuilder searchMetaCriteriaBuilder = new SearchMetaCriteriaBuilder();
		return searchMetaCriteriaBuilder.createFieldMetaCriteria(criteriaKey, 
				getSearchMetaCriteriaFields(), getEntityClass(), this::getMinMaxRangeByUnit);
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Set<Object>> getCachedUnitsOfFieldNames(String type, String category, String series) {
		MetaCriteriaKey key = MetaCriteriaKey.builder()
				.type(type != null ? MotorCategory.Type.valueOf(type) : null)
				.category(category)
				.series(series)
				.build();
		List<FieldMetaCriterion> fieldMetaCriterions = fieldMetaCriteriaMap.get(key);
		Map<String, Set<Object>> unitsOfFieldNames = null;
		if (fieldMetaCriterions != null) {
			unitsOfFieldNames = fieldMetaCriterions.stream()
					.filter(c -> c.getMinMaxByUnits() != null)
					.collect(Collectors.toMap(FieldMetaCriterion::getFieldName, c -> (Set<Object>) c.getMinMaxByUnits().keySet()));
		}
		return unitsOfFieldNames;
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
			if (model.contains("_")) {
				opEntity = getProductRepository().findByModel(model.replace('_', '/'));
			}
			if (opEntity.isPresent()) {
				return mapEntityToModel(opEntity.get());
			} else {
				return null;
			}
		}
	}

	protected PageableListDataResponse<M> searchBySeries(ConditionClause conditionClause, String series, Integer pageNumber, Integer pageSize) {
		List<Pair<String, Object>> additionalFieldMatching = new ArrayList<>();
		if (!ObjectUtils.isEmpty(series)) {
			additionalFieldMatching.add(Pair.of(AbstractProductEntity.FIELD_NAME_SERIES, series));
		}
		Map<String, Set<Object>> unitsOfFieldNames = getCachedUnitsOfFieldNames(null, null, series);
		return searchWith(conditionClause, additionalFieldMatching, pageNumber, pageSize, unitsOfFieldNames);
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
		return searchWith(conditionClause, null, pageNumber, pageSize, null);
	}

	protected PageableListDataResponse<M> searchWith(ConditionClause conditionClause,
			List<Pair<String, Object>> additionalFieldMatching,
			Integer pageNumber, Integer pageSize,
			Map<String, Set<Object>> unitsOfFieldNames) {
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
		}, pageNumber, pageSize, unitsOfFieldNames);
	}
	
	protected PageableListDataResponse<M> searchBy(
			ConditionClause conditionClause,
			BiFunction<Root<T>, CriteriaBuilder, Pair<Predicate, LogicalOperator>> fAdditionalCondition,
			Integer pageNumber, Integer pageSize,
			Map<String, Set<Object>> unitsOfFieldNames) {

		DbProductSearcher searcher = new DbProductSearcher();
		return searcher.search(getProductRepository(), 
				conditionClause, 
				fAdditionalCondition, 
				pageNumber, pageSize, 
				entity -> mapEntityToModel(entity, false),
				unitsOfFieldNames);
	}

	protected void validateSearchRequest(ConditionClause conditionClause,
			Integer pageNumber, Integer pageSize,
			Boolean fetchAll) {
		
		if (pageNumber != null && pageSize == null || pageNumber == null && pageSize != null) {
			throw new ValidationException("Must have both 'pageNumber' and 'pageSize' parameters for pagination",
					ErrorCode.ERR_CODE_REQUIRE_BOTH_OR_NONE_PAGE_PARAMS);
		}
		if (Boolean.TRUE.equals(fetchAll)) {
			if (conditionClause != null && ObjectUtils.isNotEmpty(conditionClause.getConditions())) {
				throw new ValidationException("Cannot have search conditions specified along with 'fetchAll' flag as true.",
						ErrorCode.ERR_CODE_SEARCH_CANNOT_HAVE_BOTH_CONDITION_AND_FETCHWITHOUTCONDITION);
			}
		} else {
			if (conditionClause == null || ObjectUtils.isEmpty(conditionClause.getConditions())) {
				throw new ValidationException("Require search conditions or 'fetchAll' flag as true.",
						ErrorCode.ERR_CODE_SEARCH_REQUIRE_CONDITION_OR_FETCHWITHOUTCONDITION);
			}
		}
	}

	protected String getModelDisplayName() {
		return "Model";
	}

	protected PageableListDataResponse<S> getSerieses(Integer pageNumber, Integer pageSize) {
		PageableListDataResponse<S> dataResponse = new PageableListDataResponse<>();
		List<SE> entities = null;
		if (pageNumber == null || pageSize == null) {
			entities = getSeriesRepository().findAll();
		} else {
			Page<SE> page = getSeriesRepository().findAll(
					PageRequest.of(pageNumber, pageSize, Sort.by(AbstractSeriesEntity.FIELD_NAME_SERIES)));
			entities = page.getContent();
			ContentUtils.populatePageableListDataResponse(dataResponse, page);
		}
		if (entities != null) {
			List<S> serieses = entities.stream()
					.map(entity -> getSeriesMapper().mapToSeriesModel(entity))
					.collect(Collectors.toList());
			dataResponse.setData(serieses);
		}
		return dataResponse;
	}

	protected S getSeries(String series) {
		Optional<SE> opSeries = getSeriesRepository().findBySeries(series);
		if (opSeries.isPresent()) {
			return getSeriesMapper().mapToSeriesModel(opSeries.get());
		} else {
			return null;
		}
	}
}
