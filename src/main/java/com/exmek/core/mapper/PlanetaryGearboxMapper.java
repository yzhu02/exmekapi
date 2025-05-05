package com.exmek.core.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.exmek.core.model.GearboxSeries;
import com.exmek.core.model.PlanetaryGearbox;
import com.exmek.core.persistence.entity.AbstractPlanetaryGearboxEntity;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.entity.PlanetaryGearboxEntity;

@Component
public class PlanetaryGearboxMapper extends AbstractProductMapper {

	static final Set<String> EXCLUDED_FIELDS_TO_SPECS = new HashSet<>(Arrays.asList(
			AbstractProductEntity.FIELD_NAME_SERIES,
			AbstractProductEntity.FIELD_NAME_PRODUCT_SERIES,
			AbstractProductEntity.FIELD_NAME_MODEL,
			AbstractProductEntity.FIELD_NAME_NAME,
			AbstractProductEntity.FIELD_NAME_DESCRIPTION
			));
	
	public PlanetaryGearbox mapPlanetaryGearboxToModel(AbstractPlanetaryGearboxEntity entity) {
		if (entity == null) {
			return null;
		}
		PlanetaryGearbox model = super.mapProduct(entity, PlanetaryGearbox::new);
		model.setNumOfStages(entity.getNumOfStages());
		model.setReductionRatios(parseReductionRatios(entity.getReductionRatios()));
		model.setEfficiency(toMeasuredValue(entity.getEfficiency(), entity.getEfficiencyUnit()));
		model.setRatedContinuousTorque(toMeasuredValue(entity.getRatedContinuousTorque(), entity.getRatedContinuousTorqueUnit()));
		model.setMaxMomentaryTorque(toMeasuredValue(entity.getMaxMomentaryTorque(), entity.getMaxMomentaryTorqueUnit()));
		model.setMaxRadialLoad(toMeasuredValue(entity.getMaxRadialLoad(), entity.getMaxRadialLoadUnit()));
		model.setMaxAxialLoad(toMeasuredValue(entity.getMaxAxialLoad(), entity.getMaxAxialLoadUnit()));
		model.setMaxShaftPress(toMeasuredValue(entity.getMaxShaftPress(), entity.getMaxShaftPressUnit()));
		model.setOperatingTemperature(entity.getOperatingTemperature());
		model.setRecommendInputSpeed(entity.getRecommendInputSpeed());
		
		if (entity instanceof PlanetaryGearboxEntity) {
			PlanetaryGearboxEntity fullEntity = (PlanetaryGearboxEntity) entity;
			model.setProductSeries(AbstractSeriesMapper.mapEntityToSeries(fullEntity.getProductSeries(), GearboxSeries::new, false));
			model.setAllSpecs(mapAllCombinedSpecs(entity, appConfigProvider.getSearchPlanetaryGearboxMetaCriteriaFields(), EXCLUDED_FIELDS_TO_SPECS));
			
			model.setMechanicalImagePaths(resourceManager.getGearboxMechanicalImagePaths(entity.getModel()));
			model.setThreeDDrawingPaths(resourceManager.getGearbox3DDrawingPaths(entity.getModel()));
			model.setTechDocPaths(resourceManager.getGearboxTechDocPaths(entity.getModel(), entity.getSeries()));
		}
		
		return model;
	}

	private List<String> parseReductionRatios(String strReductionRatios) {
		if (strReductionRatios == null || strReductionRatios.isBlank()) {
			return null;
		}
		List<String> ratios = new ArrayList<>();
		String[] ratioStrs = strReductionRatios.split(",");
		for (int i=0; i<ratioStrs.length; i++) {
			ratios.add(ratioStrs[i].trim());
		}
		return ratios;
	}

}
