package com.exmek.core.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.exmek.commons.expr.ComparisonOperator;
import com.exmek.core.persistence.JPAUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.exmek.commons.expr.LogicalOperator;
import com.exmek.commons.utils.MiscUtils;
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
import com.exmek.core.persistence.projection.TimestampOfCategory;
import com.exmek.core.persistence.projection.TimestampOfSeries;
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
		List<TimestampOfCategory> lastCreatedPerCategoryList =
				getProductRepository().findLastCreatedPerCategory();
		Map<String, Date> lastCreatedPerCategoryMap = Optional.ofNullable(lastCreatedPerCategoryList).stream()
				.flatMap(List::stream)
				.filter(lu -> lu.getTimestamp() != null)
				.collect(Collectors.toMap(TimestampOfCategory::getCategory, TimestampOfCategory::getTimestamp));
		return entities.stream()
				.map(entity -> mapToCategoryModel(entity, lastCreatedPerCategoryMap.get(entity.getCategory())))
				.collect(Collectors.toList());
	}

	protected MotorCategory mapToCategoryModel(AbstractMotorCategoryEntity entity, Date lastCreated) {
		MotorCategory mc = motorCategoryMapper.mapToCategoryModel(entity);
		mc.setHasNew(MapperUtils.determineIsNew(lastCreated, appConfigProvider));
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
			List<TimestampOfSeries> lastCreatedPerSeriesList =
					getProductRepository().findLastCreatedPerSeriesByCategory(category);
			Map<String, Date> lastCreatedPerSeriesMap = Optional.ofNullable(lastCreatedPerSeriesList).stream()
					.flatMap(List::stream)
					.filter(lu -> lu.getTimestamp() != null)
					.collect(Collectors.toMap(TimestampOfSeries::getSeries, TimestampOfSeries::getTimestamp));
			List<MotorSeries> serieses = entities.stream()
					.map(entity -> mapToSeriesModel(entity, lastCreatedPerSeriesMap.get(entity.getSeries())))
					.collect(Collectors.toCollection(ArrayList::new));
			
			if (serieses.size() > 1) {
				//Sort by the extract number from the series as per requested
				Collections.sort(serieses, new Comparator<> () {
					@Override
					public int compare(MotorSeries s1, MotorSeries s2) {
						Integer n1 = MiscUtils.extractFirstNumber(s1.getSeries());
						Integer n2 = MiscUtils.extractFirstNumber(s2.getSeries());
						if (n1 != null && n2 != null) {
							return n1.compareTo(n2);
						} else if (n2 == null) {
							return -1; // put at end in case no number extracted: n1, n2
						} else {
							return 1; // put at end in case no number extracted: n2, n1
						}
					}
				});
			}

			dataResponse.setData(serieses);
		}
		return dataResponse;
	}

	protected PageableListDataResponse<M> searchMotorsByCategoryType(ConditionClause conditionClause,
			String type,
      List<ConditionLine> additionalFieldMatching,
      Integer pageNumber, Integer pageSize) {
		
		if (ObjectUtils.isEmpty(type)) {
			return searchBy(conditionClause, null, pageNumber, pageSize, null);
		}
		Map<String, Set<Object>> dataAvailableUnitsOfFieldNames = getCachedUnitsOfFieldNames(type, null, null);
		return searchBy(conditionClause, (root, builder) -> {
			Join<T, CE> categoryJoin = root.join(AbstractMotorEntity.FIELD_NAME_MOTOR_CATEGORY);
			Predicate pType = builder.equal(categoryJoin.get(AbstractMotorCategoryEntity.FIELD_NAME_TYPE), Type.valueOf(type.toUpperCase()));

      if (CollectionUtils.isNotEmpty(additionalFieldMatching)) {
        List<Predicate> predicates = additionalFieldMatching.stream()
            .map(cl -> JPAUtils.buildPredicate(builder, fn -> root, cl, null))
            .collect(Collectors.toList());
        Predicate combinedAdditionalPredicate = JPAUtils.buildConjunctPredicate(builder, predicates, LogicalOperator.AND);
        Predicate resultPredicate = JPAUtils.buildConjunctPredicate(builder, List.of(pType, combinedAdditionalPredicate), LogicalOperator.AND);
        return Pair.of(resultPredicate, LogicalOperator.AND);
      } else {
        return Pair.of(pType, LogicalOperator.AND);
      }
		}, pageNumber, pageSize, dataAvailableUnitsOfFieldNames);
	}
	
	protected PageableListDataResponse<M> searchMotorsByCategoryBySeries(ConditionClause conditionClause,
			String category, String series,
			Integer pageNumber, Integer pageSize) {

		List<ConditionLine> additionalFieldMatchings = asFieldMatchings(category, series);
		return searchWith(conditionClause, additionalFieldMatchings, pageNumber, pageSize, getCachedUnitsOfFieldNames(null, category, series));
	}

	protected List<ConditionLine> asFieldMatchings(String category, String series) {
		List<ConditionLine> fieldMatchings = new ArrayList<>();
		if (category != null) {
			fieldMatchings.add(ConditionLine.of(AbstractMotorEntity.FIELD_NAME_CATEGORY, ComparisonOperator.EQ, category));
		}
		if (!ObjectUtils.isEmpty(series)) {
			fieldMatchings.add(ConditionLine.of(AbstractMotorEntity.FIELD_NAME_SERIES, ComparisonOperator.EQ, series));
		}
		return fieldMatchings;
	}
}
