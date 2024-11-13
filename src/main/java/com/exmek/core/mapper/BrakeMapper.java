package com.exmek.core.mapper;

import org.springframework.stereotype.Component;

import com.exmek.core.commons.model.MeasuredValue;
import com.exmek.core.model.Brake;
import com.exmek.core.persistence.entity.BrakeEntity;

@Component
public class BrakeMapper extends AbstractProductMapper {

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
			model.setMechanicalImagePaths(resourceContext.getBrakeMechanicalImagePaths(entity.getModel()));
			model.setThreeDDrawingPaths(resourceContext.getBrake3DDrawingPaths(entity.getModel()));
			model.setTechDocPaths(resourceContext.getBrakeTechDocPaths(entity.getModel()));
		}
		
		return model;
	}

}
