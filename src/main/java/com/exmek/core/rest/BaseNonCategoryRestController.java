package com.exmek.core.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

import com.exmek.core.model.AbstractProduct;
import com.exmek.core.model.AbstractSeries;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.entity.AbstractSeriesEntity;
import com.exmek.core.persistence.projection.LastUpdatedTimestampPerSeries;
import com.exmek.core.persistence.repository.BaseNonCategoryRepository;
import com.exmek.core.utils.ContentUtils;

public abstract class BaseNonCategoryRestController<T extends AbstractProductEntity, L extends AbstractProductEntity, M extends AbstractProduct, SE extends AbstractSeriesEntity, S extends AbstractSeries> 
extends BaseProductRestController<T, L, M, SE, S> {

	@Override
	protected abstract BaseNonCategoryRepository<T> getProductRepository();

	protected PageableListDataResponse<M> searchBySeries(ConditionClause conditionClause, String series, Integer pageNumber, Integer pageSize) {
		List<Pair<String, Object>> additionalFieldMatching = new ArrayList<>();
		if (!ObjectUtils.isEmpty(series)) {
			additionalFieldMatching.add(Pair.of(AbstractProductEntity.FIELD_NAME_SERIES, series));
		}
		Map<String, Set<Object>> dataAvailableUnitsOfFieldNames = getCachedDataAvailableUnitsOfFieldNames();
		return searchWith(conditionClause, additionalFieldMatching, pageNumber, pageSize, dataAvailableUnitsOfFieldNames);
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
			List<LastUpdatedTimestampPerSeries> lastUpdatedPerSeriesList = getProductRepository().findLastUpdatedPerSeries();
			Map<String, Date> lastUpdatedPerSeriesMap = Optional.ofNullable(lastUpdatedPerSeriesList).stream()
					.flatMap(List::stream)
					.filter(lu -> lu.getLastUpdated() != null)
					.collect(Collectors.toMap(LastUpdatedTimestampPerSeries::getSeries, LastUpdatedTimestampPerSeries::getLastUpdated));
			List<S> serieses = entities.stream()
					.map(entity -> mapToSeriesModel(entity, lastUpdatedPerSeriesMap.get(entity.getSeries())))
					.collect(Collectors.toList());
			dataResponse.setData(serieses);
		}
		return dataResponse;
	}

}
