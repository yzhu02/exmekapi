package com.exmek.core.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exmek.core.mapper.MotorMapper;
import com.exmek.core.model.DCMotor;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.model.MotorSeries;
import com.exmek.core.persistence.JPAUtils;
import com.exmek.core.persistence.entity.AbstractMotorEntity;
import com.exmek.core.persistence.entity.DCMotorEntity;
import com.exmek.core.persistence.entity.MotorCategoryEntity;
import com.exmek.core.persistence.repository.BaseProductRepository;
import com.exmek.core.persistence.repository.DCMotorRepository;
import com.exmek.core.service.ProductService;

import commons.expr.LogicalOperator;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/motors")
public class DCMotorRestController extends BaseMotorRestController<DCMotorEntity, DCMotor> implements ProductService<DCMotor> {
	
	public static final String QRY_PARAM_VALUE_TYPE_BLDC	= "BLDC";
	public static final String QRY_PARAM_VALUE_TYPE_BRUSH	= "Brush";

	@Autowired
	private DCMotorRepository dcMotorRepository;
	
	@Autowired
	private MotorMapper motorMapper;

	@Override
	protected Class<DCMotorEntity> getEntityClass() {
		return DCMotorEntity.class;
	}

	@Override
	protected BaseProductRepository<DCMotorEntity> getProductRepository() {
		return dcMotorRepository;
	}

	@Override
	protected DCMotor mapEntityToModel(DCMotorEntity entity, boolean comprehensiveMapping) {
		return motorMapper.mapDCMotorToModel(entity, comprehensiveMapping);
	}

	@GetMapping("/DC/categories")
	public List<MotorCategory> getMotorCategories(
			@RequestParam(value = QRY_PARAM_NAME_TYPE, required = false) String type) {
		
		Specification<MotorCategoryEntity> jpaSpec = (root, query, builder) -> {
			MotorCategory.Category[] cats = null;
			if (QRY_PARAM_VALUE_TYPE_BLDC.equalsIgnoreCase(type)) {
				cats = MotorCategory.Category.getCategories(MotorCategory.Supertype.DC, MotorCategory.Type.BLDC);
			} else if (QRY_PARAM_VALUE_TYPE_BRUSH.equalsIgnoreCase(type)) {
				cats = MotorCategory.Category.getCategories(MotorCategory.Supertype.DC, MotorCategory.Type.BRUSH);
			} else {
				cats = MotorCategory.Category.getCategories(MotorCategory.Supertype.DC, null);
			}
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
	@GetMapping("/DC/categories/{" + PARAM_NAME_CATEGORY + "}")
	public MotorCategory getMotorCategory(@PathVariable(PARAM_NAME_CATEGORY) MotorCategory.Category category) {
		return super.getMotorCategory(category);
	}
	
	@GetMapping("/DC/{" + PARAM_NAME_CATEGORY + "}/serieses")
	public PageableListDataResponse<MotorSeries> getMotorSeriesesByCategory(
			@PathVariable(PARAM_NAME_CATEGORY) MotorCategory.Category category,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {

		return super.searchMotorSeriesesByCategory(category, pageNumber, pageSize);
	}
	
	@Override
	@GetMapping("/DC/serieses/{" + PARAM_NAME_SERIES + "}")
	public MotorSeries getMotorSeries(@PathVariable(PARAM_NAME_SERIES) String series) {
		return super.getMotorSeries(series);
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
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {
		return super.searchBy(conditionClause, (root, builder) -> {
			MotorCategory.Category[] cats = null;
			if (QRY_PARAM_VALUE_TYPE_BLDC.equalsIgnoreCase(type)) {
				cats = MotorCategory.Category.getCategories(MotorCategory.Supertype.DC, MotorCategory.Type.BLDC);
			} else if (QRY_PARAM_VALUE_TYPE_BRUSH.equalsIgnoreCase(type)) {
				cats = MotorCategory.Category.getCategories(MotorCategory.Supertype.DC, MotorCategory.Type.BRUSH);
			} else {
				cats = MotorCategory.Category.getCategories(null, null);
			}
			String categoryField = AbstractMotorEntity.FIELD_NAME_CATEGORY;
			List<Predicate> predicates = new ArrayList<>();
			for (MotorCategory.Category cat : cats) {
				predicates.add(builder.equal(root.get(categoryField), cat));
			}
			return Pair.of(JPAUtils.buildConjunctPredicate(builder, predicates, LogicalOperator.OR), LogicalOperator.AND) ;
		}, pageNumber, pageSize);
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
			@PathVariable(PARAM_NAME_CATEGORY) MotorCategory.Category category,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {
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
	@Override
	@PostMapping("/DC/{" + PARAM_NAME_CATEGORY + "}/{" + PARAM_NAME_SERIES + "}/search")
	public PageableListDataResponse<DCMotor> searchMotorsByCategoryBySeries(@RequestBody ConditionClause conditionClause,
			@PathVariable(PARAM_NAME_CATEGORY) MotorCategory.Category category,
			@PathVariable(PARAM_NAME_SERIES) String series,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {
		return super.searchMotorsByCategoryBySeries(conditionClause, category, series, pageNumber, pageSize);
	}
	
	@GetMapping("/DC/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteria(
			@RequestParam(value = QRY_PARAM_NAME_TYPE, required = false) String type) {
		return super.getSearchMetaCriteria();
	}
	
	@GetMapping("/DC/{" + PARAM_NAME_CATEGORY + "}/criteria")
	public SearchMetaCriteriaResponse getSearchMetaCriteriaByCategory(
			@PathVariable(PARAM_NAME_CATEGORY) MotorCategory.Category category) {
		return super.getSearchMetaCriteria();
	}

	@GetMapping("/DC/{" + PARAM_NAME_CATEGORY + "}/{" + PARAM_NAME_SERIES + "}/criteria")
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
