package com.exmek.core.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exmek.commons.utils.MiscUtils;
import com.exmek.commons.utils.ReflectionUtils;
import com.exmek.core.commons.model.MeasuredValue;
import com.exmek.core.model.DCMotor;
import com.exmek.core.model.LeadDef;
import com.exmek.core.model.LinearStepperMotor;
import com.exmek.core.model.MotorSpec;
import com.exmek.core.model.StepperMotor;
import com.exmek.core.persistence.entity.AbstractMotorEntity;
import com.exmek.core.persistence.entity.AbstractMotorSpecEntity;
import com.exmek.core.persistence.entity.DCMotorEntity;
import com.exmek.core.persistence.entity.LeadDefEntity;
import com.exmek.core.persistence.entity.StepperMotorEntity;

@Component
public class MotorMapper extends AbstractProductMapper {
	
	@Autowired
	private MotorPerfCurveMapper motorPerfCurveMapper;
	
	public DCMotor mapDCMotorToModel(DCMotorEntity entity) {
		return mapDCMotorToModel(entity, true);
	}

	public DCMotor mapDCMotorToModel(DCMotorEntity entity, boolean comprehensiveMapping) {
		if (entity == null) {
			return null;
		}
		DCMotor motor = super.mapProduct(entity, DCMotor::new);
		motor.setCategory(entity.getCategory());
		motor.setRatedVoltage(MeasuredValue.of(entity.getRatedVoltage(), entity.getRatedVoltageUnit()));
		motor.setRatedCurrent(MeasuredValue.of(entity.getRatedCurrent(), entity.getRatedCurrentUnit()));
		motor.setRatedPower(MeasuredValue.of(entity.getRatedPower(), entity.getRatedPowerUnit()));
		motor.setRatedTorque(MeasuredValue.of(entity.getRatedTorque(), entity.getRatedTorqueUnit()));
		motor.setRatedRotatingSpeed(MeasuredValue.of(entity.getRatedRotatingSpeed(), entity.getRatedRotatingSpeedUnit()));
		motor.setRatedLinearSpeed(MeasuredValue.of(entity.getRatedLinearSpeed(), entity.getRatedLinearSpeedUnit()));
		motor.setPeakCurrent(MeasuredValue.of(entity.getPeakCurrent(), entity.getPeakCurrentUnit()));
		motor.setPeakTorque(MeasuredValue.of(entity.getPeakTorque(), entity.getPeakTorqueUnit()));
		motor.setMaxSortingWeight(MeasuredValue.of(entity.getMaxSortingWeight(), entity.getMaxSortingWeightUnit()));
		motor.setNoloadCurrent(MeasuredValue.of(entity.getNoloadCurrent(), entity.getNoloadCurrentUnit()));
		motor.setNoloadRotatingSpeed(MeasuredValue.of(entity.getNoloadRotatingSpeed(), entity.getNoloadRotatingSpeedUnit()));
		
		if (comprehensiveMapping) {
			motor.setAllSpecs(mapAllCombinedSpecsToModels(entity));
			motor.setPerfCurves(motorPerfCurveMapper.mapToPerfCurveModels(entity.getPerfMeasurements(), entity.getModel()));
		}
		
		if (comprehensiveMapping) {
			motor.setMechanicalImagePaths(resourceContext.getMotorMechanicalImagePaths(entity.getModel()));
			motor.setThreeDDrawingPaths(resourceContext.getMotor3DDrawingPaths(entity.getModel()));
			motor.setTechDocPaths(resourceContext.getMotorTechDocPaths(entity.getModel()));
		}
		
		return motor;
	}

	private List<MotorSpec> mapAllCombinedSpecsToModels(DCMotorEntity motorEntity) {
		if (motorEntity == null) {
			return null;
		}
		List<MotorSpec> models = new ArrayList<>();
		
		addProductPropertiesAsSpecs(models, motorEntity);
		
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Rated Voltage", motorEntity.getRatedVoltageUnit(), motorEntity.getRatedVoltage()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Rated Current", motorEntity.getRatedCurrentUnit(), motorEntity.getRatedCurrent()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Rated Power", motorEntity.getRatedPowerUnit(), motorEntity.getRatedPower()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Rated Torque", motorEntity.getRatedTorqueUnit(), motorEntity.getRatedTorque()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Rated Speed", motorEntity.getRatedRotatingSpeedUnit(), motorEntity.getRatedRotatingSpeed()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Rated Linear Speed", motorEntity.getRatedLinearSpeedUnit(), motorEntity.getRatedLinearSpeed()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Peak Current", motorEntity.getPeakCurrentUnit(), motorEntity.getPeakCurrent()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Peak Torque", motorEntity.getPeakTorqueUnit(), motorEntity.getPeakTorque()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Max Sorting Weight", motorEntity.getMaxSortingWeightUnit(), motorEntity.getMaxSortingWeight()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("No Load Current", motorEntity.getNoloadCurrentUnit(), motorEntity.getNoloadCurrent()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("No Load Speed", motorEntity.getNoloadRotatingSpeedUnit(), motorEntity.getNoloadRotatingSpeed()));

		addAllSpecsIfNameNotExist(models, mapSpecsToModels(motorEntity.getSpecs()));
		
		return models;
	}
	
	private void addProductPropertiesAsSpecs(List<MotorSpec> models, AbstractMotorEntity motorEntity) {
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Motor Length", motorEntity.getLengthUnit(), motorEntity.getLength()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Motor Weight", motorEntity.getWeightUnit(), motorEntity.getWeight()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Frame Size", motorEntity.getFrameSizeUnit(), motorEntity.getFrameSize(),
				Optional.ofNullable(motorEntity.getFrameSizeType()).map(f -> f.getSymbol()).orElse(null)));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("NEMA", null, motorEntity.getNemaSize()));

	}

	private <E extends AbstractMotorSpecEntity> List<MotorSpec> mapSpecsToModels(Set<E> entities) {
		if (entities == null) {
			return null;
		}
		List<MotorSpec> models = new ArrayList<>();
		if (entities != null) {
			for (E entity : entities) {
				MotorSpec spec = mapSpecToModel(entity);
				if (spec != null) {
					models.add(spec);
				}
			}
		}
		return models;
	}

	private MotorSpec mapSpecToModel(AbstractMotorSpecEntity entity) {
		if (entity == null) {
			return null;
		}
		MotorSpec spec = new MotorSpec();
		spec.setName(entity.getName());
		spec.setUnit(entity.getUnit());
		spec.setValue(entity.getValue());
		return spec;
	}
	
	private MotorSpec createMotorSpec(String name, Enum<?> unit, Number value) {
		return createMotorSpec(name, unit, value, null);
	}

	private MotorSpec createMotorSpec(String name, Enum<?> unit, Number value, String symbol) {
		if (value == null) {
			return null;
		}
		MotorSpec spec = new MotorSpec();
		spec.setName(name);
		if (unit != null) {
			spec.setUnit(ReflectionUtils.getEnumJsonValue(unit));
		}
		spec.setValue(String.valueOf(value));
		spec.setSymbol(symbol);
		return spec;
	}
	
	private void addAllSpecsIfNameNotExist(List<MotorSpec> resultSpecs, Collection<MotorSpec> addingSpecs) {
		if (resultSpecs == null) {
			return;
		}
		if (addingSpecs == null || addingSpecs.isEmpty()) {
			return;
		}
		Set<String> uniqueSpecNames = resultSpecs.stream().map(MotorSpec::getName).collect(Collectors.toSet());
		for (MotorSpec spec : addingSpecs) {
			if (!uniqueSpecNames.contains(spec.getName())) {
				resultSpecs.add(spec);
			}
		}
	}


	public StepperMotor mapStepperMotorToModel(StepperMotorEntity entity) {
		return mapStepperMotorToModel(entity, true);
	}

	public StepperMotor mapStepperMotorToModel(StepperMotorEntity entity, boolean comprehensiveMapping) {
		if (entity == null) {
			return null;
		}
		Set<LeadDefEntity> leadDefEntities = entity.getLinearStepperMotorLeads();
		StepperMotor motor = super.mapProduct(entity, () -> {
			if (leadDefEntities != null && !leadDefEntities.isEmpty()) {
				LinearStepperMotor linearStepperMotor = new LinearStepperMotor();
				if (comprehensiveMapping) {
					linearStepperMotor.setLeads(mapLeadsToModels(leadDefEntities));
				}
				return linearStepperMotor;
			} else {
				return new StepperMotor();
			}
		});
		motor.setCategory(entity.getCategory());
		motor.setRatedVoltage(MeasuredValue.of(entity.getRatedVoltage(), entity.getRatedVoltageUnit()));
		motor.setPhaseCurrent(MeasuredValue.of(entity.getPhaseCurrent(), entity.getPhaseCurrentUnit()));
		motor.setPhaseResistance(MeasuredValue.of(entity.getPhaseResistance(), entity.getPhaseResistanceUnit()));
		motor.setPhaseInductance(MeasuredValue.of(entity.getPhaseInductance(), entity.getPhaseInductanceUnit()));
		motor.setHoldingTorque(MeasuredValue.of(entity.getHoldingTorque(), entity.getHoldingTorqueUnit()));
		motor.setDetentTorque(MeasuredValue.of(entity.getDetentTorque(), entity.getDetentTorqueUnit()));
		motor.setStepAngle(MeasuredValue.of(entity.getStepAngle(), entity.getStepAngleUnit()));
		motor.setMaxThrust(MeasuredValue.of(entity.getMaxThrust(), entity.getMaxThrustUnit()));
		
		if (comprehensiveMapping) {
			motor.setAllSpecs(mapAllCombinedSpecsToModels(entity));
			if (motor instanceof LinearStepperMotor) {
				motor.setPerfCurves(motorPerfCurveMapper.mapToLinearStepperMotorPerfCurveModels(entity.getPerfMeasurements(), entity.getModel()));
			} else {
				motor.setPerfCurves(motorPerfCurveMapper.mapToPerfCurveModels(entity.getPerfMeasurements(), entity.getModel()));
			}
		}
		
		if (comprehensiveMapping) {
			motor.setMechanicalImagePaths(resourceContext.getMotorMechanicalImagePaths(entity.getModel()));
			motor.setThreeDDrawingPaths(resourceContext.getMotor3DDrawingPaths(entity.getModel()));
			motor.setTechDocPaths(resourceContext.getMotorTechDocPaths(entity.getModel()));
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

	private List<MotorSpec> mapAllCombinedSpecsToModels(StepperMotorEntity motorEntity) {
		if (motorEntity == null) {
			return null;
		}
		List<MotorSpec> models = new ArrayList<>();
		
		addProductPropertiesAsSpecs(models, motorEntity);
		
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Rated Voltage", motorEntity.getRatedVoltageUnit(), motorEntity.getRatedVoltage()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Phase Current", motorEntity.getPhaseCurrentUnit(), motorEntity.getPhaseCurrent()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Phase Resistance", motorEntity.getPhaseResistanceUnit(), motorEntity.getPhaseResistance()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Phase Inductance", motorEntity.getPhaseInductanceUnit(), motorEntity.getPhaseInductance()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Holding Torque", motorEntity.getHoldingTorqueUnit(), motorEntity.getHoldingTorque()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Detent Torque", motorEntity.getDetentTorqueUnit(), motorEntity.getDetentTorque()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Step Angle", motorEntity.getStepAngleUnit(), motorEntity.getStepAngle()));
		MiscUtils.addNonNullToList(models, () -> createMotorSpec("Max Thrust", motorEntity.getMaxThrustUnit(), motorEntity.getMaxThrust()));

		addAllSpecsIfNameNotExist(models, mapSpecsToModels(motorEntity.getSpecs()));
		
		return models;
	}
}
