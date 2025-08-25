package com.exmek.core.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.exmek.commons.expr.LogicalOperator;
import com.exmek.core.mapper.MapperUtils;
import com.exmek.core.mapper.MotorCategoryMapper;
import com.exmek.core.mapper.MotorSeriesMapper;
import com.exmek.core.model.AbstractMotor;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.model.MotorCategory.Type;
import com.exmek.core.model.MotorSeries;
import com.exmek.core.persistence.entity.AbstractMotorCategoryEntity;
import com.exmek.core.persistence.entity.AbstractMotorEntity;
import com.exmek.core.persistence.entity.AbstractMotorSeriesEntity;
import com.exmek.core.persistence.entity.AbstractSeriesEntity;
import com.exmek.core.persistence.projection.LastUpdatedTimestampPerCategory;
import com.exmek.core.persistence.projection.LastUpdatedTimestampPerSeries;
import com.exmek.core.persistence.repository.BaseMotorCategoryRepository;
import com.exmek.core.persistence.repository.BaseMotorRepository;
import com.exmek.core.persistence.repository.BaseMotorSeriesRepository;
import com.exmek.core.utils.ContentUtils;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public abstract class BaseMotorRestController<T extends AbstractMotorEntity, L extends AbstractMotorEntity, M extends AbstractMotor, SE extends AbstractMotorSeriesEntity, CE extends AbstractMotorCategoryEntity> 
extends BaseProductRestController<T, L, M, SE, MotorSeries> {

	public static final String PARAM_NAME_CATEGORY	= "category";
	
	public static final String QRY_PARAM_NAME_TYPE	= "type";

	@Autowired
	protected MotorCategoryMapper motorCategoryMapper;

	@Autowired
	protected MotorSeriesMapper motorSeriesMapper;

	protected abstract BaseMotorCategoryRepository<CE> getMotorCategoryRepository();
	
	@Override
	protected abstract BaseMotorSeriesRepository<SE> getSeriesRepository();
	
	@Override
	protected abstract BaseMotorRepository<T> getProductRepository();
	
	@SuppressWarnings("unchecked")
	@Override
	protected MotorSeriesMapper getSeriesMapper() {
		return motorSeriesMapper;
	}

	protected List<MotorCategory> getMotorCategories(
			@RequestParam(value = QRY_PARAM_NAME_TYPE, required = false) String type) {
		List<CE> entities = null;
		if (!ObjectUtils.isEmpty(type)) {
			entities = getMotorCategoryRepository().findByType(Type.valueOf(type.toUpperCase()));
		} else {
			entities = getMotorCategoryRepository().findAll();
		}
		if (entities == null) {
			return new ArrayList<>();
		}
		List<LastUpdatedTimestampPerCategory> lastUpdatedPerCategoryList = 
				getProductRepository().findLastUpdatedPerCategory();
		Map<String, Date> lastUpdatedPerCategoryMap = Optional.ofNullable(lastUpdatedPerCategoryList).stream()
				.flatMap(List::stream)
				.filter(lu -> lu.getLastUpdated() != null)
				.collect(Collectors.toMap(LastUpdatedTimestampPerCategory::getCategory, LastUpdatedTimestampPerCategory::getLastUpdated));
		return entities.stream()
				.map(entity -> mapToCategoryModel(entity, lastUpdatedPerCategoryMap.get(entity.getCategory())))
				.collect(Collectors.toList());
	}

	protected MotorCategory mapToCategoryModel(AbstractMotorCategoryEntity entity, Date lastUpdated) {
		MotorCategory mc = motorCategoryMapper.mapToCategoryModel(entity);
		mc.setHasNew(MapperUtils.determineIsNew(lastUpdated, appConfigProvider));
		return mc;
	}

	protected MotorCategory getMotorCategory(String category) {
		Optional<CE> opCategory = getMotorCategoryRepository().findByCategory(category);
		if (opCategory.isPresent()) {
			return motorCategoryMapper.mapToCategoryModel(opCategory.get());
		} else {
			return null;
		}
	}

	protected PageableListDataResponse<MotorSeries> getMotorSeriesesByCategory(String category,
			Integer pageNumber, Integer pageSize) {

		PageableListDataResponse<MotorSeries> dataResponse = new PageableListDataResponse<>();
		List<SE> entities = null;
		if (pageNumber == null || pageSize == null) {
			entities = getSeriesRepository().findAllByCategory(category, Sort.by(AbstractSeriesEntity.FIELD_NAME_SERIES));
		} else {
			Page<SE> page = getSeriesRepository().findAllByCategory(category,
					PageRequest.of(pageNumber, pageSize, Sort.by(AbstractSeriesEntity.FIELD_NAME_SERIES)));
			entities = page.getContent();
			ContentUtils.populatePageableListDataResponse(dataResponse, page);
		}
		if (entities != null) {
			List<LastUpdatedTimestampPerSeries> lastUpdatedPerSeriesList = 
					getProductRepository().findLastUpdatedPerSeriesByCategory(category);
			Map<String, Date> lastUpdatedPerSeriesMap = Optional.ofNullable(lastUpdatedPerSeriesList).stream()
					.flatMap(List::stream)
					.filter(lu -> lu.getLastUpdated() != null)
					.collect(Collectors.toMap(LastUpdatedTimestampPerSeries::getSeries, LastUpdatedTimestampPerSeries::getLastUpdated));
			List<MotorSeries> serieses = entities.stream()
					.map(entity -> mapToSeriesModel(entity, lastUpdatedPerSeriesMap.get(entity.getSeries())))
					.collect(Collectors.toList());
			dataResponse.setData(serieses);
		}
		return dataResponse;
	}

	protected PageableListDataResponse<M> searchMotorsByCategoryType(ConditionClause conditionClause,
			String type, Integer pageNumber, Integer pageSize) {
		
		if (ObjectUtils.isEmpty(type)) {
			return super.searchBy(conditionClause, null, pageNumber, pageSize, null);
		}
		Map<String, Set<Object>> dataAvailableUnitsOfFieldNames = getCachedDataAvailableUnitsOfFieldNames();
		return super.searchBy(conditionClause, (root, builder) -> {
			Join<T, CE> categoryJoin = root.join(AbstractMotorEntity.FIELD_NAME_MOTOR_CATEGORY);
			Predicate pType = builder.equal(categoryJoin.get(AbstractMotorCategoryEntity.FIELD_NAME_TYPE), Type.valueOf(type.toUpperCase()));
			return Pair.of(pType, LogicalOperator.AND);
		}, pageNumber, pageSize, dataAvailableUnitsOfFieldNames);
	}
	
	protected PageableListDataResponse<M> searchMotorsByCategoryBySeries(ConditionClause conditionClause,
			String category, String series,
			Integer pageNumber, Integer pageSize) {

		List<Pair<String, Object>> additionalFieldMatching = new ArrayList<>();
		if (category != null) {
			additionalFieldMatching.add(Pair.of(AbstractMotorEntity.FIELD_NAME_CATEGORY, category));
		}
		if (!ObjectUtils.isEmpty(series)) {
			additionalFieldMatching.add(Pair.of(AbstractMotorEntity.FIELD_NAME_SERIES, series));
		}
		Map<String, Set<Object>> dataAvailableUnitsOfFieldNames = getCachedDataAvailableUnitsOfFieldNames();
		return super.searchWith(conditionClause, additionalFieldMatching, pageNumber, pageSize, dataAvailableUnitsOfFieldNames);
	}

}
