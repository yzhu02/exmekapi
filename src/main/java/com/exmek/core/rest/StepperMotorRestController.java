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
import com.exmek.core.mapper.MotorMapper;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.model.MotorSeries;
import com.exmek.core.model.StepperMotor;
import com.exmek.core.persistence.entity.StepperMotorCategoryEntity;
import com.exmek.core.persistence.entity.StepperMotorEntity;
import com.exmek.core.persistence.entity.StepperMotorSeriesEntity;
import com.exmek.core.persistence.repository.LightStepperMotorRepository;
import com.exmek.core.persistence.repository.StepperMotorCategoryRepository;
import com.exmek.core.persistence.repository.StepperMotorRepository;
import com.exmek.core.persistence.repository.StepperMotorSeriesRepository;
import com.exmek.core.service.ProductService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(EndpointConsts.ENDPOINT_API_MOTORS)
public class StepperMotorRestController 
extends BaseMotorRestController<StepperMotorEntity, StepperMotorEntity.Light, StepperMotor, StepperMotorSeriesEntity, StepperMotorCategoryEntity> implements ProductService<StepperMotor> {

	@Autowired
	protected StepperMotorCategoryRepository motorCategoryRepository;

	@Autowired
	protected StepperMotorSeriesRepository motorSeriesRepository;
	
	@Autowired
	private StepperMotorRepository motorRepository;

	@Autowired
	private LightStepperMotorRepository lightMotorRepository;

	@Autowired
	private MotorMapper motorMapper;
	
	@Override
	protected Class<StepperMotorEntity> getEntityClass() {
		return StepperMotorEntity.class;
	}

	@Override
	protected StepperMotorCategoryRepository getMotorCategoryRepository() {
		return motorCategoryRepository;
	}

	@Override
	protected StepperMotorSeriesRepository getSeriesRepository() {
		return motorSeriesRepository;
	}
	
	@Override
	protected StepperMotorRepository getProductRepository() {
		return motorRepository;
	}

	@Override
	protected LightStepperMotorRepository getLightProductRepository() {
		return lightMotorRepository;
	}

	@Override
	protected StepperMotor mapEntityToModel(StepperMotorEntity entity) {
		return motorMapper.mapStepperMotorToModel(entity);
	}

	@Override
	protected StepperMotor mapLightEntityToModel(StepperMotorEntity.Light entity) {
		return motorMapper.mapStepperMotorToModel(entity);
	}

	@Override
	protected List<String> getSearchMetaCriteriaFields() {
		return appConfigProvider.getSearchStepperMotorMetaCriteriaFields();
	}
	
	@GetMapping("/stepper/categories")
	public List<MotorCategory> getMotorCategories() {
		return super.getMotorCategories(null);
	}
	
	@Override
	@GetMapping("/stepper/categories/{" + PARAM_NAME_CATEGORY + "}")
	public MotorCategory getMotorCategory(@PathVariable(PARAM_NAME_CATEGORY) String category) {
		return super.getMotorCategory(category);
	}

	@GetMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/serieses")
	public PageableListDataResponse<MotorSeries> getMotorSeriesesByCategory(
			@PathVariable(PARAM_NAME_CATEGORY) String category,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {

		return super.searchMotorSeriesesByCategory(category, pageNumber, pageSize);
	}

	@Override
	@GetMapping("/stepper/serieses/{" + PARAM_NAME_SERIES + "}")
	public MotorSeries getSeries(@PathVariable(PARAM_NAME_SERIES) String series) {
		return super.getSeries(series);
	}

	@GetMapping("/stepper/{idOrModel}")
	public StepperMotor getMotor(@NotNull @PathVariable("idOrModel") String idOrModel) {
		return super.getProduct(idOrModel);
	}
	
	/**
	 * <pre>
	 * Search with given list of criteria that act as AND, and a query parameter specifying the motor category.
	 * An sample URL: "http://localhost:8080/stepper/search?category=STEPPER_STANDARD"
	 * The format of criteria is "fieldName logicalOperator comparingValue."
	 * Criteria examples:
	 * 	ratedVoltage=24
	 * 	ratedRotatingSpeed>=2000
	 * 	ratedRotatingSpeed<=4000
	 * </pre>
	 */
	@PostMapping("/stepper/search")
	public PageableListDataResponse<StepperMotor> searchMotors(@RequestBody ConditionClause conditionClause,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = QRY_PARAM_NAME_FETCH_ALL, required = false) Boolean fetchAll) {
		
		validateSearchRequest(conditionClause, pageNumber, pageSize, fetchAll);
		return super.searchWith(conditionClause, pageNumber, pageSize);
	}

	@PostMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/search")
	public PageableListDataResponse<StepperMotor> searchMotorsByCategory(@RequestBody ConditionClause conditionClause,
			@PathVariable(PARAM_NAME_CATEGORY) String category,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = QRY_PARAM_NAME_FETCH_ALL, required = false) Boolean fetchAll) {
		
		validateSearchRequest(conditionClause, pageNumber, pageSize, fetchAll);
		return super.searchMotorsByCategoryBySeries(conditionClause, category, null, pageNumber, pageSize);
	}
	
	@PostMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/{" + PARAM_NAME_SERIES + "}/search")
	public PageableListDataResponse<StepperMotor> searchMotorsByCategoryBySeries(@RequestBody ConditionClause conditionClause,
			@PathVariable(PARAM_NAME_CATEGORY) String category,
			@PathVariable(PARAM_NAME_SERIES) String series,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = QRY_PARAM_NAME_FETCH_ALL, required = false) Boolean fetchAll) {
		
		validateSearchRequest(conditionClause, pageNumber, pageSize, fetchAll);
		return super.searchMotorsByCategoryBySeries(conditionClause, category, series, pageNumber, pageSize);
	}
	
	@GetMapping("/stepper/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteriaByNone() {
		MetaCriteriaKey key = MetaCriteriaKey.builder()
				.build();
		return super.getSearchMetaCriteria(key);
	}

	@GetMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteriaByCategory(
			@PathVariable(PARAM_NAME_CATEGORY) String category) {
		MetaCriteriaKey key = MetaCriteriaKey.builder()
				.category(category)
				.build();
		return super.getSearchMetaCriteria(key);
	}
	
	@GetMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/{" + PARAM_NAME_SERIES + "}/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteriaByCategoryBySeries(
			@PathVariable(PARAM_NAME_CATEGORY) String category,
			@PathVariable(PARAM_NAME_SERIES) String series) {
		MetaCriteriaKey key = MetaCriteriaKey.builder()
				.category(category)
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
				new Class[] {String.class, String.class},
				key.getCategory(), key.getSeries());
	}

}
