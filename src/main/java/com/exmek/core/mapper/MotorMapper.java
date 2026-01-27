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

import com.exmek.core.model.AbstractMotor;
import com.exmek.core.model.DCMotor;
import com.exmek.core.model.LeadDef;
import com.exmek.core.model.LeadFlattenLinearStepperMotor;
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
import com.exmek.core.persistence.entity.LightweightDCMotorEntity;
import com.exmek.core.persistence.entity.LightweightStepperMotorEntity;
import com.exmek.core.persistence.entity.StepperMotorEntity;
import com.exmek.core.persistence.projection.LightweightLeadFlattenLinearStepperMotorProjection;

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
	
	private void performBasicDCMotorMapping(DCMotor motor, AbstractDCMotorEntity entity) {
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
	}
	
	public DCMotor mapLightweightDCMotorToModel(LightweightDCMotorEntity entity) {
		if (entity == null) {
			return null;
		}
		DCMotor motor = super.mapProduct(entity, DCMotor::new);
		performBasicDCMotorMapping(motor, entity);
		return motor;
	}

	public DCMotor mapDCMotorToModel(DCMotorEntity entity) {
		if (entity == null) {
			return null;
		}
		DCMotor motor = super.mapProduct(entity, DCMotor::new);
		performBasicDCMotorMapping(motor, entity);
		
		motor.setMotorCategory(MotorCategoryMapper.mapEntityToCategory(entity.getMotorCategory(), false));
		motor.setProductSeries(AbstractSeriesMapper.mapEntityToSeries(entity.getProductSeries(), MotorSeries::new, false));
		motor.setAllSpecs(mapAllCombinedSpecs(entity, entity.getSpecs(), appConfigProvider.getSearchDCMotorMetaCriteriaFields(), DC_MOTOR_EXCLUDED_FIELDS_TO_SPECS));
		motor.setPerfCurves(motorPerfCurveMapper.mapToPerfCurveModels(entity.getPerfMeasurements(), entity.getModel()));
		
		performResourceMapping(motor, entity);
		
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

	private void performResourceMapping(AbstractMotor motor, AbstractMotorEntity entity) {
		motor.setMechanicalImagePaths(resourceManager.getMotorMechanicalImagePaths(entity.getModel(), entity.getSeries()));
		motor.setThreeDModelPaths(resourceManager.getMotor3DModelPaths(entity.getModel(), entity.getSeries()));
		motor.setThreeDViewPaths(resourceManager.getMotor3DViewPaths(entity.getModel(), entity.getSeries()));
		motor.setTechDocPaths(resourceManager.getMotorTechDocPaths(entity.getModel(), entity.getSeries()));
		motor.setAdditionalImagePaths(resourceManager.getMotorAdditionalImagePaths(entity.getModel(), entity.getSeries()));
	}

	private void performBasicStepperMotorMapping(StepperMotor motor, AbstractStepperMotorEntity entity) {
		motor.setCategory(entity.getCategory());
		motor.setRatedVoltage(toMeasuredValue(entity.getRatedVoltage(), entity.getRatedVoltageUnit()));
		motor.setPhaseCurrent(toMeasuredValue(entity.getPhaseCurrent(), entity.getPhaseCurrentUnit()));
		motor.setPhaseResistance(toMeasuredValue(entity.getPhaseResistance(), entity.getPhaseResistanceUnit()));
		motor.setPhaseInductance(toMeasuredValue(entity.getPhaseInductance(), entity.getPhaseInductanceUnit()));
		motor.setHoldingTorque(toMeasuredValue(entity.getHoldingTorque(), entity.getHoldingTorqueUnit()));
		motor.setDetentTorque(toMeasuredValue(entity.getDetentTorque(), entity.getDetentTorqueUnit()));
		motor.setStepAngle(toMeasuredValue(entity.getStepAngle(), entity.getStepAngleUnit()));
		motor.setMaxThrust(toMeasuredValue(entity.getMaxThrust(), entity.getMaxThrustUnit()));
	}

	public StepperMotor mapLightweightStepperMotorToModel(LightweightStepperMotorEntity entity) {
		if (entity == null) {
			return null;
		}
		StepperMotor motor = super.mapProduct(entity, StepperMotor::new);
		performBasicStepperMotorMapping(motor, entity);
		return motor;
	}

	public StepperMotor mapStepperMotorToModel(StepperMotorEntity entity) {
		if (entity == null) {
			return null;
		}
		StepperMotor motor = super.mapProduct(entity, () -> {
			if (CollectionUtils.isNotEmpty(entity.getLinearStepperMotorLeads())) {
				LinearStepperMotor linearStepperMotor = new LinearStepperMotor();
				linearStepperMotor.setLeads(mapLeadsToModels(entity.getLinearStepperMotorLeads()));
				return linearStepperMotor;
			} else {
				return new StepperMotor();
			}
		});
		performBasicStepperMotorMapping(motor, entity);
		
		motor.setMotorCategory(MotorCategoryMapper.mapEntityToCategory(entity.getMotorCategory(), false));
		motor.setProductSeries(AbstractSeriesMapper.mapEntityToSeries(entity.getProductSeries(), MotorSeries::new, false));
		motor.setAllSpecs(mapAllCombinedSpecs(entity, entity.getSpecs(), appConfigProvider.getSearchStepperMotorMetaCriteriaFields(), STEPPER_MOTOR_EXCLUDED_FIELDS_TO_SPECS));
		if (motor instanceof LinearStepperMotor) {
			motor.setPerfCurves(motorPerfCurveMapper.mapToLinearStepperMotorPerfCurveModels(entity.getPerfMeasurements(), entity.getModel()));
		} else {
			motor.setPerfCurves(motorPerfCurveMapper.mapToPerfCurveModels(entity.getPerfMeasurements(), entity.getModel()));
		}
		
		performResourceMapping(motor, entity);

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

	public LeadFlattenLinearStepperMotor mapToLeadFlattenLinearStepperMotor(LightweightLeadFlattenLinearStepperMotorProjection source) {
		LeadFlattenLinearStepperMotor linearStepperMotor = super.mapProduct(source, LeadFlattenLinearStepperMotor::new);
		performBasicStepperMotorMapping(linearStepperMotor, source);
		linearStepperMotor.setModel(source.getModel() + "-" + source.getCode()); // Overwrite the model by appending "-{LEAD_CODE}"
		linearStepperMotor.setLeadCode(source.getCode());
		linearStepperMotor.setScrewDiameterInch(source.getScrewDiameterInch());
		linearStepperMotor.setScrewDiameterMM(source.getScrewDiameterMM());
		linearStepperMotor.setLeadInch(source.getLeadInch());
		linearStepperMotor.setLeadMM(source.getLeadMM());
		linearStepperMotor.setThreads(source.getThreads());
		return linearStepperMotor;
	}
}
