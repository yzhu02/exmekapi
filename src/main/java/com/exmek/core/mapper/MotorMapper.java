package com.exmek.core.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.exmek.core.model.IntegratedStepperMotor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.exmek.core.persistence.entity.LightweightLeadFlattenStepperMotorEntity;
import com.exmek.core.persistence.entity.StepperMotorEntity;
import com.exmek.core.persistence.projection.LeadFlattenLinearStepperMotorProjection;
import com.exmek.core.persistence.projection.LightweightLeadFlattenLinearStepperMotorProjection;
import com.exmek.core.utils.MotorUtils;

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
		motor.setAllSpecs(mapAllCombinedSpecs(entity, entity.getSpecs(), appConfigProvider.getSearchDCMotorMetaCriteriaFieldsDefault(), DC_MOTOR_EXCLUDED_FIELDS_TO_SPECS));
		motor.setPerfCurves(motorPerfCurveMapper.mapToPerfCurveModels(entity.getPerfMeasurements(), entity.getModel()));
		
		performResourceMapping(motor, entity);
		
		return motor;
	}

	List<Spec> mapAllCombinedSpecs(
			AbstractMotorEntity entity,
			Set<? extends AbstractMotorSpecEntity> specEntities,
			List<String> configuredFields,
			Set<String> excludedFieldNames) {
		List<Spec> allCombinedSpecs = super.mapAllCombinedSpecs(entity, configuredFields, excludedFieldNames);
		Set<String> uniqueSpecNames = allCombinedSpecs.stream().map(Spec::getName).collect(Collectors.toSet());
		List<Spec> motorSpecs = mapMotorSpecsIfNameNotExist(specEntities, uniqueSpecNames);
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
		motor.setRatedCurrent(toMeasuredValue(entity.getRatedCurrent(), entity.getRatedCurrentUnit()));
		motor.setPeakCurrent(toMeasuredValue(entity.getPeakCurrent(), entity.getPeakCurrentUnit()));
		motor.setPhaseCurrent(toMeasuredValue(entity.getPhaseCurrent(), entity.getPhaseCurrentUnit()));
		motor.setPhaseResistance(toMeasuredValue(entity.getPhaseResistance(), entity.getPhaseResistanceUnit()));
		motor.setPhaseInductance(toMeasuredValue(entity.getPhaseInductance(), entity.getPhaseInductanceUnit()));
		motor.setHoldingTorque(toMeasuredValue(entity.getHoldingTorque(), entity.getHoldingTorqueUnit()));
		motor.setDetentTorque(toMeasuredValue(entity.getDetentTorque(), entity.getDetentTorqueUnit()));
		motor.setStepAngle(toMeasuredValue(entity.getStepAngle(), entity.getStepAngleUnit()));
		motor.setMaxThrust(toMeasuredValue(entity.getMaxThrust(), entity.getMaxThrustUnit()));
	}

	public StepperMotor mapLightweightStepperMotorToModel(AbstractStepperMotorEntity entity) {
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
		motor.setAllSpecs(mapAllCombinedSpecs(entity, entity.getSpecs(), appConfigProvider.getSearchStepperMotorMetaCriteriaFieldsDefault(), STEPPER_MOTOR_EXCLUDED_FIELDS_TO_SPECS));
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

	
	
	public LeadFlattenLinearStepperMotor mapToLeadFlattenLinearStepperMotor(StepperMotorEntity entity, String leadCode) {
		LeadFlattenLinearStepperMotor linearStepperMotor = super.mapProduct(entity, LeadFlattenLinearStepperMotor::new);
		performBasicStepperMotorMapping(linearStepperMotor, entity);
		linearStepperMotor.setMotorCategory(MotorCategoryMapper.mapEntityToCategory(entity.getMotorCategory(), false));
		linearStepperMotor.setProductSeries(AbstractSeriesMapper.mapEntityToSeries(entity.getProductSeries(), MotorSeries::new, false));
		linearStepperMotor.setAllSpecs(mapAllCombinedSpecs(entity, entity.getSpecs(), appConfigProvider.getSearchStepperMotorMetaCriteriaFieldsDefault(), STEPPER_MOTOR_EXCLUDED_FIELDS_TO_SPECS));
		linearStepperMotor.setPerfCurves(motorPerfCurveMapper.mapToLinearStepperMotorPerfCurveModels(entity.getPerfMeasurements(), entity.getModel()));
		performResourceMapping(linearStepperMotor, entity);
		
		Set<LeadDefEntity> leads = entity.getLinearStepperMotorLeads();
		if (CollectionUtils.isEmpty(leads)) {
			return linearStepperMotor;
		}
		Optional<LeadDefEntity> opLeadEntity = leads.stream()
			.filter(lead -> StringUtils.equals(lead.getCode(), leadCode))
			.findFirst();
		if (opLeadEntity.isPresent()) {
			LeadDefEntity leadEntity = opLeadEntity.get();
			linearStepperMotor.setModel(MotorUtils.makeLinearStepperMotorLeadFlattenModel(entity.getModel(), leadEntity.getCode())); // Overwrite the model by appending "-{LEAD_CODE}"
      populateLeadProperties(linearStepperMotor, leadEntity);
		}
		return linearStepperMotor;
	}

  private void populateLeadProperties(LeadFlattenLinearStepperMotor linearStepperMotor, LeadDefEntity leadEntity) {
    linearStepperMotor.setLeadCode(leadEntity.getCode());
    linearStepperMotor.setScrewDiameterInch(leadEntity.getScrewDiameterInch());
    linearStepperMotor.setScrewDiameterMM(leadEntity.getScrewDiameterMM());
    linearStepperMotor.setLeadInch(leadEntity.getLeadInch());
    linearStepperMotor.setLeadMM(leadEntity.getLeadMM());
    linearStepperMotor.setThreads(leadEntity.getThreads());
  }
	
	public List<LeadFlattenLinearStepperMotor> mapToLeadFlattenLinearStepperMotors(LightweightLeadFlattenStepperMotorEntity entity) {
		return mapToLeadFlattenLinearStepperMotors(entity, entity.getLinearStepperMotorLeads());
	}

	// To map to List of LeadFlattenLinearStepperMotor with specified Collection of LeadDefEntity
	public List<LeadFlattenLinearStepperMotor> mapToLeadFlattenLinearStepperMotors(AbstractStepperMotorEntity entity, Collection<LeadDefEntity> leadEntities) {
		if (CollectionUtils.isEmpty(leadEntities)) {
			LeadFlattenLinearStepperMotor linearStepperMotor = super.mapProduct(entity, LeadFlattenLinearStepperMotor::new);
			performBasicStepperMotorMapping(linearStepperMotor, entity);
			return List.of(linearStepperMotor);
		}
		List<LeadFlattenLinearStepperMotor> flattenLinearStepperMotors = new ArrayList<>();
		for (LeadDefEntity leadEntity : leadEntities) {
			flattenLinearStepperMotors.add(mapToLeadFlattenLinearStepperMotor(entity, leadEntity));
		}
		return flattenLinearStepperMotors;
	}
	
	public LeadFlattenLinearStepperMotor mapToLeadFlattenLinearStepperMotor(AbstractStepperMotorEntity entity, LeadDefEntity leadEntity) {
		LeadFlattenLinearStepperMotor linearStepperMotor = super.mapProduct(entity, LeadFlattenLinearStepperMotor::new);
		performBasicStepperMotorMapping(linearStepperMotor, entity);
		linearStepperMotor.setModel(MotorUtils.makeLinearStepperMotorLeadFlattenModel(entity.getModel(), leadEntity.getCode())); // Overwrite the model by appending "-{LEAD_CODE}"
    populateLeadProperties(linearStepperMotor, leadEntity);
		return linearStepperMotor;
	}
	
	@Deprecated
	public LeadFlattenLinearStepperMotor mapToLeadFlattenLinearStepperMotor(LightweightLeadFlattenLinearStepperMotorProjection source) {
		LeadFlattenLinearStepperMotor linearStepperMotor = super.mapProduct(source, LeadFlattenLinearStepperMotor::new);
		performBasicStepperMotorMapping(linearStepperMotor, source);
		if (StringUtils.isNotEmpty(source.getCode())) {
			linearStepperMotor.setModel(MotorUtils.makeLinearStepperMotorLeadFlattenModel(source.getModel(), source.getCode())); // Overwrite the model by appending "-{LEAD_CODE}"
		}
		linearStepperMotor.setLeadCode(source.getCode());
		linearStepperMotor.setScrewDiameterInch(source.getScrewDiameterInch());
		linearStepperMotor.setScrewDiameterMM(source.getScrewDiameterMM());
		linearStepperMotor.setLeadInch(source.getLeadInch());
		linearStepperMotor.setLeadMM(source.getLeadMM());
		linearStepperMotor.setThreads(source.getThreads());
		return linearStepperMotor;
	}

	@Deprecated
	public LeadFlattenLinearStepperMotor mapToLeadFlattenLinearStepperMotor(LeadFlattenLinearStepperMotorProjection source) {
		LeadFlattenLinearStepperMotor linearStepperMotor = super.mapProduct(source, LeadFlattenLinearStepperMotor::new);
		performBasicStepperMotorMapping(linearStepperMotor, source);
		if (StringUtils.isNotEmpty(source.getCode())) {
			linearStepperMotor.setModel(MotorUtils.makeLinearStepperMotorLeadFlattenModel(source.getModel(), source.getCode())); // Overwrite the model by appending "-{LEAD_CODE}"
		}
		
		linearStepperMotor.setMotorCategory(MotorCategoryMapper.mapEntityToCategory(source.getMotorCategory(), false));
		linearStepperMotor.setProductSeries(AbstractSeriesMapper.mapEntityToSeries(source.getProductSeries(), MotorSeries::new, false));
		linearStepperMotor.setAllSpecs(mapAllCombinedSpecs(source, source.getSpecs(), appConfigProvider.getSearchStepperMotorMetaCriteriaFieldsDefault(), STEPPER_MOTOR_EXCLUDED_FIELDS_TO_SPECS));
		linearStepperMotor.setPerfCurves(motorPerfCurveMapper.mapToLinearStepperMotorPerfCurveModels(source.getPerfMeasurements(), source.getModel()));
		
		performResourceMapping(linearStepperMotor, source);
		
		linearStepperMotor.setLeadCode(source.getCode());
		linearStepperMotor.setScrewDiameterInch(source.getScrewDiameterInch());
		linearStepperMotor.setScrewDiameterMM(source.getScrewDiameterMM());
		linearStepperMotor.setLeadInch(source.getLeadInch());
		linearStepperMotor.setLeadMM(source.getLeadMM());
		linearStepperMotor.setThreads(source.getThreads());
		return linearStepperMotor;
	}


  public IntegratedStepperMotor mapToLightIntegratedStepperMotor(StepperMotorEntity entity) {
    IntegratedStepperMotor integratedStepperMotor = super.mapProduct(entity, IntegratedStepperMotor::new);
    performBasicStepperMotorMapping(integratedStepperMotor, entity);
    Set<? extends AbstractMotorSpecEntity> specEntities = entity.getSpecs();
    if (CollectionUtils.isNotEmpty(specEntities)) {
      for (AbstractMotorSpecEntity specEntity : specEntities) {
        if (IntegratedStepperMotor.SPEC_NAME_FIELDBUS.equalsIgnoreCase(specEntity.getName())) {
          integratedStepperMotor.setFieldbus(specEntity.getValue());
        } else if (IntegratedStepperMotor.SPEC_NAME_OPERATING_VOLTAGE.equalsIgnoreCase(specEntity.getName())) {
          integratedStepperMotor.setOperatingVoltage(specEntity.getValue() + " " + specEntity.getUnit());
        } else if (IntegratedStepperMotor.SPEC_NAME_ENCODER_RESOLUTION.equalsIgnoreCase(specEntity.getName())) {
          integratedStepperMotor.setEncoderResolution(specEntity.getValue() + " " + specEntity.getUnit());
        }
      }
    }
    return integratedStepperMotor;
  }
}
