package com.exmek.core.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exmek.commons.expr.LogicalOperator;
import com.exmek.core.mapper.MotorMapper;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.model.MotorSeries;
import com.exmek.core.model.StepperMotor;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.MotorCategoryEntity;
import com.exmek.core.persistence.entity.StepperMotorEntity;
import com.exmek.core.persistence.repository.BaseProductRepository;
import com.exmek.core.persistence.repository.StepperMotorRepository;
import com.exmek.core.service.ProductService;

import jakarta.persistence.criteria.Predicate;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/motors")
public class StepperMotorRestController extends BaseMotorRestController<StepperMotorEntity, StepperMotor> implements ProductService<StepperMotor> {

	@Autowired
	private StepperMotorRepository stepperMotorRepository;

	@Autowired
	private MotorMapper motorMapper;
	
	@Override
	protected Class<StepperMotorEntity> getEntityClass() {
		return StepperMotorEntity.class;
	}

	@Override
	protected BaseProductRepository<StepperMotorEntity> getProductRepository() {
		return stepperMotorRepository;
	}

	@Override
	protected StepperMotor mapEntityToModel(StepperMotorEntity entity, boolean comprehensiveMapping) {
		return motorMapper.mapStepperMotorToModel(entity, comprehensiveMapping);
	}

	@Override
	protected List<String> getSearchMetaCriteriaFields() {
		return appConfig.getSearchStepperMotorMetaCriteriaFields();
	}
	
	@GetMapping("/stepper/categories")
	public List<MotorCategory> getMotorCategories() {
		
		Specification<MotorCategoryEntity> jpaSpec = (root, query, builder) -> {
			MotorCategory.Category[] cats = MotorCategory.Category.getCategories(MotorCategory.Supertype.STEPPER, null);
			String categoryField = MotorCategoryEntity.FIELD_NAME_CATEGORY;
			List<Predicate> predicates = new ArrayList<>();
			for (MotorCategory.Category cat : cats) {
				predicates.add(builder.equal(root.get(categoryField), cat));
			}
			Predicate pResult = JPAUtils.buildConjunctPredicate(builder, predicates, LogicalOperator.OR);
			return pResult;
		};
		List<MotorCategoryEntity> entities = motorCategoryRepository.findAll(jpaSpec);
		
		if (entities == null) {
			return new ArrayList<>();
		}
		return entities.stream()
				.map(entity -> motorCategoryMapper.mapToCategoryModel(entity))
				.collect(Collectors.toList());
	}
	
	@Override
	@GetMapping("/stepper/categories/{" + PARAM_NAME_CATEGORY + "}")
	public MotorCategory getMotorCategory(@PathVariable(PARAM_NAME_CATEGORY) MotorCategory.Category category) {
		return super.getMotorCategory(category);
	}

	@GetMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/serieses")
	public PageableListDataResponse<MotorSeries> getMotorSeriesesByCategory(
			@PathVariable(PARAM_NAME_CATEGORY) MotorCategory.Category category,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {

		return super.searchMotorSeriesesByCategory(category, pageNumber, pageSize);
	}

	@Override
	@GetMapping("/stepper/serieses/{" + PARAM_NAME_SERIES + "}")
	public MotorSeries getMotorSeries(@PathVariable(PARAM_NAME_SERIES) String series) {
		return super.getMotorSeries(series);
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
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {
		return super.searchWith(conditionClause, pageNumber, pageSize);
	}

	@PostMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/search")
	public PageableListDataResponse<StepperMotor> searchMotorsByCategory(@RequestBody ConditionClause conditionClause,
			@PathVariable(PARAM_NAME_CATEGORY) MotorCategory.Category category,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {
		return super.searchMotorsByCategoryBySeries(conditionClause, category, null, pageNumber, pageSize);
	}
	
	@Override
	@PostMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/{" + PARAM_NAME_SERIES + "}/search")
	public PageableListDataResponse<StepperMotor> searchMotorsByCategoryBySeries(@RequestBody ConditionClause conditionClause,
			@PathVariable(PARAM_NAME_CATEGORY) MotorCategory.Category category,
			@PathVariable(PARAM_NAME_SERIES) String series,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {
		return super.searchMotorsByCategoryBySeries(conditionClause, category, series, pageNumber, pageSize);
	}
	
	@Override
	@GetMapping("/stepper/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteria() {
		return super.getSearchMetaCriteria();
	}

	@GetMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteriaByCategory(
			@PathVariable(PARAM_NAME_CATEGORY) MotorCategory.Category category) {
		return super.getSearchMetaCriteria();
	}
	
	@GetMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/{" + PARAM_NAME_SERIES + "}/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteriaByCategoryBySeries(
			@PathVariable(PARAM_NAME_CATEGORY) MotorCategory.Category category,
			@PathVariable(PARAM_NAME_SERIES) String series) {
		return super.getSearchMetaCriteria();
	}
	
	@Override
	protected String getModelDisplayName() {
		return "P/N";
	}
	
}
