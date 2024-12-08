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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ResourceContext {
	
	////Directory names BEGIN
	private static final String DIR_NAME_STATIC			= "static";

	private static final String DIR_NAME_IMAGES				= "images";

	private static final String DIR_NAME_MOTOR					= "motor";
	private static final String DIR_NAME_GEARBOX					= "gearbox";
	private static final String DIR_NAME_BRAKE					= "brake";
	private static final String DIR_NAME_MICHANICAL					= "mechanical";

	private static final String DIR_NAME_MATERIALS			= "materials";
	private static final String DIR_NAME_3D							= "3d";
	private static final String DIR_NAME_TECHDOC					= "techdoc";
	
	public static final String IMAGES_PATH_PREFIX					= "/" + DIR_NAME_IMAGES + "/";
	public static final String MATERIALS_PATH_PREFIX				= "/" + DIR_NAME_MATERIALS + "/";
	////Directory names END
	
	////Exposed relative paths BEGIN
	public static final String IMAGES_MOTOR_MECHANICAL_REL_PATH		= IMAGES_PATH_PREFIX + DIR_NAME_MOTOR + "/" + DIR_NAME_MICHANICAL;
	public static final String IMAGES_GEARBOX_MECHANICAL_REL_PATH	= IMAGES_PATH_PREFIX + DIR_NAME_GEARBOX + "/" + DIR_NAME_MICHANICAL;
	public static final String IMAGES_BRAKE_MECHANICAL_REL_PATH		= IMAGES_PATH_PREFIX + DIR_NAME_BRAKE + "/" + DIR_NAME_MICHANICAL;

	public static final String MATERIALS_MOTOR_3D_REL_PATH			= MATERIALS_PATH_PREFIX + DIR_NAME_MOTOR + "/" + DIR_NAME_3D;
	public static final String MATERIALS_GEARBOX_3D_REL_PATH		= MATERIALS_PATH_PREFIX + DIR_NAME_GEARBOX + "/" + DIR_NAME_3D;
	public static final String MATERIALS_BRAKE_3D_REL_PATH			= MATERIALS_PATH_PREFIX + DIR_NAME_BRAKE + "/" + DIR_NAME_3D;
	
	public static final String MATERIALS_MOTOR_TECHDOC_REL_PATH		= MATERIALS_PATH_PREFIX + DIR_NAME_MOTOR + "/" + DIR_NAME_TECHDOC;
	public static final String MATERIALS_GEARBOX_TECHDOC_REL_PATH	= MATERIALS_PATH_PREFIX + DIR_NAME_GEARBOX + "/" + DIR_NAME_TECHDOC;
	public static final String MATERIALS_BRAKE_TECHDOC_REL_PATH		= MATERIALS_PATH_PREFIX + DIR_NAME_BRAKE + "/" + DIR_NAME_TECHDOC;
	////Exposed relative paths END
	
	////Internal directory locations BEGIN
	private static final String IMAGES_MOTOR_MECHANICAL_FULL_LOCATION =
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + IMAGES_MOTOR_MECHANICAL_REL_PATH;

	private static final String IMAGES_GEARBOX_MECHANICAL_FULL_LOCATION =
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + IMAGES_GEARBOX_MECHANICAL_REL_PATH;
	
	private static final String IMAGES_BRAKE_MECHANICAL_FULL_LOCATION =
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + IMAGES_BRAKE_MECHANICAL_REL_PATH;
	

	private static final String MATERIALS_MOTOR_3D_FULL_LOCATION =
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + MATERIALS_MOTOR_3D_REL_PATH;
	
	private static final String MATERIALS_GEARBOX_3D_FULL_LOCATION =
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + MATERIALS_GEARBOX_3D_REL_PATH;
	
	private static final String MATERIALS_BRAKE_3D_FULL_LOCATION =
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + MATERIALS_BRAKE_3D_REL_PATH;
	
	
	private static final String MATERIALS_MOTOR_TECHDOC_FULL_LOCATION =
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + MATERIALS_MOTOR_TECHDOC_REL_PATH;
	
	private static final String MATERIALS_GEARBOX_TECHDOC_FULL_LOCATION =
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + MATERIALS_GEARBOX_TECHDOC_REL_PATH;
	
	private static final String MATERIALS_BRAKE_TECHDOC_FULL_LOCATION =
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + MATERIALS_BRAKE_TECHDOC_REL_PATH;
	////Internal directory locations END
	
	private static final String IMAGE_FILENAME_REGEX = "(\\w+)(\\[(\\d+)\\])*";

	private static final Logger logger = LoggerFactory.getLogger(ResourceContext.class);

	@Autowired
	private ApplicationContext applicationContext;

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
		
		initResourcePathMap(this.motorMechanicalImagePathsMap,
				IMAGES_MOTOR_MECHANICAL_FULL_LOCATION + "/" + resFileMatch,
				IMAGES_MOTOR_MECHANICAL_REL_PATH);
		initResourcePathMap(this.gearboxMechanicalImagePathsMap,
				IMAGES_GEARBOX_MECHANICAL_FULL_LOCATION + "/" + resFileMatch,
				IMAGES_GEARBOX_MECHANICAL_REL_PATH);
		initResourcePathMap(this.brakeMechanicalImagePathsMap,
				IMAGES_BRAKE_MECHANICAL_FULL_LOCATION + "/" + resFileMatch,
				IMAGES_BRAKE_MECHANICAL_REL_PATH);
		
		initResourcePathMap(this.motor3DDrawingPathsMap,
				MATERIALS_MOTOR_3D_FULL_LOCATION + "/" + resFileMatch,
				MATERIALS_MOTOR_3D_REL_PATH);
		initResourcePathMap(this.gearbox3DDrawingPathsMap,
				MATERIALS_GEARBOX_3D_FULL_LOCATION + "/" + resFileMatch,
				MATERIALS_GEARBOX_3D_REL_PATH);
		initResourcePathMap(this.brake3DDrawingPathsMap,
				MATERIALS_BRAKE_3D_FULL_LOCATION + "/" + resFileMatch,
				MATERIALS_BRAKE_3D_REL_PATH);
		
		initResourcePathMap(this.motorTechDocPathsMap,
				MATERIALS_MOTOR_TECHDOC_FULL_LOCATION + "/" + resFileMatch,
				MATERIALS_MOTOR_TECHDOC_REL_PATH);
		initResourcePathMap(this.gearboxTechDocPathsMap,
				MATERIALS_GEARBOX_TECHDOC_FULL_LOCATION + "/" + resFileMatch,
				MATERIALS_GEARBOX_TECHDOC_REL_PATH);
		initResourcePathMap(this.brakeTechDocPathsMap,
				MATERIALS_BRAKE_TECHDOC_FULL_LOCATION + "/" + resFileMatch,
				MATERIALS_BRAKE_TECHDOC_REL_PATH);
	}

	private void initResourcePathMap(Map<String, List<String>> resourceMap, String resourceFullLocation, String resourceRelPath) {
		Resource[] resources = null;
		try {
			resources = ResourcePatternUtils.getResourcePatternResolver(applicationContext).getResources(resourceFullLocation);
			if (resources == null || resources.length == 0) {
				logger.info("No resource loaded from {} ", resourceFullLocation);
			} else {
				logger.info("Resources are loaded from {} successfully. ", resourceFullLocation);
			}
		} catch (IOException ex) {
			logger.error("Failed to load resources from {} ", resourceFullLocation, ex);
		}
		if (resources == null || resources.length == 0) {
			return;
		}
		BiConsumer<String, String> putResourceCallback = (resName, filename) -> {
			List<String> resourcePaths = resourceMap.get(resName);
			if (resourcePaths == null) {
				resourcePaths = new ArrayList<>();
				resourceMap.put(resName, resourcePaths);
			}
			resourcePaths.add(resourceRelPath + "/" + filename);
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
				Pattern p = Pattern.compile(IMAGE_FILENAME_REGEX);
				Matcher m = p.matcher(resName);
				if (m.matches()) {
					resName = m.group(1);
				}
			}
			putResourceCallback.accept(resName, filename);
		}
		logger.info("Resources are initialized successfully for parent path {} with size {} ", resourceRelPath, resourceMap.size());
	}
	
	private List<String> getResourcePaths(
			String model, String pathPrefix, String productDirName, String resourceDirName, Map<String, List<String>> defaultResourceMap) {
		String relModelResPath = pathPrefix + productDirName + "/" + model + "/" + resourceDirName;
		String resFileMatch = "*.*";
		String resLocation = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + relModelResPath + "/" + resFileMatch;
		Resource[] resources = null;
		try {
			resources = ResourcePatternUtils.getResourcePatternResolver(applicationContext).getResources(resLocation);
			if (resources == null || resources.length == 0) {
				logger.info("No resource loaded from {} for product {}. ", resLocation, model);
			} else {
				logger.info("Resources are loaded from {} successfully for product {}. ", resLocation, model);
			}
		} catch (IOException ex) {
			logger.warn("Failed to load resource from {} for product {}. ", resLocation, model, ex);
		}
		if (resources == null || resources.length == 0) {
			return defaultResourceMap.get(model);
		}
		List<String> resPaths = new ArrayList<>();
		if (resources.length > 1) {
			Arrays.sort(resources, (r1, r2) -> {
				return r1.getFilename().compareTo(r2.getFilename());
			});
		}
		for (Resource res : resources) {
			resPaths.add(relModelResPath + "/" + res.getFilename());
		}
		return resPaths;
	}

	public List<String> getMotorMechanicalImagePaths(String model) {
		return getResourcePaths(model, IMAGES_PATH_PREFIX, DIR_NAME_MOTOR, DIR_NAME_MICHANICAL, this.motorMechanicalImagePathsMap);
	}

	public List<String> getGearboxMechanicalImagePaths(String model) {
		return getResourcePaths(model, IMAGES_PATH_PREFIX, DIR_NAME_GEARBOX, DIR_NAME_MICHANICAL, this.gearboxMechanicalImagePathsMap);
	}

	public List<String> getBrakeMechanicalImagePaths(String model) {
		return getResourcePaths(model, IMAGES_PATH_PREFIX, DIR_NAME_BRAKE, DIR_NAME_MICHANICAL, this.brakeMechanicalImagePathsMap);
	}

	public List<String> getMotor3DDrawingPaths(String model) {
		return getResourcePaths(model, MATERIALS_PATH_PREFIX, DIR_NAME_MOTOR, DIR_NAME_3D, this.motor3DDrawingPathsMap);
	}
	
	public List<String> getGearbox3DDrawingPaths(String model) {
		return getResourcePaths(model, MATERIALS_PATH_PREFIX, DIR_NAME_GEARBOX, DIR_NAME_3D, this.gearbox3DDrawingPathsMap);
	}
	
	public List<String> getBrake3DDrawingPaths(String model) {
		return getResourcePaths(model, MATERIALS_PATH_PREFIX, DIR_NAME_BRAKE, DIR_NAME_3D, this.brake3DDrawingPathsMap);
	}

	public List<String> getMotorTechDocPaths(String model) {
		return getResourcePaths(model, MATERIALS_PATH_PREFIX, DIR_NAME_MOTOR, DIR_NAME_TECHDOC, this.motorTechDocPathsMap);
	}
	
	public List<String> getGearboxTechDocPaths(String model) {
		return getResourcePaths(model, MATERIALS_PATH_PREFIX, DIR_NAME_GEARBOX, DIR_NAME_TECHDOC, this.gearboxTechDocPathsMap);
	}
	
	public List<String> getBrakeTechDocPaths(String model) {
		return getResourcePaths(model, MATERIALS_PATH_PREFIX, DIR_NAME_BRAKE, DIR_NAME_TECHDOC, this.brakeTechDocPathsMap);
	}
}

