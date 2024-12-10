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

import com.exmek.core.consts.EndpointConsts;
import com.exmek.core.mapper.GearboxSeriesMapper;
import com.exmek.core.mapper.PlanetaryGearboxMapper;
import com.exmek.core.model.GearboxSeries;
import com.exmek.core.model.PlanetaryGearbox;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.entity.AbstractSeriesEntity;
import com.exmek.core.persistence.entity.GearboxSeriesEntity;
import com.exmek.core.persistence.entity.PlanetaryGearboxEntity;
import com.exmek.core.persistence.repository.GearboxSeriesRepository;
import com.exmek.core.persistence.repository.PlanetaryGearboxRepository;
import com.exmek.core.service.ProductService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(EndpointConsts.ENDPOINT_API_GEARBOXES)
public class PlanetaryGearboxRestController extends BaseProductRestController<PlanetaryGearboxEntity, PlanetaryGearbox> implements ProductService<PlanetaryGearbox> {

	@Autowired
	private PlanetaryGearboxRepository planetaryGearboxRepository;

	@Autowired
	private GearboxSeriesRepository gearboxSeriesRepository;

	@Autowired
	private PlanetaryGearboxMapper planetaryGearboxMapper;

	@Autowired
	private GearboxSeriesMapper gearboxSeriesMapper;
	
	@Override
	protected Class<PlanetaryGearboxEntity> getEntityClass() {
		return PlanetaryGearboxEntity.class;
	}
	
	@Override
	protected PlanetaryGearboxRepository getProductRepository() {
		return planetaryGearboxRepository;
	}

	@Override
	protected PlanetaryGearbox mapEntityToModel(PlanetaryGearboxEntity entity, boolean comprehensiveMapping) {
		return planetaryGearboxMapper.mapPlanetaryGearboxToModel(entity, comprehensiveMapping);
	}

	@Override
	protected List<String> getSearchMetaCriteriaFields() {
		return appConfigProvider.getSearchPlanetaryGearboxMetaCriteriaFields();
	}
	
	@GetMapping("/serieses")
	public PageableListDataResponse<GearboxSeries> getGearboxSerieses(
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {

		PageableListDataResponse<GearboxSeries> dataResponse = new PageableListDataResponse<>();
		List<GearboxSeriesEntity> entities = null;
		if (pageNumber == null || pageSize == null) {
			entities = gearboxSeriesRepository.findAll();
		} else {
			Page<GearboxSeriesEntity> page = gearboxSeriesRepository.findAll(
					PageRequest.of(pageNumber, pageSize, Sort.by(AbstractSeriesEntity.FIELD_NAME_SERIES)));
			entities = page.getContent();
			populatePageableListDataResponse(dataResponse, page);
		}
		if (entities != null) {
			List<GearboxSeries> serieses = entities.stream()
					.map(entity -> gearboxSeriesMapper.mapToSeriesModel(entity))
					.collect(Collectors.toList());
			dataResponse.setData(serieses);
		}
		return dataResponse;
	}

	@GetMapping("/serieses/{" + PARAM_NAME_SERIES + "}")
	public GearboxSeries getGearboxSeries(@PathVariable(PARAM_NAME_SERIES) String series) {
		Optional<GearboxSeriesEntity> opSeries = gearboxSeriesRepository.findBySeries(series);
		if (opSeries.isPresent()) {
			return gearboxSeriesMapper.mapToSeriesModel(opSeries.get());
		} else {
			return null;
		}
	}
	
	@GetMapping("/{idOrModel}")
	public PlanetaryGearbox getPlanetaryGearbox(@NotNull @PathVariable("idOrModel") String idOrModel) {
		return super.getProduct(idOrModel);
	}

	@PostMapping("/search")
	public PageableListDataResponse<PlanetaryGearbox> searchGearboxes(@RequestBody ConditionClause conditionClause,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {
		return super.searchWith(conditionClause, pageNumber, pageSize);
	}

	@PostMapping("/{" + PARAM_NAME_SERIES + "}/search")
	public PageableListDataResponse<PlanetaryGearbox> searchGearboxesBySeries(@RequestBody ConditionClause conditionClause,
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
