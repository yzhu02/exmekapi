package com.exmek.core.mapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.exmek.core.commons.model.MeasuredValue;
import com.exmek.core.model.Brake;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.entity.BrakeEntity;

@Component
public class BrakeMapper extends AbstractProductMapper {

	static final Set<String> EXCLUDED_FIELDS_TO_SPECS = new HashSet<>(Arrays.asList(
			AbstractProductEntity.FIELD_NAME_SERIES,
			AbstractProductEntity.FIELD_NAME_MODEL,
			AbstractProductEntity.FIELD_NAME_NAME,
			AbstractProductEntity.FIELD_NAME_DESCRIPTION
			));
	
	public Brake mapBrakeToModel(BrakeEntity entity, boolean comprehensiveMapping) {
		if (entity == null) {
			return null;
		}
		Brake model = super.mapProduct(entity, Brake::new);
		model.setRatedVoltage(MeasuredValue.of(entity.getRatedVoltage(), entity.getRatedVoltageUnit()));
		model.setResistance(MeasuredValue.of(entity.getResistance(), entity.getResistanceUnit()));
		model.setCurrent(MeasuredValue.of(entity.getCurrent(), entity.getCurrentUnit()));
		model.setStaticTorque(MeasuredValue.of(entity.getStaticTorque(), entity.getStaticTorqueUnit()));
		model.setRatedPower(MeasuredValue.of(entity.getRatedPower(), entity.getRatedPowerUnit()));
		model.setStartVoltage(MeasuredValue.of(entity.getStartVoltage(), entity.getStartVoltageUnit()));
		
		if (comprehensiveMapping) {
			model.setAllSpecs(mapAllCombinedSpecs(entity, appConfigProvider.getSearchPlanetaryGearboxMetaCriteriaFields(), EXCLUDED_FIELDS_TO_SPECS));
		}
		
		if (comprehensiveMapping) {
			model.setMechanicalImagePaths(resourceContext.getBrakeMechanicalImagePaths(entity.getModel()));
			model.setThreeDDrawingPaths(resourceContext.getBrake3DDrawingPaths(entity.getModel()));
			model.setTechDocPaths(resourceContext.getBrakeTechDocPaths(entity.getModel(), entity.getSeries()));
		}
		
		return model;
	}

}
