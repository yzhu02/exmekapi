package com.exmek.core.resource;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompositeResourceManager implements ResourceManager {

	@Autowired
	private UserResourceManager userResourceManager;
	
	@Autowired
	private ClasspathResourceManager classpathResourceManager;

	@Override
	public List<String> getMotorMechanicalImagePaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getMotorMechanicalImagePaths(model, series), 
				() -> classpathResourceManager.getMotorMechanicalImagePaths(model, series));
	}

	@Override
	public List<String> getGearboxMechanicalImagePaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getGearboxMechanicalImagePaths(model, series), 
				() -> classpathResourceManager.getGearboxMechanicalImagePaths(model, series));
	}

	@Override
	public List<String> getBrakeMechanicalImagePaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getBrakeMechanicalImagePaths(model, series), 
				() -> classpathResourceManager.getBrakeMechanicalImagePaths(model, series));
	}

	@Override
	public List<String> getMotor3DDrawingPaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getMotor3DDrawingPaths(model, series), 
				() -> classpathResourceManager.getMotor3DDrawingPaths(model, series));
	}

	@Override
	public List<String> getGearbox3DDrawingPaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getGearbox3DDrawingPaths(model, series), 
				() -> classpathResourceManager.getGearbox3DDrawingPaths(model, series));
	}

	@Override
	public List<String> getBrake3DDrawingPaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getBrake3DDrawingPaths(model, series), 
				() -> classpathResourceManager.getBrake3DDrawingPaths(model, series));
	}

	@Override
	public List<String> getMotorTechDocPaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getMotorTechDocPaths(model, series), 
				() -> classpathResourceManager.getMotorTechDocPaths(model, series));
	}

	@Override
	public List<String> getGearboxTechDocPaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getGearboxTechDocPaths(model, series),
				() -> classpathResourceManager.getGearboxTechDocPaths(model, series));
	}

	@Override
	public List<String> getBrakeTechDocPaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getBrakeTechDocPaths(model, series),
				() -> classpathResourceManager.getBrakeTechDocPaths(model, series));
	}

	@Override
	public Map<String, List<String>> getMotorAdditionalImagePaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getMotorAdditionalImagePaths(model, series),
				() -> classpathResourceManager.getMotorAdditionalImagePaths(model, series));
	}
	
	@Override
	public Map<String, List<String>> getGearboxAdditionalImagePaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getGearboxAdditionalImagePaths(model, series),
				() -> classpathResourceManager.getGearboxAdditionalImagePaths(model, series));
	}
	
	@Override
	public Map<String, List<String>> getBrakeAdditionalImagePaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getBrakeAdditionalImagePaths(model, series),
				() -> classpathResourceManager.getBrakeAdditionalImagePaths(model, series));
	}
	
	private <T> T getOneByOrder(Supplier<T> p1, Supplier<T> p2) {
		T result = p1.get();
		return result != null ? result : p2.get();
	}

	@Override
	public List<ResourceInfo> getTechDocInfos() {
		List<ResourceInfo> techDocInfos = userResourceManager.getTechDocInfos();
		Set<String> techDocNameSet = techDocInfos.stream()
				.map(ResourceInfo::getName)
				.collect(Collectors.toSet());
		List<ResourceInfo> cpTechDocInfos = classpathResourceManager.getTechDocInfos();
		if (CollectionUtils.isNotEmpty(cpTechDocInfos)) {
			cpTechDocInfos.forEach(r -> {
				if (!techDocNameSet.contains(r.getName())) {
					techDocInfos.add(r);
					techDocNameSet.add(r.getName());
				}
			});
		}
		return techDocInfos;
	}
}
