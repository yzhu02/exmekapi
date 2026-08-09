package com.exmek.core.gensearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import com.exmek.core.persistence.repository.LinearActuatorRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.entity.LeadDefEntity;
import com.exmek.core.persistence.entity.StepperMotorEntity;
import com.exmek.core.persistence.repository.BrakeRepository;
import com.exmek.core.persistence.repository.DCMotorRepository;
import com.exmek.core.persistence.repository.PlanetaryGearboxRepository;
import com.exmek.core.persistence.repository.StepperMotorRepository;
import com.exmek.core.utils.MotorUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GeneralSearcher {

	@Autowired
	protected AppConfigProvider appConfigProvider;
	
	@Autowired
	private DCMotorRepository dcMotorRepository;
	
	@Autowired
	private StepperMotorRepository stepperMotorRepository;
	
	@Autowired
	private PlanetaryGearboxRepository planetaryGearboxRepository;
	
	@Autowired
	private BrakeRepository brakeRepository;

  @Autowired
  private LinearActuatorRepository linearActuatorRepository;

	public List<GeneralSearchItem> priorityProductSearch(String keyword) {
		List<GeneralSearchItem> result = new ArrayList<>();
		String matchingField = AbstractProductEntity.FIELD_NAME_MODEL;
		log.info("Searching product by model for keyword {} ", keyword);
		findToAdd(keyword, k -> dcMotorRepository.findByModelContaining(k), GeneralSearchItem.Type.DC_MOTOR, matchingField, result);
		findToAdd(keyword, k -> stepperMotorRepository.findByModelContaining(k), GeneralSearchItem.Type.STEPPER_MOTOR, matchingField, result);
		if (BooleanUtils.isTrue(appConfigProvider.getLinearStepperMotorModelFlattenWithLeadCodeEnabled())
				&& MotorUtils.isLeadFlattenLinearStepperMotor(keyword)) {
			String[] modelAndLeadCode = keyword.split("-");
			Optional<StepperMotorEntity> opEntity = stepperMotorRepository.findByModel(modelAndLeadCode[0]);
			if (opEntity.isPresent()) {
				StepperMotorEntity linearStepperMotorEntity = opEntity.get();
				Set<LeadDefEntity> leads = linearStepperMotorEntity.getLinearStepperMotorLeads();
				if (leads != null) {
					if (leads.stream().anyMatch(lead -> StringUtils.equals(lead.getCode(), modelAndLeadCode[1]))) {
						result.add(GeneralSearchItem.builder()
								.type(GeneralSearchItem.Type.STEPPER_MOTOR)
								.model(MotorUtils.makeLinearStepperMotorLeadFlattenModel(linearStepperMotorEntity.getModel(), modelAndLeadCode[1]))
								.series(linearStepperMotorEntity.getSeries())
								.description(linearStepperMotorEntity.getDescription())
								.matchingField(matchingField)
								.build());
					}
				}
			}
		}
		findToAdd(keyword, k -> planetaryGearboxRepository.findByModelContaining(k), GeneralSearchItem.Type.PLANETARY_GEARBOX, matchingField, result);
		findToAdd(keyword, k -> brakeRepository.findByModelContaining(k), GeneralSearchItem.Type.BRAKE, matchingField, result);
    findToAdd(keyword, k -> linearActuatorRepository.findByModelContaining(k), GeneralSearchItem.Type.LINEAR_ACTUATOR, matchingField, result);
		return result;
	}

	public List<GeneralSearchItem> alternativeProductSearch(String keyword) {
		List<GeneralSearchItem> result = new ArrayList<>();
		String matchingField = AbstractProductEntity.FIELD_NAME_DESCRIPTION;
		log.info("Searching product by description for keyword {} ", keyword);
		findToAdd(keyword, k -> dcMotorRepository.findByDescriptionContaining(k), GeneralSearchItem.Type.DC_MOTOR, matchingField, result);
		findToAdd(keyword, k -> stepperMotorRepository.findByDescriptionContaining(k), GeneralSearchItem.Type.STEPPER_MOTOR, matchingField, result);
		findToAdd(keyword, k -> planetaryGearboxRepository.findByDescriptionContaining(k), GeneralSearchItem.Type.PLANETARY_GEARBOX, matchingField, result);
		findToAdd(keyword, k -> brakeRepository.findByDescriptionContaining(k), GeneralSearchItem.Type.BRAKE, matchingField, result);
    findToAdd(keyword, k -> linearActuatorRepository.findByDescriptionContaining(k), GeneralSearchItem.Type.LINEAR_ACTUATOR, matchingField, result);
		return result;
	}

	private <T extends AbstractProductEntity> void findToAdd(
			String keyword, 
			Function<String, List<T>> finder, 
			GeneralSearchItem.Type type,
			String matchingField,
			List<GeneralSearchItem> result) {

		List<T> foundProducts = finder.apply(keyword);
		if (ObjectUtils.isEmpty(foundProducts)) {
			return;
		}
		foundProducts.forEach(p -> result.add(GeneralSearchItem.builder()
				.type(type)
				.model(p.getModel())
				.series(p.getSeries())
				.description(p.getDescription())
				.matchingField(matchingField)
				.build())
		);
	}
}
