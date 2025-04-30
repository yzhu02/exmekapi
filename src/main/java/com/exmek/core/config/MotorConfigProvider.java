package com.exmek.core.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exmek.commons.utils.JsonMapperUtils;
import com.exmek.commons.utils.MiscUtils;
import com.exmek.core.persistence.repository.MotorConfigRepository;
import com.exmek.core.scheduler.Scheduleable;
import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MotorConfigProvider implements Scheduleable {

	public static final String CONFIG_NAME_MOTOR_CURVE_COORDINATES	= "curve.coordinates";

	@Autowired
	private MotorConfigRepository motorConfigRepository;
	
	private Map<String, Map<String, String>> perMotorConfigMap = new HashMap<>();

	@PostConstruct
	protected void initialize() {
		this.perMotorConfigMap = new HashMap<>();
		this.motorConfigRepository.findAll().stream().forEach(motorConfigEntity -> {
			String modelRefsStr = motorConfigEntity.getModelRefs();
			String[] modelRefs = MiscUtils.split(modelRefsStr, ",");
			for (String modelRef : modelRefs) {
				Map<String, String> mConfigMap = this.perMotorConfigMap.get(modelRef);
				if (mConfigMap == null) {
					mConfigMap = new HashMap<>();
					this.perMotorConfigMap.put(modelRef, mConfigMap);
				}
				mConfigMap.put(motorConfigEntity.getConfigName(), motorConfigEntity.getConfigValue());
			}
		});
	}

	@Override
	public void onSchedule() {
		initialize();
	}

	public List<CurveCoordinate> getMotorCurveCoordinates(String model) {
		if (model == null) {
			return null;
		}
		if (perMotorConfigMap == null) {
			log.error("Unable to getMotorCurveCoordinates for model '{}' as the 'perMotorConfigMap' is null. ", model);
			return null;
		}
		
		//trying to exact match the model first
		Map<String, String> mConfigMap = perMotorConfigMap.get(model);

		//if not found by exact match, then try to match by stripping tail digits
		//for example, MB057GA100 will match with MB057GA*
		if (mConfigMap == null) {
//			int i = -1;
//			for (i = model.length() - 1; i >= 0; i--) {
//				char c = model.charAt(i);
//				if (!Character.isDigit(c) && c != '-' && c != '_') {
//					break;
//				}
//			}
//			if (i >= 0) {
//				mConfigMap = perMotorConfigMap.get(model.substring(0, i + 1) + "*");
//			}
			
			Optional<String> opFoundKey = perMotorConfigMap.keySet().stream()
					.filter(k -> matchKey(k, model))
					.findFirst();
			if (opFoundKey.isPresent()) {
				mConfigMap = perMotorConfigMap.get(opFoundKey.get());
			}
		}
		
		if (mConfigMap == null) {
			log.error("Unable to getMotorCurveCoordinates for model '{}' as the submap from 'perMotorConfigMap[{}]' is null. ", model, model);
			return null;
		}
		String configValue = mConfigMap.get(CONFIG_NAME_MOTOR_CURVE_COORDINATES);
		if (configValue == null) {
			log.error("Unable to getMotorCurveCoordinates as the config value is null. ");
			return null;
		}
		return JsonMapperUtils.readValue(configValue, new TypeReference<List<CurveCoordinate>>() {});
	}
	
	private boolean matchKey(String keyPattern, String finding) {
		if (keyPattern != null && keyPattern.contains("*")) {
			keyPattern = keyPattern.replace("*", ".*")
					.replace("(", "\\(")
					.replace(")", "\\)");
			
			return finding.matches(keyPattern);
		}
		return Objects.equals(keyPattern, finding);
	}
	
	public int getMotorCurveCoordinateCount() {
		return perMotorConfigMap.size();
	}
}
