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
import com.exmek.core.mapper.GearboxSeriesMapper;
import com.exmek.core.mapper.PlanetaryGearboxMapper;
import com.exmek.core.model.GearboxSeries;
import com.exmek.core.model.PlanetaryGearbox;
import com.exmek.core.persistence.entity.GearboxSeriesEntity;
import com.exmek.core.persistence.entity.LightweightPlanetaryGearboxEntity;
import com.exmek.core.persistence.entity.PlanetaryGearboxEntity;
import com.exmek.core.persistence.repository.GearboxSeriesRepository;
import com.exmek.core.persistence.repository.LightweightPlanetaryGearboxRepository;
import com.exmek.core.persistence.repository.PlanetaryGearboxRepository;
import com.exmek.core.service.ProductService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(EndpointConsts.ENDPOINT_API_GEARBOXES)
public class PlanetaryGearboxRestController 
extends BaseNonCategoryRestController<PlanetaryGearboxEntity, LightweightPlanetaryGearboxEntity, PlanetaryGearbox, GearboxSeriesEntity, GearboxSeries> 
implements ProductService<PlanetaryGearbox> {

	@Autowired
	private GearboxSeriesRepository gearboxSeriesRepository;

	@Autowired
	private PlanetaryGearboxRepository planetaryGearboxRepository;

	@Autowired
	private LightweightPlanetaryGearboxRepository lightweightPlanetaryGearboxRepository;
	
	@Autowired
	private PlanetaryGearboxMapper planetaryGearboxMapper;

	@Autowired
	private GearboxSeriesMapper seriesMapper;
	
	@Override
	protected Class<PlanetaryGearboxEntity> getEntityClass(MetaCriteriaKey criteriaKey) {
		return PlanetaryGearboxEntity.class;
	}
	
	@Override
	protected GearboxSeriesRepository getSeriesRepository() {
		return gearboxSeriesRepository;
	}

	@Override
	protected PlanetaryGearboxRepository getProductRepository() {
		return planetaryGearboxRepository;
	}

	@Override
	protected LightweightPlanetaryGearboxRepository getLightweightProductRepository() {
		return lightweightPlanetaryGearboxRepository;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected GearboxSeriesMapper getSeriesMapper() {
		return seriesMapper;
	}

	@Override
	protected PlanetaryGearbox mapEntityToModel(PlanetaryGearboxEntity entity) {
		return planetaryGearboxMapper.mapPlanetaryGearboxToModel(entity);
	}

	@Override
	protected PlanetaryGearbox mapLightweightEntityToModel(LightweightPlanetaryGearboxEntity entity) {
		return planetaryGearboxMapper.mapLightweightPlanetaryGearboxToModel(entity);
	}

	@Override
	protected List<String> getSearchMetaCriteriaFields(MetaCriteriaKey criteriaKey) {
		return resolveSearchMetaCriteriaFields(appConfigProvider.getSearchPlanetaryGearboxMetaCriteriaFields(), criteriaKey);
	}
	
	@Override
	@GetMapping("/serieses")
	public PageableListDataResponse<GearboxSeries> getSerieses(
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {

		return super.getSerieses(pageNumber, pageSize);
	}

	@Override
	@GetMapping("/serieses/{" + PARAM_NAME_SERIES + "}")
	public GearboxSeries getSeries(@PathVariable(PARAM_NAME_SERIES) String series) {
		return super.getSeries(series);
	}
	
	@GetMapping("/{idOrModel}")
	public PlanetaryGearbox getPlanetaryGearbox(@NotNull @PathVariable("idOrModel") String idOrModel) {
		return super.getProduct(idOrModel);
	}

	@PostMapping("/search")
	public PageableListDataResponse<PlanetaryGearbox> searchGearboxes(@RequestBody ConditionClause conditionClause,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = QRY_PARAM_NAME_FETCH_ALL, required = false) Boolean fetchAll) {
		
		validateSearchRequest(conditionClause, pageNumber, pageSize, fetchAll);
		return super.searchWith(conditionClause, pageNumber, pageSize);
	}

	@PostMapping("/{" + PARAM_NAME_SERIES + "}/search")
	public PageableListDataResponse<PlanetaryGearbox> searchBySeries(@RequestBody ConditionClause conditionClause,
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
