package com.exmek.core.mapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.exmek.core.model.Brake;
import com.exmek.core.model.BrakeSeries;
import com.exmek.core.persistence.entity.AbstractBrakeEntity;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.entity.BrakeEntity;
import com.exmek.core.persistence.entity.LightweightBrakeEntity;

@Component
public class BrakeMapper extends AbstractProductMapper {

	static final Set<String> EXCLUDED_FIELDS_TO_SPECS = new HashSet<>(Arrays.asList(
			AbstractProductEntity.FIELD_NAME_SERIES,
			AbstractProductEntity.FIELD_NAME_PRODUCT_SERIES,
			AbstractProductEntity.FIELD_NAME_MODEL,
			AbstractProductEntity.FIELD_NAME_NAME,
			AbstractProductEntity.FIELD_NAME_DESCRIPTION
			));
	
	private void performBasicMapping(Brake model, AbstractBrakeEntity entity) {
		model.setRatedVoltage(toMeasuredValue(entity.getRatedVoltage(), entity.getRatedVoltageUnit()));
		model.setResistance(toMeasuredValue(entity.getResistance(), entity.getResistanceUnit()));
		model.setCurrent(toMeasuredValue(entity.getCurrent(), entity.getCurrentUnit()));
		model.setStaticTorque(toMeasuredValue(entity.getStaticTorque(), entity.getStaticTorqueUnit()));
		model.setRatedPower(toMeasuredValue(entity.getRatedPower(), entity.getRatedPowerUnit()));
		model.setStartVoltage(toMeasuredValue(entity.getStartVoltage(), entity.getStartVoltageUnit()));
	}

	public Brake mapLightweightBrakeToModel(LightweightBrakeEntity entity) {
		if (entity == null) {
			return null;
		}
		Brake model = super.mapProduct(entity, Brake::new);
		performBasicMapping(model, entity);
		return model;
	}

	public Brake mapBrakeToModel(BrakeEntity entity) {
		if (entity == null) {
			return null;
		}
		Brake model = super.mapProduct(entity, Brake::new);
		performBasicMapping(model, entity);

		model.setProductSeries(AbstractSeriesMapper.mapEntityToSeries(entity.getProductSeries(), BrakeSeries::new, false));
		model.setAllSpecs(mapAllCombinedSpecs(entity, appConfigProvider.getSearchPlanetaryGearboxMetaCriteriaFields(), EXCLUDED_FIELDS_TO_SPECS));

		model.setMechanicalImagePaths(resourceManager.getBrakeMechanicalImagePaths(entity.getModel(), entity.getSeries()));
		model.setThreeDModelPaths(resourceManager.getBrake3DModelPaths(entity.getModel(), entity.getSeries()));
		model.setThreeDViewPaths(resourceManager.getBrake3DViewPaths(entity.getModel(), entity.getSeries()));
		model.setTechDocPaths(resourceManager.getBrakeTechDocPaths(entity.getModel(), entity.getSeries()));
		model.setAdditionalImagePaths(resourceManager.getBrakeAdditionalImagePaths(entity.getModel(), entity.getSeries()));
		
		return model;
	}
}
