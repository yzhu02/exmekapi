package com.exmek.core.rest;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
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
import com.exmek.core.model.DCMotor;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.model.MotorSeries;
import com.exmek.core.persistence.entity.DCMotorCategoryEntity;
import com.exmek.core.persistence.entity.DCMotorEntity;
import com.exmek.core.persistence.entity.DCMotorSeriesEntity;
import com.exmek.core.persistence.entity.LightweightDCMotorEntity;
import com.exmek.core.persistence.repository.DCMotorCategoryRepository;
import com.exmek.core.persistence.repository.DCMotorRepository;
import com.exmek.core.persistence.repository.DCMotorSeriesRepository;
import com.exmek.core.persistence.repository.LightweightDCMotorRepository;
import com.exmek.core.service.ProductService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(EndpointConsts.ENDPOINT_API_MOTORS)
public class DCMotorRestController 
extends BaseMotorRestController<DCMotorEntity, LightweightDCMotorEntity, DCMotor, DCMotorSeriesEntity, DCMotorCategoryEntity> 
implements ProductService<DCMotor> {
	
	public static final String QRY_PARAM_VALUE_TYPE_BLDC	= "BLDC";
	public static final String QRY_PARAM_VALUE_TYPE_BRUSH	= "Brush";

  /// BLDC_INTEGRATED is displayed as separate independent category (no longer under BLDC type) in the frontend right now///
  private static final String[] CATEGORIES_DISPLAY_AS_SEPARATE = {
      MotorCategory.BLDC_INTEGRATED
  };

	@Autowired
	protected DCMotorCategoryRepository motorCategoryRepository;

	@Autowired
	protected DCMotorSeriesRepository motorSeriesRepository;

	@Autowired
	private DCMotorRepository motorRepository;
	
	@Autowired
	private LightweightDCMotorRepository lightweightMotorRepository;
	
	@Autowired
	private MotorMapper motorMapper;

	@Override
	protected Class<DCMotorEntity> getEntityClass() {
		return DCMotorEntity.class;
	}

	@Override
	protected DCMotorCategoryRepository getMotorCategoryRepository() {
		return motorCategoryRepository;
	}

	@Override
	protected DCMotorSeriesRepository getSeriesRepository() {
		return motorSeriesRepository;
	}
	
	@Override
	protected DCMotorRepository getProductRepository() {
		return motorRepository;
	}

	@Override
	protected LightweightDCMotorRepository getLightweightProductRepository() {
		return lightweightMotorRepository;
	}
	
	@Override
	protected DCMotor mapEntityToModel(DCMotorEntity entity) {
		return motorMapper.mapDCMotorToModel(entity);
	}

	@Override
	protected DCMotor mapLightweightEntityToModel(LightweightDCMotorEntity entity) {
		return motorMapper.mapLightweightDCMotorToModel(entity);
	}
	
	@Override
	protected List<String> getSearchMetaCriteriaFields() {
		return appConfigProvider.getSearchDCMotorMetaCriteriaFields();
	}
	
	@Override
	@GetMapping("/DC/categories")
	public List<MotorCategory> getMotorCategories(@RequestParam(value = QRY_PARAM_NAME_TYPE, required = false) String type) {
    List<MotorCategory> categories = super.getMotorCategories(type);
    return categories.stream()
        .filter(cat -> !ArrayUtils.contains(CATEGORIES_DISPLAY_AS_SEPARATE, cat.getCategory()))
        .toList();
	}

	@Override
	@GetMapping("/DC/categories/{" + PARAM_NAME_CATEGORY + "}")
	public MotorCategory getMotorCategory(@PathVariable(PARAM_NAME_CATEGORY) String category) {
		return super.getMotorCategory(category);
	}
	
	@Override
	@GetMapping("/DC/{" + PARAM_NAME_CATEGORY + "}/serieses")
	public PageableListDataResponse<MotorSeries> getMotorSeriesesByCategory(
			@PathVariable(PARAM_NAME_CATEGORY) String category,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {

		return super.getMotorSeriesesByCategory(category, pageNumber, pageSize);
	}
	
	@Override
	@GetMapping("/DC/serieses/{" + PARAM_NAME_SERIES + "}")
	public MotorSeries getSeries(@PathVariable(PARAM_NAME_SERIES) String series) {
		return super.getSeries(series);
	}
	
	@GetMapping("/DC/{idOrModel}")
	public DCMotor getMotor(@NotNull @PathVariable("idOrModel") String idOrModel) {
		return super.getProduct(idOrModel);
	}
	
	/**
	 * <pre>
	 * Search with given list of criteria that act as AND, and a query parameter specifying the motor category.
	 * An sample URL: "http://localhost:8080/DC/search?type=BLDC"
	 * The format of criteria is "fieldName logicalOperator comparingValue."
	 * Criteria examples:
	 * 	ratedVoltage=24
	 * 	ratedRotatingSpeed>=2000
	 * 	ratedRotatingSpeed<=4000
	 * </pre>
	 */
	@PostMapping("/DC/search")
	public PageableListDataResponse<DCMotor> searchMotors(@RequestBody ConditionClause conditionClause,
			@RequestParam(value = QRY_PARAM_NAME_TYPE, required = false) String type,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = QRY_PARAM_NAME_FETCH_ALL, required = false) Boolean fetchAll) {

		validateSearchRequest(conditionClause, pageNumber, pageSize, fetchAll);
		return super.searchMotorsByCategoryType(conditionClause, type, pageNumber, pageSize);
	}

	/**
	 * <pre>
	 * Search with given list of criteria that act as AND, and a query parameter specifying the motor category.
	 * An sample URL: "http://localhost:8080/DC/BLDC_INTERNAL_ROTOR/search"
	 * The format of criteria is "fieldName logicalOperator comparingValue."
	 * Criteria examples:
	 * 	ratedVoltage=24
	 * 	ratedRotatingSpeed>=2000
	 * 	ratedRotatingSpeed<=4000
	 * </pre>
	 */
	@PostMapping("/DC/{" + PARAM_NAME_CATEGORY + "}/search")
	public PageableListDataResponse<DCMotor> searchMotorsByCategory(@RequestBody ConditionClause conditionClause,
			@PathVariable(PARAM_NAME_CATEGORY) String category,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = QRY_PARAM_NAME_FETCH_ALL, required = false) Boolean fetchAll) {
		
		validateSearchRequest(conditionClause, pageNumber, pageSize, fetchAll);
		return super.searchMotorsByCategoryBySeries(conditionClause, category, null, pageNumber, pageSize);
	}
	
	/**
	 * <pre>
	 * Search with given list of criteria that act as AND, and a query parameter specifying the motor category.
	 * An sample URL: "http://localhost:8080/DC/BLDC_INTERNAL_ROTOR/ME042WS/search"
	 * The format of criteria is "fieldName logicalOperator comparingValue."
	 * Criteria examples:
	 * 	ratedVoltage=24
	 * 	ratedRotatingSpeed>=2000
	 * 	ratedRotatingSpeed<=4000
	 * </pre>
	 */
	@PostMapping("/DC/{" + PARAM_NAME_CATEGORY + "}/{" + PARAM_NAME_SERIES + "}/search")
	public PageableListDataResponse<DCMotor> searchMotorsByCategoryBySeries(@RequestBody ConditionClause conditionClause,
			@PathVariable(PARAM_NAME_CATEGORY) String category,
			@PathVariable(PARAM_NAME_SERIES) String series,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = QRY_PARAM_NAME_FETCH_ALL, required = false) Boolean fetchAll) {
		
		validateSearchRequest(conditionClause, pageNumber, pageSize, fetchAll);
		return super.searchMotorsByCategoryBySeries(conditionClause, category, series, pageNumber, pageSize);
	}
		
	@GetMapping("/DC/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteriaByType(
			@RequestParam(value = QRY_PARAM_NAME_TYPE, required = false) String type) {
		MetaCriteriaKey key = MetaCriteriaKey.builder()
				.type(type != null ? MotorCategory.Type.valueOf(type) : null)
				.build();
		return super.getSearchMetaCriteria(key);
	}
	
	@GetMapping("/DC/{" + PARAM_NAME_CATEGORY + "}/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteriaByCategory(
			@PathVariable(PARAM_NAME_CATEGORY) String category) {
		MetaCriteriaKey key = MetaCriteriaKey.builder()
				.category(category)
				.build();
		return super.getSearchMetaCriteria(key);
	}

	@GetMapping("/DC/{" + PARAM_NAME_CATEGORY + "}/{" + PARAM_NAME_SERIES + "}/criteria")
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
				new Class[] {MotorCategory.Type.class, String.class, String.class},
				key.getType(), key.getCategory(), key.getSeries());
	}

}
