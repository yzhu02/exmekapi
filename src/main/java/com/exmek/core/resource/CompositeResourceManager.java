package com.exmek.core.resource;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompositeResourceManager implements ResourceManager {

	@Autowired
	private UserResourceManager userResourceManager;
	
	@Autowired
	private ClasspathResourceManager classpathResourceManager;

	// The ClasspathResourceManager is used as secondary resource. 
	// Currently all image, mechanical, 3d, 3d-view and tech-docs resources are moved to user-resources folder, 
	// the secondary resource is disabled by default.  
	private boolean secondaryFallbackEnabled = false;
	
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
	public List<String> getMotor3DModelPaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getMotor3DModelPaths(model, series), 
				() -> classpathResourceManager.getMotor3DModelPaths(model, series));
	}

	@Override
	public List<String> getGearbox3DModelPaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getGearbox3DModelPaths(model, series), 
				() -> classpathResourceManager.getGearbox3DModelPaths(model, series));
	}

	@Override
	public List<String> getBrake3DModelPaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getBrake3DModelPaths(model, series), 
				() -> classpathResourceManager.getBrake3DModelPaths(model, series));
	}


	@Override
	public Map<String, List<String>> getMotor3DViewPaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getMotor3DViewPaths(model, series), 
				() -> classpathResourceManager.getMotor3DViewPaths(model, series));
	}

	@Override
	public Map<String, List<String>> getGearbox3DViewPaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getGearbox3DViewPaths(model, series), 
				() -> classpathResourceManager.getGearbox3DViewPaths(model, series));
	}
	
	@Override
	public Map<String, List<String>> getBrake3DViewPaths(String model, String series) {
		return getOneByOrder(() -> userResourceManager.getBrake3DViewPaths(model, series), 
				() -> classpathResourceManager.getBrake3DViewPaths(model, series));
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

	
	private <T> T getOneByOrder(Supplier<T> primary, Supplier<T> secondary) {
		T result = primary.get();
		if (result != null) {
			return result;
		}
		return secondaryFallbackEnabled ? secondary.get() : null;
	}

//	@Override
//	public List<ResourceInfo> getTechDocInfos() {
//		List<ResourceInfo> techDocInfos = userResourceManager.getTechDocInfos();
//		Set<String> techDocNameSet = techDocInfos.stream()
//				.map(ResourceInfo::getName)
//				.collect(Collectors.toSet());
//		List<ResourceInfo> cpTechDocInfos = classpathResourceManager.getTechDocInfos();
//		if (CollectionUtils.isNotEmpty(cpTechDocInfos)) {
//			cpTechDocInfos.forEach(r -> {
//				if (!techDocNameSet.contains(r.getName())) {
//					techDocInfos.add(r);
//					techDocNameSet.add(r.getName());
//				}
//			});
//		}
//		return techDocInfos;
//	}
}
