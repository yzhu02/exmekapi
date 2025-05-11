package com.exmek.core.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.exmek.commons.utils.UrlUtils;
import com.exmek.core.config.AppConfigProvider;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClasspathResourceManager implements ResourceManager {
	
	////Directory names BEGIN
	public static final String DIR_NAME_STATIC			= "static";
	////Directory names END
	
	////Exposed relative paths BEGIN
	public static final String MATERIALS_MOTOR_MECHANICAL_REL_PATH	= UrlUtils.concatURL("/", DIR_NAME_MATERIALS, DIR_NAME_MOTOR, DIR_NAME_MICHANICAL);
	public static final String MATERIALS_GEARBOX_MECHANICAL_REL_PATH= UrlUtils.concatURL("/", DIR_NAME_MATERIALS, DIR_NAME_GEARBOX, DIR_NAME_MICHANICAL);
	public static final String MATERIALS_BRAKE_MECHANICAL_REL_PATH	= UrlUtils.concatURL("/", DIR_NAME_MATERIALS, DIR_NAME_BRAKE, DIR_NAME_MICHANICAL);

	public static final String MATERIALS_MOTOR_3D_REL_PATH			= UrlUtils.concatURL("/", DIR_NAME_MATERIALS, DIR_NAME_MOTOR, DIR_NAME_3D);
	public static final String MATERIALS_GEARBOX_3D_REL_PATH		= UrlUtils.concatURL("/", DIR_NAME_MATERIALS, DIR_NAME_GEARBOX, DIR_NAME_3D);
	public static final String MATERIALS_BRAKE_3D_REL_PATH			= UrlUtils.concatURL("/", DIR_NAME_MATERIALS, DIR_NAME_BRAKE, DIR_NAME_3D);
	
	public static final String MATERIALS_MOTOR_TECHDOC_REL_PATH		= UrlUtils.concatURL("/", DIR_NAME_MATERIALS, DIR_NAME_MOTOR, DIR_NAME_TECHDOC);
	public static final String MATERIALS_GEARBOX_TECHDOC_REL_PATH	= UrlUtils.concatURL("/", DIR_NAME_MATERIALS, DIR_NAME_GEARBOX, DIR_NAME_TECHDOC);
	public static final String MATERIALS_BRAKE_TECHDOC_REL_PATH		= UrlUtils.concatURL("/", DIR_NAME_MATERIALS, DIR_NAME_BRAKE, DIR_NAME_TECHDOC);
	////Exposed relative paths END
	
	////Internal directory locations BEGIN
	private static final String MATERIALS_MOTOR_MECHANICAL_FULL_LOCATION = UrlUtils.concatURL(
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, DIR_NAME_STATIC, MATERIALS_MOTOR_MECHANICAL_REL_PATH);

	private static final String MATERIALS_GEARBOX_MECHANICAL_FULL_LOCATION = UrlUtils.concatURL(
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, DIR_NAME_STATIC, MATERIALS_GEARBOX_MECHANICAL_REL_PATH);
	
	private static final String MATERIALS_BRAKE_MECHANICAL_FULL_LOCATION = UrlUtils.concatURL(
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, DIR_NAME_STATIC, MATERIALS_BRAKE_MECHANICAL_REL_PATH);
	

	private static final String MATERIALS_MOTOR_3D_FULL_LOCATION = UrlUtils.concatURL(
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, DIR_NAME_STATIC, MATERIALS_MOTOR_3D_REL_PATH);
	
	private static final String MATERIALS_GEARBOX_3D_FULL_LOCATION = UrlUtils.concatURL(
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, DIR_NAME_STATIC, MATERIALS_GEARBOX_3D_REL_PATH);
	
	private static final String MATERIALS_BRAKE_3D_FULL_LOCATION = UrlUtils.concatURL(
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, DIR_NAME_STATIC, MATERIALS_BRAKE_3D_REL_PATH);
	
	
	private static final String MATERIALS_MOTOR_TECHDOC_FULL_LOCATION = UrlUtils.concatURL(
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, DIR_NAME_STATIC, MATERIALS_MOTOR_TECHDOC_REL_PATH);
	
	private static final String MATERIALS_GEARBOX_TECHDOC_FULL_LOCATION = UrlUtils.concatURL(
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, DIR_NAME_STATIC, MATERIALS_GEARBOX_TECHDOC_REL_PATH);
	
	private static final String MATERIALS_BRAKE_TECHDOC_FULL_LOCATION = UrlUtils.concatURL(
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, DIR_NAME_STATIC, MATERIALS_BRAKE_TECHDOC_REL_PATH);
	////Internal directory locations END
	
	private static final String RESOURCE_FILENAME_REGEX = "(\\w+)(\\[(\\d+)\\])*";

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private AppConfigProvider appConfigProvider;

	private Map<String, List<String>> motorMechanicalImagePathsMap = new HashMap<>();
	private Map<String, List<String>> gearboxMechanicalImagePathsMap = new HashMap<>();
	private Map<String, List<String>> brakeMechanicalImagePathsMap = new HashMap<>();
	
	private Map<String, List<String>> motor3DDrawingPathsMap = new HashMap<>();
	private Map<String, List<String>> gearbox3DDrawingPathsMap = new HashMap<>();
	private Map<String, List<String>> brake3DDrawingPathsMap = new HashMap<>();
	
	private Map<String, List<String>> motorTechDocPathsMap = new HashMap<>();
	private Map<String, List<String>> gearboxTechDocPathsMap = new HashMap<>();
	private Map<String, List<String>> brakeTechDocPathsMap = new HashMap<>();

	@PostConstruct
	protected void initialize() {
		String resFileMatch = "*.*";
		
		this.motorMechanicalImagePathsMap = initResourcePathMap(
				UrlUtils.concatURL(MATERIALS_MOTOR_MECHANICAL_FULL_LOCATION, resFileMatch),
				MATERIALS_MOTOR_MECHANICAL_REL_PATH);
		this.gearboxMechanicalImagePathsMap = initResourcePathMap(
				UrlUtils.concatURL(MATERIALS_GEARBOX_MECHANICAL_FULL_LOCATION, resFileMatch),
				MATERIALS_GEARBOX_MECHANICAL_REL_PATH);
		this.brakeMechanicalImagePathsMap = initResourcePathMap(
				UrlUtils.concatURL(MATERIALS_BRAKE_MECHANICAL_FULL_LOCATION, resFileMatch),
				MATERIALS_BRAKE_MECHANICAL_REL_PATH);
		
		this.motor3DDrawingPathsMap = initResourcePathMap(
				UrlUtils.concatURL(MATERIALS_MOTOR_3D_FULL_LOCATION, resFileMatch),
				MATERIALS_MOTOR_3D_REL_PATH);
		this.gearbox3DDrawingPathsMap = initResourcePathMap(
				UrlUtils.concatURL(MATERIALS_GEARBOX_3D_FULL_LOCATION, resFileMatch),
				MATERIALS_GEARBOX_3D_REL_PATH);
		this.brake3DDrawingPathsMap = initResourcePathMap(
				UrlUtils.concatURL(MATERIALS_BRAKE_3D_FULL_LOCATION, resFileMatch),
				MATERIALS_BRAKE_3D_REL_PATH);
		
		this.motorTechDocPathsMap = initResourcePathMap(
				UrlUtils.concatURL(MATERIALS_MOTOR_TECHDOC_FULL_LOCATION, resFileMatch),
				MATERIALS_MOTOR_TECHDOC_REL_PATH);
		this.gearboxTechDocPathsMap = initResourcePathMap(
				UrlUtils.concatURL(MATERIALS_GEARBOX_TECHDOC_FULL_LOCATION, resFileMatch),
				MATERIALS_GEARBOX_TECHDOC_REL_PATH);
		this.brakeTechDocPathsMap = initResourcePathMap(
				UrlUtils.concatURL(MATERIALS_BRAKE_TECHDOC_FULL_LOCATION, resFileMatch),
				MATERIALS_BRAKE_TECHDOC_REL_PATH);
	}
	
	private Map<String, List<String>> initResourcePathMap(String resourceFullLocation, String resourceRelPath) {
		Map<String, List<String>> resourceMap = new HashMap<>();
		Resource[] resources = null;
		try {
			resources = ResourcePatternUtils.getResourcePatternResolver(applicationContext).getResources(resourceFullLocation);
			if (resources == null || resources.length == 0) {
				log.info("No resource loaded from {} ", resourceFullLocation);
			} else {
				log.info("Resources are loaded from {} successfully. ", resourceFullLocation);
			}
		} catch (IOException ex) {
			log.error("Failed to load resources from {} ", resourceFullLocation, ex);
		}
		if (resources == null || resources.length == 0) {
			return resourceMap;
		}
		BiConsumer<String, String> putResourceCallback = (resName, filename) -> {
			List<String> resourcePaths = resourceMap.get(resName);
			if (resourcePaths == null) {
				resourcePaths = new ArrayList<>();
				resourceMap.put(resName, resourcePaths);
			}
			resourcePaths.add(UrlUtils.concatURL(resourceRelPath, filename));
		};
		if (resources.length > 1) {
			Arrays.sort(resources, (r1, r2) -> {
				return r1.getFilename().compareTo(r2.getFilename());
			});
		}
		for (Resource res : resources) {
			String filename = res.getFilename();
			String resName = filename;
			int dotInx = filename.lastIndexOf('.');
			if (dotInx > 0) {
				resName = filename.substring(0, dotInx);
				Pattern p = Pattern.compile(RESOURCE_FILENAME_REGEX);
				Matcher m = p.matcher(resName);
				if (m.matches()) {
					resName = m.group(1);
				}
			}
			putResourceCallback.accept(resName, filename);
		}
		log.info("Resources are initialized successfully for parent path {} with size {} ", resourceRelPath, resourceMap.size());
		return resourceMap;
	}
	
	private List<String> getResourcePaths(String model, String baseDirName, String productDirName, String resSubCatDirName, 
			Map<String, List<String>> defaultResMap, String series) {

		List<String> resourcePaths = getResourcePaths(model, baseDirName, productDirName, resSubCatDirName, defaultResMap);
		if (ObjectUtils.isEmpty(resourcePaths) && series != null) {
			resourcePaths = getResourcePaths(series, baseDirName, productDirName, resSubCatDirName, defaultResMap);
		}
		return resourcePaths;
	}

	private List<String> getResourcePaths(String modelOrSeries, String baseDirName, String productDirName, String resSubCatDirName, 
			Map<String, List<String>> defaultResMap) {
		
		if (Boolean.FALSE.equals(appConfigProvider.getResourceReadIndividualFolderEnabled())) {
			return defaultResMap.get(modelOrSeries);
		}
		String relResPath = UrlUtils.concatURL("/", baseDirName, productDirName, modelOrSeries, resSubCatDirName);
		Resource[] resources = readIndividualResources(relResPath, modelOrSeries);
		if (resources == null || resources.length == 0) {
			return defaultResMap.get(modelOrSeries);
		}
		List<String> resPaths = new ArrayList<>();
		if (resources.length > 1) {
			Arrays.sort(resources, (r1, r2) -> {
				return r1.getFilename().compareTo(r2.getFilename());
			});
		}
		for (Resource res : resources) {
			resPaths.add(UrlUtils.concatURL(relResPath, res.getFilename()));
		}
		return resPaths;
	}

	private Resource[] readIndividualResources(String relModelResPath, String modelOrSeries) {
		Resource[] resources = null;
		String resFileMatch = "*.*";
		String resLocation = UrlUtils.concatURL(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, DIR_NAME_STATIC, relModelResPath, resFileMatch);
		try {
			resources = ResourcePatternUtils.getResourcePatternResolver(applicationContext).getResources(resLocation);
			if (resources == null || resources.length == 0) {
				log.warn("No resource loaded from {} for product {}. ", resLocation, modelOrSeries);
			} else {
				log.info("Resources are loaded from {} successfully for product {}. ", resLocation, modelOrSeries);
			}
		} catch (IOException ex) {
			log.warn("Failed to load resource from {} for product {}. ", resLocation, modelOrSeries, ex);
		}
		return resources;
	}
	
	@Override
	public List<String> getMotorMechanicalImagePaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_MOTOR, DIR_NAME_MICHANICAL, this.motorMechanicalImagePathsMap, series);
	}

	@Override
	public List<String> getGearboxMechanicalImagePaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_GEARBOX, DIR_NAME_MICHANICAL, this.gearboxMechanicalImagePathsMap, series);
	}

	@Override
	public List<String> getBrakeMechanicalImagePaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_BRAKE, DIR_NAME_MICHANICAL, this.brakeMechanicalImagePathsMap, series);
	}
	
	@Override
	public List<String> getMotor3DDrawingPaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_MOTOR, DIR_NAME_3D, this.motor3DDrawingPathsMap, series);
	}

	@Override
	public List<String> getGearbox3DDrawingPaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_GEARBOX, DIR_NAME_3D, this.gearbox3DDrawingPathsMap, series);
	}
	
	@Override
	public List<String> getBrake3DDrawingPaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_BRAKE, DIR_NAME_3D, this.brake3DDrawingPathsMap, series);
	}

	@Override
	public List<String> getMotorTechDocPaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_MOTOR, DIR_NAME_TECHDOC, this.motorTechDocPathsMap, series);
	}
	
	@Override
	public List<String> getGearboxTechDocPaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_GEARBOX, DIR_NAME_TECHDOC, this.gearboxTechDocPathsMap, series);
	}
	
	@Override
	public List<String> getBrakeTechDocPaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_BRAKE, DIR_NAME_TECHDOC, this.brakeTechDocPathsMap, series);
	}
	
	@Override
	public List<ResourceInfo> getTechDocInfos() {
		String techDocPathPattern = UrlUtils.concatURL(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, 
				DIR_NAME_STATIC, DIR_NAME_MATERIALS, "**", DIR_NAME_TECHDOC, "*.pdf");
		Resource[] resources = null;
		try {
			resources = ResourcePatternUtils.getResourcePatternResolver(applicationContext).getResources(techDocPathPattern);
		} catch (IOException ex) {
			log.error("Failed to load techdoc resources from {} ", DIR_NAME_STATIC, ex);
		}
		if (resources == null || resources.length == 0) {
			log.info("No techdoc resources loaded from {} ", DIR_NAME_STATIC);
			return null;
		}
		log.info("Loaded all techdocs. ");
		return Arrays.stream(resources)
				.map(r -> createResourceInfo(r, DIR_NAME_STATIC, DIR_NAME_MATERIALS))
				.collect(Collectors.toList());
    }
	
	private ResourceInfo createResourceInfo(Resource res, String contextDirName, String baseDirName) {
		ResourceInfo resInfo = new ResourceInfo();
		resInfo.setName(res.getFilename());
		String fullPath = null;
		try {
			fullPath = res.getURL().getPath();
		} catch (IOException e) {
			log.warn("Failed to get URL from file resource {} ", res.getFilename());
		}
		if (fullPath != null) {
			String basePath = contextDirName + "/" + baseDirName;
			resInfo.setPath(fullPath.substring(fullPath.indexOf(basePath) + contextDirName.length()));
		}
		try {
			resInfo.setSize(res.contentLength());
		} catch (IOException e) {
			log.warn("Failed to get contentLength from file resource {} ", res.getFilename());
		}
		return resInfo;
	}
}

