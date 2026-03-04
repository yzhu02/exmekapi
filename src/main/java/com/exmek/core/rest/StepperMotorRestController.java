package com.exmek.core.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exmek.commons.expr.LogicalOperator;
import com.exmek.commons.utils.ReflectionUtils;
import com.exmek.core.commons.model.Range;
import com.exmek.core.consts.EndpointConsts;
import com.exmek.core.helper.MetaCriteriaKey;
import com.exmek.core.mapper.MotorMapper;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.model.MotorSeries;
import com.exmek.core.model.StepperMotor;
import com.exmek.core.persistence.entity.LeadDefEntity;
import com.exmek.core.persistence.entity.LightweightLeadFlattenStepperMotorEntity;
import com.exmek.core.persistence.entity.LightweightStepperMotorEntity;
import com.exmek.core.persistence.entity.StepperMotorCategoryEntity;
import com.exmek.core.persistence.entity.StepperMotorEntity;
import com.exmek.core.persistence.entity.StepperMotorSeriesEntity;
import com.exmek.core.persistence.repository.LightweightLeadFlattenStepperMotorRepository;
import com.exmek.core.persistence.repository.LightweightStepperMotorRepository;
import com.exmek.core.persistence.repository.StepperMotorCategoryRepository;
import com.exmek.core.persistence.repository.StepperMotorRepository;
import com.exmek.core.persistence.repository.StepperMotorSeriesRepository;
import com.exmek.core.service.ProductService;
import com.exmek.core.utils.MotorUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(EndpointConsts.ENDPOINT_API_MOTORS)
public class StepperMotorRestController 
extends BaseMotorRestController<StepperMotorEntity, LightweightStepperMotorEntity, StepperMotor, StepperMotorSeriesEntity, StepperMotorCategoryEntity> 
implements ProductService<StepperMotor> {

	@Autowired
	protected StepperMotorCategoryRepository motorCategoryRepository;

	@Autowired
	protected StepperMotorSeriesRepository motorSeriesRepository;
	
	@Autowired
	private StepperMotorRepository motorRepository;

	@Autowired
	private LightweightStepperMotorRepository lightweightMotorRepository;
	
	@Autowired
	private LightweightLeadFlattenStepperMotorRepository lightweightLeadFlattenStepperMotorRepository;
		
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
	protected LightweightStepperMotorRepository getLightweightProductRepository() {
		return lightweightMotorRepository;
	}

	@Override
	protected StepperMotor mapEntityToModel(StepperMotorEntity entity) {
		return motorMapper.mapStepperMotorToModel(entity);
	}

	@Override
	protected StepperMotor mapLightweightEntityToModel(LightweightStepperMotorEntity entity) {
		return motorMapper.mapLightweightStepperMotorToModel(entity);
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

	@Override
	@GetMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/serieses")
	public PageableListDataResponse<MotorSeries> getMotorSeriesesByCategory(
			@PathVariable(PARAM_NAME_CATEGORY) String category,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize) {

		return super.getMotorSeriesesByCategory(category, pageNumber, pageSize);
	}

	@Override
	@GetMapping("/stepper/serieses/{" + PARAM_NAME_SERIES + "}")
	public MotorSeries getSeries(@PathVariable(PARAM_NAME_SERIES) String series) {
		return super.getSeries(series);
	}

	@GetMapping("/stepper/{idOrModel}")
	public StepperMotor getMotor(@NotNull @PathVariable("idOrModel") String idOrModel) {
		StepperMotor stepperMotor = null;
		if (BooleanUtils.isTrue(appConfigProvider.getLinearStepperMotorModelFlattenWithLeadCodeEnabled())
				&& MotorUtils.maybeLinearStepperMotor(idOrModel)) {

			String[] modelAndLeadCode = idOrModel.split("-");
			stepperMotor = getLeadFlattenLinearStepperMotor(modelAndLeadCode[0], modelAndLeadCode[1]);
		}
		if (stepperMotor == null) {
			stepperMotor = super.getProduct(idOrModel);
		}
		return stepperMotor;
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
		return searchWith(conditionClause, pageNumber, pageSize);
	}

	@PostMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/search")
	public PageableListDataResponse<? extends StepperMotor> searchMotorsByCategory(@RequestBody ConditionClause conditionClause,
			@PathVariable(PARAM_NAME_CATEGORY) String category,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = QRY_PARAM_NAME_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = QRY_PARAM_NAME_FETCH_ALL, required = false) Boolean fetchAll) {
		
		validateSearchRequest(conditionClause, pageNumber, pageSize, fetchAll);
		return super.searchMotorsByCategoryBySeries(conditionClause, category, null, pageNumber, pageSize);
	}
	
	@PostMapping("/stepper/{" + PARAM_NAME_CATEGORY + "}/{" + PARAM_NAME_SERIES + "}/search")
	public PageableListDataResponse<? extends StepperMotor> searchMotorsByCategoryBySeries(@RequestBody ConditionClause conditionClause,
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

	
	
	
	//// Handle Lead Flatten Linear Stepper Motors - begin ////
	private StepperMotor getLeadFlattenLinearStepperMotor(String linearMotorBaseModel, String leadCode) {
		Optional<StepperMotorEntity> opEntity = getProductRepository().findByModel(linearMotorBaseModel);
		if (opEntity.isPresent()) {
			return motorMapper.mapToLeadFlattenLinearStepperMotor(opEntity.get(), leadCode);
		} else {
			return null;
		}
	}
	
	@Override
	protected PageableListDataResponse<StepperMotor> searchWith(ConditionClause conditionClause,
			List<Pair<String, Object>> additionalFieldMatching, Integer pageNumber, Integer pageSize,
			Map<String, Set<Object>> dataAvailableUnitsOfFieldNames) {
		
		if (BooleanUtils.isTrue(appConfigProvider.getLinearStepperMotorModelFlattenWithLeadCodeEnabled())) {
			return searchWithLeadFlattenStepperMotors(conditionClause, additionalFieldMatching, pageNumber, pageSize, dataAvailableUnitsOfFieldNames);
		} else {
			return super.searchWith(conditionClause, additionalFieldMatching, pageNumber, pageSize, dataAvailableUnitsOfFieldNames);
		}
	}
	
	// Search for any StepperMotor but in case of LinearStepperMotor then flatten it with lead codes
	private PageableListDataResponse<StepperMotor> searchWithLeadFlattenStepperMotors(ConditionClause conditionClause,
			List<Pair<String, Object>> additionalFieldMatching, Integer pageNumber, Integer pageSize,
			Map<String, Set<Object>> dataAvailableUnitsOfFieldNames) {
		
		resolveLeadFlattenModelAndLeadCode(conditionClause.getConditions());
		
		PageableListDataResponse<StepperMotor> dataResponse = new PageableListDataResponse<>();
		
		BiFunction<Root<LightweightLeadFlattenStepperMotorEntity>, CriteriaBuilder, Pair<Predicate, LogicalOperator>> fAdditionalCondition = (root, builder) -> {
			return toPredicateWithOperator(additionalFieldMatching, root, builder);
		};
		
//		List<LightweightLeadFlattenStepperMotorEntity> entities = null;
//		if (pageNumber == null || pageSize == null) {
//			entities = productSearcher.search(lightweightLeadFlattenStepperMotorRepository, 
//					conditionClause, 
//					fAdditionalCondition, 
//					dataAvailableUnitsOfFieldNames);
//		} else {
//			Page<LightweightLeadFlattenStepperMotorEntity> page = productSearcher.search(lightweightLeadFlattenStepperMotorRepository, 
//					conditionClause, 
//					fAdditionalCondition, 
//					pageNumber, pageSize, 
//					dataAvailableUnitsOfFieldNames);
//			entities = page.getContent();
//			ContentUtils.populatePageableListDataResponse(dataResponse, page);
//		}
		
		
		List<LightweightLeadFlattenStepperMotorEntity> entities = productSearcher.search(lightweightLeadFlattenStepperMotorRepository, 
				conditionClause, 
				fAdditionalCondition, 
				dataAvailableUnitsOfFieldNames);
		
		List<StepperMotor> stepperMotors = new ArrayList<>();
		if (pageNumber == null || pageSize == null) {
			entities.forEach(entity -> {
				if (StringUtils.equals(entity.getCategory(), MotorCategory.STEPPER_LINEAR)) {
					stepperMotors.addAll(motorMapper.mapToLeadFlattenLinearStepperMotors(entity));
				} else {
					stepperMotors.add(motorMapper.mapLightweightStepperMotorToModel(entity));
				}
			});
		} else {
			Integer offset = pageNumber * pageSize;
			int currFlattenPosition = 0;
			for (int i = 0; i < entities.size(); i++) {
				if (stepperMotors.size() >= pageSize) {
					break;
				}
				LightweightLeadFlattenStepperMotorEntity entity = entities.get(i);
				Set<LeadDefEntity> linearStepperMotorLeads = entity.getLinearStepperMotorLeads();
				if (CollectionUtils.isEmpty(linearStepperMotorLeads)) {
					if (currFlattenPosition >= offset) {
						stepperMotors.add(motorMapper.mapLightweightStepperMotorToModel(entity));
					}
					currFlattenPosition++;
				} else {
					if (currFlattenPosition + linearStepperMotorLeads.size() < offset + pageSize) {
						stepperMotors.addAll(motorMapper.mapToLeadFlattenLinearStepperMotors(entity));
						currFlattenPosition += linearStepperMotorLeads.size();
					} else {
						List<LeadDefEntity> leadEntities = new ArrayList<>(entity.getLinearStepperMotorLeads());
						Collections.sort(leadEntities, (ld0, ld1) -> ld0.getCode().compareTo(ld1.getCode()));
						int limitN = offset + pageSize - currFlattenPosition;
						List<LeadDefEntity> subLeadEntities = leadEntities.subList(0, limitN);
						stepperMotors.addAll(motorMapper.mapToLeadFlattenLinearStepperMotors(entity, subLeadEntities));
						currFlattenPosition += limitN;
					}
				}

			}
			dataResponse.setPageNumber(pageNumber);
			dataResponse.setPageSize(pageSize);
			dataResponse.setTotalElementsOfCurrPage(stepperMotors.size());
			
			
			// Calculate totalElementsOfAllPages and totalPages
			int totalInAll = 0;
			for (int i = 0; i < entities.size(); i++) {
				LightweightLeadFlattenStepperMotorEntity entity = entities.get(i);
				Set<LeadDefEntity> linearStepperMotorLeads = entity.getLinearStepperMotorLeads();
				if (CollectionUtils.isEmpty(linearStepperMotorLeads)) {
					totalInAll++;
				} else {
					totalInAll += linearStepperMotorLeads.size();
				}
			}
			dataResponse.setTotalElementsOfAllPages(totalInAll);
			dataResponse.setTotalPages(totalInAll / pageSize);
		}
		
		dataResponse.setData(stepperMotors);
		
		return dataResponse;
	}

    private void resolveLeadFlattenModelAndLeadCode(List<String> conditions) {
		if (CollectionUtils.isEmpty(conditions)) {
			return;
		}
		String modelLikePrefix = "model LIKE ";
		List<String> toAddLeadCodeConditions = new ArrayList<>();
		for (int i = 0; i < conditions.size(); i++) {
			String c = conditions.get(i);
			if (c.startsWith(modelLikePrefix)) {
				ConditionLine cl = ConditionLine.parse(c);
				Pair<String, String> pModelAndLeadCode = parseLinearStepperMotorModelAndLeadCode(cl.getValue()); // like "LS057NB103-K0050"
				if (pModelAndLeadCode != null) {
					conditions.set(i, modelLikePrefix + pModelAndLeadCode.getLeft());
					toAddLeadCodeConditions.add("code LIKE " + pModelAndLeadCode.getRight());
				}
			}
		}
		if (CollectionUtils.isNotEmpty(toAddLeadCodeConditions)) {
			for (String toAddLeadCode : toAddLeadCodeConditions) {
				conditions.add(toAddLeadCode);
			}
		}
	}

    private Pair<String, String> parseLinearStepperMotorModelAndLeadCode(String linearStepperMotorCombinedModel) {
    	if (linearStepperMotorCombinedModel.contains("-")) {
			String[] splitted = linearStepperMotorCombinedModel.split("-");
			return Pair.of(splitted[0], splitted[1]);
		}
    	return null;
    }

//    private PageableListDataResponse<LeadFlattenLinearStepperMotor> searchLeadFlattenLinearStepperMotors(ConditionClause conditionClause,
//			String category, String series,
//			Integer pageNumber, Integer pageSize) {
//
//		PageableListDataResponse<LeadFlattenLinearStepperMotor> dataResponse = new PageableListDataResponse<>();
//		
//		Page<LightweightLeadFlattenLinearStepperMotorProjection> page = leadFlattenLinearStepperMotorRepository
//				.findLightweightLeadFlattenLinearStepperMotorProjections(conditionClause, category, series, pageNumber, pageSize);
//		List<LightweightLeadFlattenLinearStepperMotorProjection> projectionData = page.getContent();
//		if (CollectionUtils.isNotEmpty(projectionData)) {
//			dataResponse.setData(projectionData.stream()
//					.map(motorMapper::mapToLeadFlattenLinearStepperMotor)
//					.collect(Collectors.toList())
//					);
//		}
//		if (pageNumber != null && pageSize != null) {
//			ContentUtils.populatePageableListDataResponse(dataResponse, page);
//		}
//		
//		return dataResponse;
//	}

    ////Handle Lead Flatten Linear Stepper Motors - end ////
}
