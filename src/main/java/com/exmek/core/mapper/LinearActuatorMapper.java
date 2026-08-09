package com.exmek.core.mapper;

import com.exmek.core.commons.model.MeasuredRawValue;
import com.exmek.core.model.LinearActuator;
import com.exmek.core.persistence.entity.AbstractLinearActuatorEntity;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.entity.LightweightLinearActuatorEntity;
import com.exmek.core.persistence.entity.LinearActuatorEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class LinearActuatorMapper extends AbstractProductMapper {

	static final Set<String> EXCLUDED_FIELDS_TO_SPECS = new HashSet<>(Arrays.asList(
			AbstractProductEntity.FIELD_NAME_SERIES,
			AbstractProductEntity.FIELD_NAME_PRODUCT_SERIES,
			AbstractProductEntity.FIELD_NAME_MODEL,
			AbstractProductEntity.FIELD_NAME_NAME,
			AbstractProductEntity.FIELD_NAME_DESCRIPTION
			));
	
	private void performBasicMapping(LinearActuator model, AbstractLinearActuatorEntity entity) {
		model.setInputVoltage(toMeasuredValue(entity.getInputVoltage(), entity.getInputVoltageUnit()));
    model.setMaxLoad(toMeasuredValue(entity.getMaxLoad(), entity.getMaxLoadUnit()));
    model.setMaxStroke(toMeasuredValue(entity.getMaxStroke(), entity.getMaxStrokeUnit()));
    model.setNoloadSpeed(toMeasuredValue(entity.getNoloadSpeed(), entity.getNoloadSpeedUnit()));
    model.setNoloadCurrent(toMeasuredValue(entity.getNoloadCurrent(), entity.getNoloadCurrentUnit()));
    model.setFullLoadSpeed(toMeasuredValue(entity.getFullLoadSpeed(), entity.getFullLoadSpeedUnit()));
    model.setFullLoadCurrent(toMeasuredValue(entity.getFullLoadCurrent(), entity.getFullLoadCurrentUnit()));
    model.setInstallationDistance(toMeasuredRawValue(entity.getInstallationDistance(), entity.getInstallationDistanceUnit()));
	}

  private <V, U> MeasuredRawValue<V, U> toMeasuredRawValue(V value, U unit) {
    if (value == null) {
      return null;
    }
    return MeasuredRawValue.of(value, unit);
  }

	public LinearActuator mapLightweightLinearActuatorToModel(LightweightLinearActuatorEntity entity) {
		if (entity == null) {
			return null;
		}
    LinearActuator model = super.mapProduct(entity, LinearActuator::new);
		performBasicMapping(model, entity);
		return model;
	}

	public LinearActuator mapLinearActuatorToModel(LinearActuatorEntity entity) {
		if (entity == null) {
			return null;
		}
    LinearActuator model = super.mapProduct(entity, LinearActuator::new);
		performBasicMapping(model, entity);

    // model.setProductSeries(AbstractSeriesMapper.mapEntityToSeries(entity.getProductSeries(), ActuatorSeries::new, false));
    model.setAllSpecs(mapAllCombinedSpecs(entity, appConfigProvider.getSearchLinearActuatorMetaCriteriaFieldsDefault(), EXCLUDED_FIELDS_TO_SPECS));

    model.setMechanicalImagePaths(resourceManager.getActuatorMechanicalImagePaths(entity.getModel(), entity.getSeries()));
    model.setThreeDModelPaths(resourceManager.getActuator3DModelPaths(entity.getModel(), entity.getSeries()));
    model.setThreeDViewPaths(resourceManager.getActuator3DViewPaths(entity.getModel(), entity.getSeries()));
    model.setTechDocPaths(resourceManager.getActuatorTechDocPaths(entity.getModel(), entity.getSeries()));
    model.setAdditionalImagePaths(resourceManager.getActuatorAdditionalImagePaths(entity.getModel(), entity.getSeries()));

		return model;
	}
}
