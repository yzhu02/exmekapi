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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exmek.core.mapper.BrakeMapper;
import com.exmek.core.mapper.BrakeSeriesMapper;
import com.exmek.core.model.Brake;
import com.exmek.core.model.BrakeSeries;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.entity.AbstractSeriesEntity;
import com.exmek.core.persistence.entity.BrakeEntity;
import com.exmek.core.persistence.entity.BrakeSeriesEntity;
import com.exmek.core.persistence.repository.BaseProductRepository;
import com.exmek.core.persistence.repository.BrakeRepository;
import com.exmek.core.persistence.repository.BrakeSeriesRepository;
import com.exmek.core.service.ProductService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/brakes")
public class BrakeRestController extends BaseProductRestController<BrakeEntity, Brake> implements ProductService<Brake> {

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
	protected BaseProductRepository<BrakeEntity> getProductRepository() {
		return brakeRepository;
	}

	@Override
	protected Brake mapEntityToModel(BrakeEntity entity, boolean comprehensiveMapping) {
		return brakeMapper.mapBrakeToModel(entity, comprehensiveMapping);
	}

	@Override
	protected List<String> getSearchMetaCriteriaFields() {
		return appConfig.getSearchBrakeMetaCriteriaFields();
	}

	@GetMapping("/serieses")
	public PageableListDataResponse<BrakeSeries> getBrakeSerieses(
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {

		PageableListDataResponse<BrakeSeries> dataResponse = new PageableListDataResponse<>();
		List<BrakeSeriesEntity> entities = null;
		if (pageNumber == null || pageSize == null) {
			entities = brakeSeriesRepository.findAll();
		} else {
			Page<BrakeSeriesEntity> page = brakeSeriesRepository.findAll(
					PageRequest.of(pageNumber, pageSize, Sort.by(AbstractSeriesEntity.FIELD_NAME_SERIES)));
			entities = page.getContent();
			populatePageableListDataResponse(dataResponse, page);
		}
		if (entities != null) {
			List<BrakeSeries> serieses = entities.stream()
					.map(entity -> brakeSeriesMapper.mapToSeriesModel(entity))
					.collect(Collectors.toList());
			dataResponse.setData(serieses);
		}
		return dataResponse;
	}

	@GetMapping("/serieses/{" + PARAM_NAME_SERIES + "}")
	public BrakeSeries getBrakeSeries(@PathVariable(PARAM_NAME_SERIES) String series) {
		Optional<BrakeSeriesEntity> opSeries = brakeSeriesRepository.findBySeries(series);
		if (opSeries.isPresent()) {
			return brakeSeriesMapper.mapToSeriesModel(opSeries.get());
		} else {
			return null;
		}
	}
	
	@GetMapping("/{idOrModel}")
	public Brake getBrake(@NotNull @PathVariable("idOrModel") String idOrModel) {
		return super.getProduct(idOrModel);
	}
	
	@PostMapping("/search")
	public PageableListDataResponse<Brake> searchBrakes(@RequestBody ConditionClause conditionClause,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {
		return super.searchWith(conditionClause, pageNumber, pageSize);
	}

	@PostMapping("/{" + PARAM_NAME_SERIES + "}/search")
	public PageableListDataResponse<Brake> searchBraksBySeries(@RequestBody ConditionClause conditionClause,
			@PathVariable(PARAM_NAME_SERIES) String series,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {
		List<Pair<String, Object>> additionalFieldMatching = new ArrayList<>();
		if (!ObjectUtils.isEmpty(series)) {
			additionalFieldMatching.add(Pair.of(AbstractProductEntity.FIELD_NAME_SERIES, series));
		}
		return super.searchWith(conditionClause, additionalFieldMatching, pageNumber, pageSize);
	}

	
	@Override
	@GetMapping("/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteria() {
		return super.getSearchMetaCriteria();
	}
	
	@GetMapping("/{" + PARAM_NAME_SERIES + "}/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteriaBySeries(
			@PathVariable(PARAM_NAME_SERIES) String series) {
		return super.getSearchMetaCriteria();
	}

}
