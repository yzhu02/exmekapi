package com.exmek.core.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.exmek.core.commons.model.MeasuredValue;
import com.exmek.core.model.PlanetaryGearbox;
import com.exmek.core.persistence.entity.PlanetaryGearboxEntity;

@Component
public class PlanetaryGearboxMapper extends AbstractProductMapper {

	public PlanetaryGearbox mapPlanetaryGearboxToModel(PlanetaryGearboxEntity entity) {
		if (entity == null) {
			return null;
		}
		PlanetaryGearbox model = super.mapProduct(entity, PlanetaryGearbox::new);
		model.setNumOfStages(entity.getNumOfStages());
		model.setReductionRatios(parseReductionRatios(entity.getReductionRatios()));
		model.setEfficiency(MeasuredValue.of(entity.getEfficiency(), entity.getEfficiencyUnit()));
		model.setRatedContinuousTorque(MeasuredValue.of(entity.getRatedContinuousTorque(), entity.getRatedContinuousTorqueUnit()));
		model.setMaxMomentaryTorque(MeasuredValue.of(entity.getMaxMomentaryTorque(), entity.getMaxMomentaryTorqueUnit()));
		model.setMaxRadialLoad(MeasuredValue.of(entity.getMaxRadialLoad(), entity.getMaxRadialLoadUnit()));
		model.setMaxAxialLoad(MeasuredValue.of(entity.getMaxAxialLoad(), entity.getMaxAxialLoadUnit()));
		model.setMaxShaftPress(MeasuredValue.of(entity.getMaxShaftPress(), entity.getMaxShaftPressUnit()));
		model.setOperatingTemperature(entity.getOperatingTemperature());
		model.setRecommendInputSpeed(entity.getRecommendInputSpeed());
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
