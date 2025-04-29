package com.exmek.core.gensearch;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.repository.BrakeRepository;
import com.exmek.core.persistence.repository.DCMotorRepository;
import com.exmek.core.persistence.repository.PlanetaryGearboxRepository;
import com.exmek.core.persistence.repository.StepperMotorRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GeneralSearcher {

	@Autowired
	private DCMotorRepository dcMotorRepository;
	
	@Autowired
	private StepperMotorRepository stepperMotorRepository;
	
	@Autowired
	private PlanetaryGearboxRepository planetaryGearboxRepository;
	
	@Autowired
	private BrakeRepository brakeRepository;

	public List<GeneralSearchItem> priorityProductSearch(String keyword) {
		List<GeneralSearchItem> result = new ArrayList<>();
		String matchingField = AbstractProductEntity.FIELD_NAME_MODEL;
		log.info("Searching product by model for keyword {} ", keyword);
		findToAdd(keyword, k -> dcMotorRepository.findByModelContaining(k), GeneralSearchItem.Type.DC_MOTOR, matchingField, result);
		findToAdd(keyword, k -> stepperMotorRepository.findByModelContaining(k), GeneralSearchItem.Type.STEPPER_MOTOR, matchingField, result);
		findToAdd(keyword, k -> planetaryGearboxRepository.findByModelContaining(k), GeneralSearchItem.Type.PLANETARY_GEARBOX, matchingField, result);
		findToAdd(keyword, k -> brakeRepository.findByModelContaining(k), GeneralSearchItem.Type.BRAKE, matchingField, result);
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
				.type(GeneralSearchItem.Type.DC_MOTOR)
				.model(p.getModel())
				.description(p.getDescription())
				.matchingField(matchingField)
				.build())
		);
	}
}
