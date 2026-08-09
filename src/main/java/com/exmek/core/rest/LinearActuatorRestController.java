package com.exmek.core.rest;

import com.exmek.commons.utils.ReflectionUtils;
import com.exmek.core.commons.model.Range;
import com.exmek.core.consts.EndpointConsts;
import com.exmek.core.helper.MetaCriteriaKey;
import com.exmek.core.mapper.BrakeSeriesMapper;
import com.exmek.core.mapper.LinearActuatorMapper;
import com.exmek.core.model.AbstractSeries;
import com.exmek.core.model.LinearActuator;
import com.exmek.core.persistence.entity.AbstractSeriesEntity;
import com.exmek.core.persistence.entity.LightweightLinearActuatorEntity;
import com.exmek.core.persistence.entity.LinearActuatorEntity;
import com.exmek.core.persistence.repository.BaseSeriesRepository;
import com.exmek.core.persistence.repository.LightweightLinearActuatorRepository;
import com.exmek.core.persistence.repository.LinearActuatorRepository;
import com.exmek.core.service.ProductService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(EndpointConsts.ENDPOINT_API_ACTUATORS)
public class LinearActuatorRestController extends BaseNonCategoryRestController<LinearActuatorEntity, LightweightLinearActuatorEntity, LinearActuator, AbstractSeriesEntity, AbstractSeries>
implements ProductService<LinearActuator> {

	@Autowired
	private LinearActuatorRepository linearActuatorRepository;

	@Autowired
	private LightweightLinearActuatorRepository lightweightLinearActuatorRepository;
	
	@Autowired
	private LinearActuatorMapper linearActuatorMapper;

	@Override
	protected Class<LinearActuatorEntity> getEntityClass(MetaCriteriaKey criteriaKey) {
		return LinearActuatorEntity.class;
	}
	
	@Override
	protected BaseSeriesRepository<AbstractSeriesEntity> getSeriesRepository() {
    // TODO Currently linear actuator doesn't have series
		return null;
	}

	@Override
	protected LinearActuatorRepository getProductRepository() {
		return linearActuatorRepository;
	}

	@Override
	protected LightweightLinearActuatorRepository getLightweightProductRepository() {
		return lightweightLinearActuatorRepository;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BrakeSeriesMapper getSeriesMapper() {
    // TODO Currently linear actuator doesn't have series
		return null;
	}

	@Override
	protected LinearActuator mapEntityToModel(LinearActuatorEntity entity) {
		return linearActuatorMapper.mapLinearActuatorToModel(entity);
	}

	@Override
	protected LinearActuator mapLightweightEntityToModel(LightweightLinearActuatorEntity entity) {
		return linearActuatorMapper.mapLightweightLinearActuatorToModel(entity);
	}
	
	@Override
	protected List<String> getSearchMetaCriteriaFields(MetaCriteriaKey criteriaKey) {
		return resolveSearchMetaCriteriaFields(appConfigProvider.getSearchLinearActuatorMetaCriteriaFields(), criteriaKey);
	}

	@GetMapping("/{idOrModel}")
	public LinearActuator getLinearActuator(@NotNull @PathVariable("idOrModel") String idOrModel) {
		return super.getProduct(idOrModel);
	}
	
	@PostMapping("/search")
	public PageableListDataResponse<LinearActuator> searchLinearActuators(@RequestBody ConditionClause conditionClause,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = QRY_PARAM_NAME_FETCH_ALL, required = false) Boolean fetchAll) {
		
		validateSearchRequest(conditionClause, pageNumber, pageSize, fetchAll);
		return super.searchWith(conditionClause, pageNumber, pageSize);
	}

	@GetMapping("/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteriaByNone() {
		MetaCriteriaKey key = MetaCriteriaKey.builder()
				.build();
		return super.getSearchMetaCriteria(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Map<?, Range<? extends Number>> getMinMaxRangeByUnit(String fieldName, MetaCriteriaKey key) {
		return (Map<?, Range<? extends Number>>) ReflectionUtils.readValueFromMethod(
				getProductRepository(),
				"find" + StringUtils.capitalize(fieldName) + "MinMaxByUnits",
				null);
	}
}
