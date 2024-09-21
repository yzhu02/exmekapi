package com.exmek.core.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.util.ObjectUtils;

import com.exmek.core.mapper.MotorCategoryMapper;
import com.exmek.core.mapper.MotorSeriesMapper;
import com.exmek.core.model.AbstractMotor;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.model.MotorSeries;
import com.exmek.core.persistence.entity.AbstractMotorEntity;
import com.exmek.core.persistence.entity.AbstractSeriesEntity;
import com.exmek.core.persistence.entity.MotorCategoryEntity;
import com.exmek.core.persistence.entity.MotorSeriesEntity;
import com.exmek.core.persistence.repository.MotorCategoryRepository;
import com.exmek.core.persistence.repository.MotorSeriesRepository;

public abstract class BaseMotorRestController<T extends AbstractMotorEntity, M extends AbstractMotor> extends BaseProductRestController<T, M> {

	public static final String PARAM_NAME_CATEGORY	= "category";
	
	public static final String QRY_PARAM_NAME_TYPE	= "type";

	@Autowired
	protected MotorCategoryRepository motorCategoryRepository;

	@Autowired
	protected MotorSeriesRepository motorSeriesRepository;

	@Autowired
	protected MotorCategoryMapper motorCategoryMapper;

	@Autowired
	protected MotorSeriesMapper motorSeriesMapper;

	protected MotorCategory getMotorCategory(MotorCategory.Category category) {
		Optional<MotorCategoryEntity> opCategory = motorCategoryRepository.findByCategory(category);
		if (opCategory.isPresent()) {
			return motorCategoryMapper.mapToCategoryModel(opCategory.get());
		} else {
			return null;
		}
	}

	protected MotorSeries getMotorSeries(String series) {
		Optional<MotorSeriesEntity> opSeries = motorSeriesRepository.findBySeries(series);
		if (opSeries.isPresent()) {
			return motorSeriesMapper.mapToSeriesModel(opSeries.get());
		} else {
			return null;
		}
	}

	protected PageableListDataResponse<M> searchMotorsByCategoryBySeries(ConditionClause conditionClause,
			MotorCategory.Category category, String series,
			Integer pageNumber, Integer pageSize) {

		List<Pair<String, Object>> additionalFieldMatching = new ArrayList<>();
		if (category != null) {
			additionalFieldMatching.add(Pair.of(AbstractMotorEntity.FIELD_NAME_CATEGORY, category));
		}
		if (!ObjectUtils.isEmpty(series)) {
			additionalFieldMatching.add(Pair.of(AbstractMotorEntity.FIELD_NAME_SERIES, series));
		}
		return super.searchWith(conditionClause, additionalFieldMatching, pageNumber, pageSize);
	}

	protected PageableListDataResponse<MotorSeries> searchMotorSeriesesByCategory(MotorCategory.Category category,
			Integer pageNumber, Integer pageSize) {

		PageableListDataResponse<MotorSeries> dataResponse = new PageableListDataResponse<>();
		List<MotorSeriesEntity> entities = null;
		if (pageNumber == null || pageSize == null) {
			entities = motorSeriesRepository.findAllByCategory(category);
		} else {
			Page<MotorSeriesEntity> page = motorSeriesRepository.findAllByCategory(category,
					PageRequest.of(pageNumber, pageSize, Sort.by(AbstractSeriesEntity.FIELD_NAME_SERIES)));
			entities = page.getContent();
			populatePageableListDataResponse(dataResponse, page);
		}
		if (entities != null) {
			List<MotorSeries> serieses = entities.stream()
					.map(entity -> motorSeriesMapper.mapToSeriesModel(entity))
					.collect(Collectors.toList());
			dataResponse.setData(serieses);
		}
		return dataResponse;
	}
}
