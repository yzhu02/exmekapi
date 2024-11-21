package com.exmek.core.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exmek.commons.utils.JsonMapperUtils;
import com.exmek.core.persistence.repository.MotorConfigRepository;
import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.annotation.PostConstruct;

@Component
public class MotorConfig {

	private static final Logger logger = LoggerFactory.getLogger(MotorConfig.class);

	public static final String CONFIG_NAME_MOTOR_CURVE_COORDINATES	= "curve.coordinates";

	@Autowired
	private MotorConfigRepository motorConfigRepository;
	
	private Map<String, Map<String, String>> perMotorConfigMap = new HashMap<>();

	@PostConstruct
	protected void init() {
		this.perMotorConfigMap = new HashMap<>();
		this.motorConfigRepository.findAll().stream().forEach(motorConfigEntity -> {
			String modelRef = motorConfigEntity.getModelRef();
			Map<String, String> mConfigMap = this.perMotorConfigMap.get(modelRef);
			if (mConfigMap == null) {
				mConfigMap = new HashMap<>();
				this.perMotorConfigMap.put(modelRef, mConfigMap);
			}
			mConfigMap.put(motorConfigEntity.getConfigName(), motorConfigEntity.getConfigValue());
		});
	}
		
	public List<CurveCoordinate> getMotorCurveCoordinates(String model) {
		if (model == null) {
			return null;
		}
		if (perMotorConfigMap == null) {
			logger.error("Unable to getMotorCurveCoordinates for model '{}' as the 'perMotorConfigMap' is null. ", model);
			return null;
		}
		
		//trying to exact match the model first
		Map<String, String> mConfigMap = perMotorConfigMap.get(model);

		//if not found by exact match, then try to match by stripping tail digits
		//for example, MB057GA100 will match with MB057GA*
		if (mConfigMap == null) {
			int i = -1;
			for (i = model.length() - 1; i >= 0; i--) {
				char c = model.charAt(i);
				if (!Character.isDigit(c) && c != '-' && c != '_') {
					break;
				}
			}
			if (i >= 0) {
				mConfigMap = perMotorConfigMap.get(model.substring(0, i + 1) + "*");
			}
		}
		
		//if not found by exact match and stripping tail digits match, then try to match by looking up with prefix or suffix
		//for example, MB057GA100 will match with MB057GA*
//		if (mConfigMap == null) {
//			Set<String> modelRefs = perMotorConfigMap.keySet();
//			for (String modelRef : modelRefs) {
//				if (modelRef.endsWith("*")) {
//					if (model.startsWith(modelRef.substring(0, modelRef.length() - 1))) {
//						mConfigMap = perMotorConfigMap.get(modelRef);
//						break;
//					}
//				}
//			}
//		}
		
		if (mConfigMap == null) {
			logger.error("Unable to getMotorCurveCoordinates for model '{}' as the submap from 'perMotorConfigMap[{}]' is null. ", model, model);
			return null;
		}
		String configValue = mConfigMap.get(CONFIG_NAME_MOTOR_CURVE_COORDINATES);
		if (configValue == null) {
			logger.error("Unable to getMotorCurveCoordinates as the config value is null. ");
			return null;
		}
		return JsonMapperUtils.readValue(configValue, new TypeReference<List<CurveCoordinate>>() {});
	}
}
