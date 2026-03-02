package com.exmek.core.rest;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.exmek.commons.expr.LogicalOperator;
import com.exmek.core.commons.model.Range;
import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.exception.ErrorCode;
import com.exmek.core.exception.ValidationException;
import com.exmek.core.helper.MetaCriteriaKey;
import com.exmek.core.mapper.AbstractSeriesMapper;
import com.exmek.core.mapper.MapperUtils;
import com.exmek.core.model.AbstractProduct;
import com.exmek.core.model.AbstractSeries;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.entity.AbstractSeriesEntity;
import com.exmek.core.persistence.repository.BaseProductRepository;
import com.exmek.core.persistence.repository.BaseSeriesRepository;
import com.exmek.core.scheduler.Scheduleable;
import com.exmek.core.search.DbProductSearcher;
import com.exmek.core.search.SearchMetaCriteriaBuilder;
import com.exmek.core.service.ProductService;
import com.exmek.core.utils.ContentUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class BaseProductRestController<T extends AbstractProductEntity, L extends AbstractProductEntity, M extends AbstractProduct, SE extends AbstractSeriesEntity, S extends AbstractSeries> 
implements ProductService<M>, Scheduleable {

	public static final String PARAM_NAME_SERIES			= "series";

	public static final String QRY_PARAM_NAME_PAGE_NUMBER	= "pageNumber";
	
	public static final String QRY_PARAM_NAME_PAGE_SIZE		= "pageSize";
	
	public static final String QRY_PARAM_NAME_FETCH_ALL		= "fetchAll";

	@Autowired
	protected AppConfigProvider appConfigProvider;
	
	@Autowired
	protected DbProductSearcher productSearcher;

	@Autowired
	protected SearchMetaCriteriaBuilder searchMetaCriteriaBuilder;
	
	protected Map<MetaCriteriaKey, List<FieldMetaCriterion>> fieldMetaCriteriaMap = new ConcurrentHashMap<>();
	
	protected abstract Class<T> getEntityClass();

	protected abstract BaseSeriesRepository<SE> getSeriesRepository();
	
	protected abstract BaseProductRepository<T> getProductRepository();
	
	protected abstract BaseProductRepository<L> getLightweightProductRepository();
	
	protected abstract <SM extends AbstractSeriesMapper<S, SE>> SM getSeriesMapper();
	
	protected abstract M mapEntityToModel(T entity);

	protected abstract M mapLightweightEntityToModel(L entity);
	
	protected abstract List<String> getSearchMetaCriteriaFields();

	protected abstract Map<?, Range<? extends Number>> getMinMaxRangeByUnit(String fieldName, MetaCriteriaKey criteriaKey);

	@Override
	public void onSchedule() {
		fieldMetaCriteriaMap.clear();
	}
	
	@Override
	public SearchMetaCriteriaResponse getSearchMetaCriteria(MetaCriteriaKey criteriaKey) {
		List<FieldMetaCriterion> fieldMetaCriteria = 
				fieldMetaCriteriaMap.computeIfAbsent(criteriaKey, this::createFieldMetaCriteria);
		List<FieldMetaCriterion> resultCriteria = fieldMetaCriteria.stream()
				.filter(c -> filterCriterion(c))
				.collect(Collectors.toList());
		return SearchMetaCriteriaResponse.builder()
				.domain(getEntityClass().getSimpleName())
				.fieldMetaCriteria(resultCriteria)
				.defaultPageSize(appConfigProvider.getSearchDefaultPageSize())
				.build();
		
	}

	private boolean filterCriterion(FieldMetaCriterion c) {
		if (c == null) {
			return false;
		}
		if (Boolean.TRUE.equals(c.getIsNumber())) {
			return MapUtils.isNotEmpty(c.getMinMaxByUnits());
		}
		return true;
	}

	protected List<FieldMetaCriterion> createFieldMetaCriteria(MetaCriteriaKey criteriaKey) {
		return searchMetaCriteriaBuilder.createFieldMetaCriteria(criteriaKey, 
				getSearchMetaCriteriaFields(), getEntityClass(), this::getMinMaxRangeByUnit);
	}

	protected Map<String, Set<Object>> getCachedDataAvailableUnitsOfFieldNames() {
		return getCachedUnitsOfFieldNames(null, null, null);
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
			Map<String, Set<Object>> dataAvailableUnitsOfFieldNames) {
		return searchBy(conditionClause, (root, builder) -> {
			return toPredicateWithOperator(additionalFieldMatching, root, builder);
		}, pageNumber, pageSize, dataAvailableUnitsOfFieldNames);
	}
	
	static <X extends AbstractProductEntity> Pair<Predicate, LogicalOperator> toPredicateWithOperator(
			List<Pair<String, Object>> additionalFieldMatching, Root<X> root, CriteriaBuilder builder) {

		if (!ObjectUtils.isEmpty(additionalFieldMatching)) {
			List<Predicate> predicates = additionalFieldMatching.stream()
					.filter(p -> !ObjectUtils.isEmpty(p.getLeft()) && !ObjectUtils.isEmpty(p.getRight()))
					.map(p -> builder.equal(root.get(p.getLeft()), p.getRight()))
					.collect(Collectors.toList());
			Predicate combinedPredicate = JPAUtils.buildConjunctPredicate(builder, predicates, LogicalOperator.AND);
			return Pair.of(combinedPredicate, LogicalOperator.AND);
		} else {
			return null;
		}
	}
	
	protected PageableListDataResponse<M> searchBy(
			ConditionClause conditionClause,
			BiFunction<Root<L>, CriteriaBuilder, Pair<Predicate, LogicalOperator>> fAdditionalCondition,
			Integer pageNumber, Integer pageSize,
			Map<String, Set<Object>> dataAvailableUnitsOfFieldNames) {

		PageableListDataResponse<M> dataResponse = new PageableListDataResponse<>();
		
		List<L> entities = null;
		if (pageNumber == null || pageSize == null) {
			entities = productSearcher.search(getLightweightProductRepository(), 
					conditionClause, 
					fAdditionalCondition, 
					dataAvailableUnitsOfFieldNames);
		} else {
			Page<L> page = productSearcher.search(getLightweightProductRepository(), 
					conditionClause, 
					fAdditionalCondition, 
					pageNumber, pageSize, 
					dataAvailableUnitsOfFieldNames);
			entities = page.getContent();
			ContentUtils.populatePageableListDataResponse(dataResponse, page);
		}
		dataResponse.setData(mapToLightweightModels(entities));
		
		return dataResponse;
	}
		
	protected List<M> mapToLightweightModels(List<L> entities) {
		if (entities == null) {
			return null;
		}
		return entities.stream()
				.map(entity -> mapLightweightEntityToModel(entity))
				.collect(Collectors.toList());
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

	protected S mapToSeriesModel(SE entity, Date lastUpdated) {
		S series = getSeriesMapper().mapToSeriesModel(entity);
		series.setHasNew(MapperUtils.determineIsNew(lastUpdated, appConfigProvider));
		return series;
	}

	protected S getSeries(String series) {
		Optional<SE> opSeries = getSeriesRepository().findBySeries(series);
		if (opSeries.isPresent()) {
			Date lastUpdated = getProductRepository().findLastUpdatedBySeries(series);
			return mapToSeriesModel(opSeries.get(), lastUpdated);
		} else {
			return null;
		}
	}
}
