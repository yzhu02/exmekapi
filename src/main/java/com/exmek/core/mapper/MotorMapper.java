package com.exmek.core.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.exmek.core.model.DCMotor;
import com.exmek.core.model.LeadDef;
import com.exmek.core.model.LinearStepperMotor;
import com.exmek.core.model.MotorSeries;
import com.exmek.core.model.Spec;
import com.exmek.core.model.StepperMotor;
import com.exmek.core.persistence.entity.AbstractDCMotorEntity;
import com.exmek.core.persistence.entity.AbstractMotorEntity;
import com.exmek.core.persistence.entity.AbstractMotorSpecEntity;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.entity.AbstractStepperMotorEntity;
import com.exmek.core.persistence.entity.DCMotorEntity;
import com.exmek.core.persistence.entity.LeadDefEntity;
import com.exmek.core.persistence.entity.StepperMotorEntity;

@Component
public class MotorMapper extends AbstractProductMapper {
	
	static final Set<String> DC_MOTOR_EXCLUDED_FIELDS_TO_SPECS = new HashSet<>(Arrays.asList(
			AbstractProductEntity.FIELD_NAME_SERIES,
			AbstractProductEntity.FIELD_NAME_PRODUCT_SERIES,
			AbstractProductEntity.FIELD_NAME_MODEL,
			AbstractProductEntity.FIELD_NAME_NAME,
			AbstractProductEntity.FIELD_NAME_DESCRIPTION,
			AbstractMotorEntity.FIELD_NAME_CATEGORY,
			AbstractMotorEntity.FIELD_NAME_MOTOR_CATEGORY,
			"specs",
			"perfMeasurements"
			));
	
	static final Set<String> STEPPER_MOTOR_EXCLUDED_FIELDS_TO_SPECS = new HashSet<>(DC_MOTOR_EXCLUDED_FIELDS_TO_SPECS);
	static {
		STEPPER_MOTOR_EXCLUDED_FIELDS_TO_SPECS.add("linearStepperMotorLeads");
	}
	
	@Autowired
	private MotorPerfCurveMapper motorPerfCurveMapper;
	
	public DCMotor mapDCMotorToModel(AbstractDCMotorEntity entity) {
		if (entity == null) {
			return null;
		}
		DCMotor motor = super.mapProduct(entity, DCMotor::new);
		motor.setCategory(entity.getCategory());
		motor.setRatedVoltage(toMeasuredValue(entity.getRatedVoltage(), entity.getRatedVoltageUnit()));
		motor.setRatedCurrent(toMeasuredValue(entity.getRatedCurrent(), entity.getRatedCurrentUnit()));
		motor.setRatedPower(toMeasuredValue(entity.getRatedPower(), entity.getRatedPowerUnit()));
		motor.setRatedTorque(toMeasuredValue(entity.getRatedTorque(), entity.getRatedTorqueUnit()));
		motor.setRatedRotatingSpeed(toMeasuredValue(entity.getRatedRotatingSpeed(), entity.getRatedRotatingSpeedUnit()));
		motor.setRatedLinearSpeed(toMeasuredValue(entity.getRatedLinearSpeed(), entity.getRatedLinearSpeedUnit()));
		motor.setPeakCurrent(toMeasuredValue(entity.getPeakCurrent(), entity.getPeakCurrentUnit()));
		motor.setPeakTorque(toMeasuredValue(entity.getPeakTorque(), entity.getPeakTorqueUnit()));
		motor.setMaxSortingWeight(toMeasuredValue(entity.getMaxSortingWeight(), entity.getMaxSortingWeightUnit()));
		motor.setNoloadCurrent(toMeasuredValue(entity.getNoloadCurrent(), entity.getNoloadCurrentUnit()));
		motor.setNoloadRotatingSpeed(toMeasuredValue(entity.getNoloadRotatingSpeed(), entity.getNoloadRotatingSpeedUnit()));
		
		if (entity instanceof DCMotorEntity) {
			DCMotorEntity fullEntity = (DCMotorEntity) entity;
			motor.setMotorCategory(MotorCategoryMapper.mapEntityToCategory(fullEntity.getMotorCategory(), false));
			motor.setProductSeries(AbstractSeriesMapper.mapEntityToSeries(fullEntity.getProductSeries(), MotorSeries::new, false));
			motor.setAllSpecs(mapAllCombinedSpecs(entity, fullEntity.getSpecs(), appConfigProvider.getSearchDCMotorMetaCriteriaFields(), DC_MOTOR_EXCLUDED_FIELDS_TO_SPECS));
			motor.setPerfCurves(motorPerfCurveMapper.mapToPerfCurveModels(fullEntity.getPerfMeasurements(), entity.getModel()));
			
			motor.setMechanicalImagePaths(resourceManager.getMotorMechanicalImagePaths(entity.getModel(), entity.getSeries()));
			motor.setThreeDDrawingPaths(resourceManager.getMotor3DDrawingPaths(entity.getModel(), entity.getSeries()));
			motor.setTechDocPaths(resourceManager.getMotorTechDocPaths(entity.getModel(), entity.getSeries()));
			motor.setAdditionalImagePaths(resourceManager.getMotorAdditionalImagePaths(entity.getModel(), entity.getSeries()));
		}
		
		return motor;
	}

	List<Spec> mapAllCombinedSpecs(
			AbstractMotorEntity entity,
			Set<? extends AbstractMotorSpecEntity> specEntitities,
			List<String> configuredFields,
			Set<String> excludedFieldNames) {
		List<Spec> allCombinedSpecs = super.mapAllCombinedSpecs(entity, configuredFields, excludedFieldNames);
		Set<String> uniqueSpecNames = allCombinedSpecs.stream().map(Spec::getName).collect(Collectors.toSet());
		List<Spec> motorSpecs = mapMotorSpecsIfNameNotExist(specEntitities, uniqueSpecNames);
		if (!ObjectUtils.isEmpty(motorSpecs)) {
			allCombinedSpecs.addAll(motorSpecs);
		}
		return allCombinedSpecs;
	}
	
	private List<Spec> mapMotorSpecsIfNameNotExist(Set<? extends AbstractMotorSpecEntity> specEntities, Set<String> uniqueSpecNames) {
		if (specEntities == null || specEntities.isEmpty()) {
			return null;
		}
		List<Spec> result = new ArrayList<>();
		for (AbstractMotorSpecEntity specEntity : specEntities) {
			if (!uniqueSpecNames.contains(specEntity.getName())) {
				result.add(mapSpecToModel(specEntity));
			}
		}
		Collections.sort(result, Comparator.comparing(Spec::getName));
		return result;
	}

	private Spec mapSpecToModel(AbstractMotorSpecEntity entity) {
		if (entity == null) {
			return null;
		}
		Spec spec = new Spec();
		spec.setName(entity.getName());
		spec.setValue(entity.getValue());
		spec.setUnit(entity.getUnit());
		return spec;
	}

	public StepperMotor mapStepperMotorToModel(AbstractStepperMotorEntity entity) {
		if (entity == null) {
			return null;
		}
		StepperMotorEntity fullEntity = entity instanceof StepperMotorEntity ? (StepperMotorEntity) entity : null;
		StepperMotor motor = super.mapProduct(entity, () -> {
			if (fullEntity != null && CollectionUtils.isNotEmpty(fullEntity.getLinearStepperMotorLeads())) {
				LinearStepperMotor linearStepperMotor = new LinearStepperMotor();
				linearStepperMotor.setLeads(mapLeadsToModels(fullEntity.getLinearStepperMotorLeads()));
				return linearStepperMotor;
			} else {
				return new StepperMotor();
			}
		});
		motor.setCategory(entity.getCategory());
		motor.setRatedVoltage(toMeasuredValue(entity.getRatedVoltage(), entity.getRatedVoltageUnit()));
		motor.setPhaseCurrent(toMeasuredValue(entity.getPhaseCurrent(), entity.getPhaseCurrentUnit()));
		motor.setPhaseResistance(toMeasuredValue(entity.getPhaseResistance(), entity.getPhaseResistanceUnit()));
		motor.setPhaseInductance(toMeasuredValue(entity.getPhaseInductance(), entity.getPhaseInductanceUnit()));
		motor.setHoldingTorque(toMeasuredValue(entity.getHoldingTorque(), entity.getHoldingTorqueUnit()));
		motor.setDetentTorque(toMeasuredValue(entity.getDetentTorque(), entity.getDetentTorqueUnit()));
		motor.setStepAngle(toMeasuredValue(entity.getStepAngle(), entity.getStepAngleUnit()));
		motor.setMaxThrust(toMeasuredValue(entity.getMaxThrust(), entity.getMaxThrustUnit()));
		
		if (fullEntity != null) {
			motor.setMotorCategory(MotorCategoryMapper.mapEntityToCategory(fullEntity.getMotorCategory(), false));
			motor.setProductSeries(AbstractSeriesMapper.mapEntityToSeries(fullEntity.getProductSeries(), MotorSeries::new, false));
			motor.setAllSpecs(mapAllCombinedSpecs(entity, fullEntity.getSpecs(), appConfigProvider.getSearchStepperMotorMetaCriteriaFields(), STEPPER_MOTOR_EXCLUDED_FIELDS_TO_SPECS));
			if (motor instanceof LinearStepperMotor) {
				motor.setPerfCurves(motorPerfCurveMapper.mapToLinearStepperMotorPerfCurveModels(fullEntity.getPerfMeasurements(), entity.getModel()));
			} else {
				motor.setPerfCurves(motorPerfCurveMapper.mapToPerfCurveModels(fullEntity.getPerfMeasurements(), entity.getModel()));
			}
			
			motor.setMechanicalImagePaths(resourceManager.getMotorMechanicalImagePaths(entity.getModel(), entity.getSeries()));
			motor.setThreeDDrawingPaths(resourceManager.getMotor3DDrawingPaths(entity.getModel(), entity.getSeries()));
			motor.setTechDocPaths(resourceManager.getMotorTechDocPaths(entity.getModel(), entity.getSeries()));
			motor.setAdditionalImagePaths(resourceManager.getMotorAdditionalImagePaths(entity.getModel(), entity.getSeries()));
		}

		return motor;
	}
	
	private List<LeadDef> mapLeadsToModels(Set<LeadDefEntity> leadDefEntities) {
		if (leadDefEntities == null) {
			return null;
		}
		return leadDefEntities.stream()
		.map(lde -> mapLeadToModel(lde))
		.sorted((l1, l2) -> l1.getCode().compareTo(l2.getCode()))
		.collect(Collectors.toList());
	}
	
	private LeadDef mapLeadToModel(LeadDefEntity leadEntity) {
		if (leadEntity == null) {
			return null;
		}
		LeadDef lead = new LeadDef();
//		lead.setId(leadEntity.getId());
		lead.setCode(leadEntity.getCode());
		lead.setLeadInch(leadEntity.getLeadInch());
		lead.setLeadMM(leadEntity.getLeadMM());
		lead.setScrewDiameterInch(leadEntity.getScrewDiameterInch());
		lead.setScrewDiameterMM(leadEntity.getScrewDiameterMM());
		lead.setThreads(leadEntity.getThreads());
		return lead;
	}
}
