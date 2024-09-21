package com.exmek.core.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.exmek.core.commons.model.CurveLine;
import com.exmek.core.commons.model.MeasuredValue;
import com.exmek.core.commons.model.Point;
import com.exmek.core.config.Configuration;
import com.exmek.core.config.CurveCoordinate;
import com.exmek.core.model.DCMotor;
import com.exmek.core.model.LeadDef;
import com.exmek.core.model.LinearStepperMotor;
import com.exmek.core.model.MotorPerfCurve;
import com.exmek.core.model.MotorSpec;
import com.exmek.core.model.StepperMotor;
import com.exmek.core.persistence.entity.AbstractMotorEntity;
import com.exmek.core.persistence.entity.AbstractMotorPerfMeasurementEntity;
import com.exmek.core.persistence.entity.AbstractMotorSpecEntity;
import com.exmek.core.persistence.entity.DCMotorEntity;
import com.exmek.core.persistence.entity.LeadDefEntity;
import com.exmek.core.persistence.entity.StepperMotorEntity;

import commons.utils.CommonUtils;
import commons.utils.ReflectionUtils;

@Component
public class MotorMapper extends AbstractProductMapper {

	@Autowired
	private Configuration configuration;
	
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
			motor.setPerfCurves(mapPerfMeasurementsToCurveModels(entity.getPerfMeasurements(), entity.getModel()));
		}
		
		return motor;
	}

	private List<MotorSpec> mapAllCombinedSpecsToModels(DCMotorEntity motorEntity) {
		if (motorEntity == null) {
			return null;
		}
		List<MotorSpec> models = new ArrayList<>();
		
		addProductPropertiesAsSpecs(models, motorEntity);
		
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Rated Voltage", motorEntity.getRatedVoltageUnit(), motorEntity.getRatedVoltage()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Rated Current", motorEntity.getRatedCurrentUnit(), motorEntity.getRatedCurrent()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Rated Power", motorEntity.getRatedPowerUnit(), motorEntity.getRatedPower()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Rated Torque", motorEntity.getRatedTorqueUnit(), motorEntity.getRatedTorque()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Rated Speed", motorEntity.getRatedRotatingSpeedUnit(), motorEntity.getRatedRotatingSpeed()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Rated Linear Speed", motorEntity.getRatedLinearSpeedUnit(), motorEntity.getRatedLinearSpeed()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Peak Current", motorEntity.getPeakCurrentUnit(), motorEntity.getPeakCurrent()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Peak Torque", motorEntity.getPeakTorqueUnit(), motorEntity.getPeakTorque()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Max Sorting Weight", motorEntity.getMaxSortingWeightUnit(), motorEntity.getMaxSortingWeight()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("No Load Current", motorEntity.getNoloadCurrentUnit(), motorEntity.getNoloadCurrent()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("No Load Speed", motorEntity.getNoloadRotatingSpeedUnit(), motorEntity.getNoloadRotatingSpeed()));

		addAllSpecsIfNameNotExist(models, mapSpecsToModels(motorEntity.getSpecs()));
		
		return models;
	}
	
	private void addProductPropertiesAsSpecs(List<MotorSpec> models, AbstractMotorEntity motorEntity) {
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Motor Length", motorEntity.getLengthUnit(), motorEntity.getLength()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Motor Weight", motorEntity.getWeightUnit(), motorEntity.getWeight()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Frame Size", motorEntity.getFrameSizeUnit(), motorEntity.getFrameSize(),
				Optional.ofNullable(motorEntity.getFrameSizeType()).map(f -> f.getSymbol()).orElse(null)));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("NEMA", null, motorEntity.getNemaSize()));

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

	private <E extends AbstractMotorPerfMeasurementEntity> List<MotorPerfCurve> mapPerfMeasurementsToCurveModels(Set<E> entities, String model) {
		if (entities == null) {
			return null;
		}
		List<MotorPerfCurve> models = new ArrayList<>();
		for (E entity : entities) {
			MotorPerfCurve perfCurve = mapPerfMeasurementToCurveModel(entity, model);
			if (perfCurve != null) {
				models.add(perfCurve);
			}
		}
		return models;
	}

	private MotorPerfCurve mapPerfMeasurementToCurveModel(AbstractMotorPerfMeasurementEntity entity, String model) {
		if (entity == null) {
			return null;
		}
		MotorPerfCurve perfCurve = new MotorPerfCurve();
		perfCurve.setTitle(entity.getTitle());
		List<CurveCoordinate> dcMotorCurveCoordinates = configuration.getMotorCurveCoordinates(model);
		if (!CollectionUtils.isEmpty(dcMotorCurveCoordinates)) {
			String[] columnNames = CommonUtils.split(entity.getVariables(), ",");
			String[] mConditions = CommonUtils.split(entity.getConditions(), ",");
			BigDecimal[][] mValues = CommonUtils.parseCSVLikeValues(entity.getValues(),
					rows -> new BigDecimal[rows][], cells -> new BigDecimal[cells], s -> new BigDecimal(s));
			List<CurveLine> curveLines = new ArrayList<>();
			for (int i = 0; i < dcMotorCurveCoordinates.size(); i++) {
				CurveCoordinate cc = dcMotorCurveCoordinates.get(i);
				CurveLine cLine = new CurveLine();
				if (mConditions != null && mConditions.length >= i) {
					cLine.setName(mConditions[i]);
				} else {
					cLine.setName(cc.getName());
				}
				int xColInx = CommonUtils.findIndex(columnNames, cc.getX());
				int yColInx = CommonUtils.findIndex(columnNames, cc.getY());
				if (xColInx >= 0 && yColInx >= 0) {
					for (int r = 0; r < mValues.length; r++) {
						cLine.addPoint(Point.of(mValues[r][xColInx], mValues[r][yColInx]));
					}
				}
				curveLines.add(cLine);
			}
			perfCurve.setCurveLines(curveLines);
		}
		return perfCurve;
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
		motor.setSeries(entity.getSeries());
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
			motor.setPerfCurves(mapPerfMeasurementsToCurveModels(entity.getPerfMeasurements(), entity.getModel()));
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
		
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Rated Voltage", motorEntity.getRatedVoltageUnit(), motorEntity.getRatedVoltage()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Phase Current", motorEntity.getPhaseCurrentUnit(), motorEntity.getPhaseCurrent()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Phase Resistance", motorEntity.getPhaseResistanceUnit(), motorEntity.getPhaseResistance()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Phase Inductance", motorEntity.getPhaseInductanceUnit(), motorEntity.getPhaseInductance()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Holding Torque", motorEntity.getHoldingTorqueUnit(), motorEntity.getHoldingTorque()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Detent Torque", motorEntity.getDetentTorqueUnit(), motorEntity.getDetentTorque()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Step Angle", motorEntity.getStepAngleUnit(), motorEntity.getStepAngle()));
		CommonUtils.addNonNullToList(models, () -> createMotorSpec("Max Thrust", motorEntity.getMaxThrustUnit(), motorEntity.getMaxThrust()));

		addAllSpecsIfNameNotExist(models, mapSpecsToModels(motorEntity.getSpecs()));
		
		return models;
	}
}
