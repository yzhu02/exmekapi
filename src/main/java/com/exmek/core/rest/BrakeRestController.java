package com.exmek.core.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exmek.commons.utils.ReflectionUtils;
import com.exmek.core.commons.model.Range;
import com.exmek.core.consts.EndpointConsts;
import com.exmek.core.helper.MetaCriteriaKey;
import com.exmek.core.mapper.BrakeMapper;
import com.exmek.core.mapper.BrakeSeriesMapper;
import com.exmek.core.model.Brake;
import com.exmek.core.model.BrakeSeries;
import com.exmek.core.persistence.entity.BrakeEntity;
import com.exmek.core.persistence.entity.BrakeSeriesEntity;
import com.exmek.core.persistence.repository.BaseProductRepository;
import com.exmek.core.persistence.repository.BaseSeriesRepository;
import com.exmek.core.persistence.repository.BrakeRepository;
import com.exmek.core.persistence.repository.BrakeSeriesRepository;
import com.exmek.core.service.ProductService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(EndpointConsts.ENDPOINT_API_BRAKES)
public class BrakeRestController extends BaseProductRestController<BrakeEntity, Brake, BrakeSeriesEntity, BrakeSeries> implements ProductService<Brake> {

	@Autowired
	private BrakeRepository brakeRepository;
	
	@Autowired
	private BrakeSeriesRepository brakeSeriesRepository;

	@Autowired
	private BrakeMapper brakeMapper;

	@Autowired
	private BrakeSeriesMapper brakeSeriesMapper;

	@Override
	protected Class<BrakeEntity> getEntityClass() {
		return BrakeEntity.class;
	}
	
	@Override
	protected BaseSeriesRepository<BrakeSeriesEntity> getSeriesRepository() {
		return brakeSeriesRepository;
	}

	@Override
	protected BaseProductRepository<BrakeEntity> getProductRepository() {
		return brakeRepository;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BrakeSeriesMapper getSeriesMapper() {
		return brakeSeriesMapper;
	}

	@Override
	protected Brake mapEntityToModel(BrakeEntity entity, boolean comprehensiveMapping) {
		return brakeMapper.mapBrakeToModel(entity, comprehensiveMapping);
	}

	@Override
	protected List<String> getSearchMetaCriteriaFields() {
		return appConfigProvider.getSearchBrakeMetaCriteriaFields();
	}

	@Override
	@GetMapping("/serieses")
	public PageableListDataResponse<BrakeSeries> getSerieses(
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {
		return super.getSerieses(pageNumber, pageSize);
	}

	@Override
	@GetMapping("/serieses/{" + PARAM_NAME_SERIES + "}")
	public BrakeSeries getSeries(@PathVariable(PARAM_NAME_SERIES) String series) {
		return super.getSeries(series);
	}
	
	@GetMapping("/{idOrModel}")
	public Brake getBrake(@NotNull @PathVariable("idOrModel") String idOrModel) {
		return super.getProduct(idOrModel);
	}
	
	@PostMapping("/search")
	public PageableListDataResponse<Brake> searchBrakes(@RequestBody ConditionClause conditionClause,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = QRY_PARAM_NAME_FETCH_ALL, required = false) Boolean fetchAll) {
		
		validateSearchRequest(conditionClause, pageNumber, pageSize, fetchAll);
		return super.searchWith(conditionClause, pageNumber, pageSize);
	}

	@PostMapping("/{" + PARAM_NAME_SERIES + "}/search")
	public PageableListDataResponse<Brake> searchBySeries(@RequestBody ConditionClause conditionClause,
			@PathVariable(PARAM_NAME_SERIES) String series,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = QRY_PARAM_NAME_FETCH_ALL, required = false) Boolean fetchAll) {
		
		validateSearchRequest(conditionClause, pageNumber, pageSize, fetchAll);
		return super.searchBySeries(conditionClause, series, pageNumber, pageSize);
	}

	@GetMapping("/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteriaByNone() {
		MetaCriteriaKey key = MetaCriteriaKey.builder()
				.build();
		return super.getSearchMetaCriteria(key);
	}
	
	@GetMapping("/{" + PARAM_NAME_SERIES + "}/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteriaBySeries(
			@PathVariable(PARAM_NAME_SERIES) String series) {
		MetaCriteriaKey key = MetaCriteriaKey.builder()
				.series(series)
				.build();
		return super.getSearchMetaCriteria(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Map<?, Range<? extends Number>> getMinMaxRangeByUnit(String fieldName, MetaCriteriaKey key) {
		return (Map<?, Range<? extends Number>>) ReflectionUtils.readValueFromMethod(
				getProductRepository(),
				"find" + StringUtils.capitalize(fieldName) + "MinMaxByUnits",
				new Class[] {String.class},
				key.getSeries());
	}
}
