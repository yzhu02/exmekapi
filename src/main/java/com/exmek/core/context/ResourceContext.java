package com.exmek.core.context;

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
	
	public static final String DIR_NAME_STATIC						= "static";
	public static final String DIR_NAME_IMAGES						= "images";
	public static final String DIR_NAME_MOTOR						= "motor";
	public static final String DIR_NAME_GEARBOX						= "gearbox";
	public static final String DIR_NAME_BRAKE						= "brake";
	public static final String DIR_NAME_MICHANICAL					= "mechanical";
	
	private static final String IMAGES_PATH_PREFIX					= "/" + DIR_NAME_IMAGES + "/";
	
	public static final String IMAGES_MOTOR_MECHANICAL_REL_PATH		= IMAGES_PATH_PREFIX + DIR_NAME_MOTOR + "/" + DIR_NAME_MICHANICAL;
	public static final String IMAGES_GEARBOX_MECHANICAL_REL_PATH	= IMAGES_PATH_PREFIX + DIR_NAME_GEARBOX + "/" + DIR_NAME_MICHANICAL;
	public static final String IMAGES_BRAKE_MECHANICAL_REL_PATH		= IMAGES_PATH_PREFIX + DIR_NAME_BRAKE + "/" + DIR_NAME_MICHANICAL;

	private static final String IMAGES_MOTOR_MECHANICAL_FULL_LOCATION =
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + IMAGES_MOTOR_MECHANICAL_REL_PATH;

	private static final String IMAGES_GEARBOX_MECHANICAL_FULL_LOCATION =
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + IMAGES_GEARBOX_MECHANICAL_REL_PATH;
	
	private static final String IMAGES_BRAKE_MECHANICAL_FULL_LOCATION =
			ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + IMAGES_BRAKE_MECHANICAL_REL_PATH;
	
	private static final String IMAGE_FILENAME_REGEX = "(\\w+)(\\[(\\d+)\\])*";

	private static final Logger logger = LoggerFactory.getLogger(ResourceContext.class);

	@Autowired
	private ApplicationContext applicationContext;

	private Map<String, List<String>> motorMechanicalImagesMap = new HashMap<>();
	
	private Map<String, List<String>> gearboxMechanicalImagesMap = new HashMap<>();
	
	private Map<String, List<String>> brakeMechanicalImagesMap = new HashMap<>();

	@PostConstruct
	protected void initialize() {
		String imgFileMatch = "*.*";
		initImagesResource(this.motorMechanicalImagesMap, IMAGES_MOTOR_MECHANICAL_FULL_LOCATION + "/" + imgFileMatch, IMAGES_MOTOR_MECHANICAL_REL_PATH);
		initImagesResource(this.gearboxMechanicalImagesMap, IMAGES_GEARBOX_MECHANICAL_FULL_LOCATION + "/" + imgFileMatch, IMAGES_GEARBOX_MECHANICAL_REL_PATH);
		initImagesResource(this.brakeMechanicalImagesMap, IMAGES_BRAKE_MECHANICAL_FULL_LOCATION + "/" + imgFileMatch, IMAGES_BRAKE_MECHANICAL_REL_PATH);
	}

	private void initImagesResource(Map<String, List<String>> imagesMap, String imagesFullLocation, String imagesRelPath) {
		Resource[] resources = null;
		try {
			resources = ResourcePatternUtils.getResourcePatternResolver(applicationContext).getResources(imagesFullLocation);
			if (resources == null || resources.length == 0) {
				logger.info("No images loaded from {} ", imagesFullLocation);
			} else {
				logger.info("Images are loaded from {} successfully. ", imagesFullLocation);
			}
		} catch (IOException ex) {
			logger.error("Failed to load images from {} ", imagesFullLocation, ex);
		}
		if (resources == null || resources.length == 0) {
			return;
		}
		BiConsumer<String, String> putImageCallback = (imageName, filename) -> {
			List<String> imagePaths = imagesMap.get(imageName);
			if (imagePaths == null) {
				imagePaths = new ArrayList<>();
				imagesMap.put(imageName, imagePaths);
			}
			imagePaths.add(imagesRelPath + "/" + filename);
		};
		if (resources.length > 1) {
			Arrays.sort(resources, (r1, r2) -> {
				return r1.getFilename().compareTo(r2.getFilename());
			});
		}
		for (Resource res : resources) {
			String filename = res.getFilename();
			String imageName = filename;
			int dotInx = filename.lastIndexOf('.');
			if (dotInx > 0) {
				imageName = filename.substring(0, dotInx);
				Pattern p = Pattern.compile(IMAGE_FILENAME_REGEX);
				Matcher m = p.matcher(imageName);
				if (m.matches()) {
					imageName = m.group(1);
				}
			}
			putImageCallback.accept(imageName, filename);
		}
		logger.info("images are initialized successfully images path {} with size {} ", imagesRelPath, imagesMap.size());
	}
	
	private List<String> getMechanicalImagePaths(String model, String productDirName, Map<String, List<String>> defaultImagesMap) {
		String relModelImgPath = IMAGES_PATH_PREFIX + productDirName + "/" + model + "/" + DIR_NAME_MICHANICAL;
		String imgFileMatch = "*.*";
		String imgMechanicalLocation = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/" + DIR_NAME_STATIC + relModelImgPath + "/" + imgFileMatch;
		Resource[] resources = null;
		try {
			resources = ResourcePatternUtils.getResourcePatternResolver(applicationContext).getResources(imgMechanicalLocation);
			if (resources == null || resources.length == 0) {
				logger.info("No mechanical image loaded from {} for product {}. ", imgMechanicalLocation, model);
			} else {
				logger.info("Mechanical image are loaded from {} successfully for product {}. ", imgMechanicalLocation, model);
			}
		} catch (IOException ex) {
			logger.warn("Failed to load mechanical image from {} for product {}. ", imgMechanicalLocation, model, ex);
		}
		if (resources == null || resources.length == 0) {
			return defaultImagesMap.get(model);
		}
		List<String> imagePaths = new ArrayList<>();
		if (resources.length > 1) {
			Arrays.sort(resources, (r1, r2) -> {
				return r1.getFilename().compareTo(r2.getFilename());
			});
		}
		for (Resource res : resources) {
			imagePaths.add(relModelImgPath + "/" + res.getFilename());
		}
		return imagePaths;
	}

	public List<String> getMotorMechanicalImagePaths(String model) {
		return getMechanicalImagePaths(model, DIR_NAME_MOTOR, this.motorMechanicalImagesMap);
	}

	public List<String> getGearboxMechanicalImagePaths(String model) {
		return getMechanicalImagePaths(model, DIR_NAME_GEARBOX, this.gearboxMechanicalImagesMap);
	}

	public List<String> getBrakeMechanicalImagePaths(String model) {
		return getMechanicalImagePaths(model, DIR_NAME_BRAKE, this.brakeMechanicalImagesMap);
	}
}

