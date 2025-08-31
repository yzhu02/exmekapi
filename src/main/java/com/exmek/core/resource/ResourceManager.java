package com.exmek.core.resource;

import java.util.List;
import java.util.Map;

public interface ResourceManager {

	String DIR_NAME_IMAGES				= "images";

	String DIR_NAME_MATERIALS			= "materials";
	
	String DIR_NAME_MOTOR					= "motor";
	String DIR_NAME_GEARBOX					= "gearbox";
	String DIR_NAME_BRAKE					= "brake";

	String DIR_NAME_MICHANICAL					= "mechanical";
	String DIR_NAME_3D							= "3d";
	String DIR_NAME_3D_VIEW						= "3d-view";
	String DIR_NAME_TECHDOC						= "techdoc";
	String DIR_NAME_ADDITIONAL_IMAGES			= "additional-images";

	List<String> getMotorMechanicalImagePaths(String model, String series);

	List<String> getGearboxMechanicalImagePaths(String model, String series);

	List<String> getBrakeMechanicalImagePaths(String model, String series);
	

	List<String> getMotor3DModelPaths(String model, String series);
	
	List<String> getGearbox3DModelPaths(String model, String series);
	
	List<String> getBrake3DModelPaths(String model, String series);

	
	Map<String, List<String>> getMotor3DViewPaths(String model, String series);

	Map<String, List<String>> getGearbox3DViewPaths(String model, String series);
	
	Map<String, List<String>> getBrake3DViewPaths(String model, String series);

	
	List<String> getMotorTechDocPaths(String model, String series);
	
	List<String> getGearboxTechDocPaths(String model, String series);
	
	List<String> getBrakeTechDocPaths(String model, String series);

	
	Map<String, List<String>> getMotorAdditionalImagePaths(String model, String series);
	
	Map<String, List<String>> getGearboxAdditionalImagePaths(String model, String series);
	
	Map<String, List<String>> getBrakeAdditionalImagePaths(String model, String series);

//	List<ResourceInfo> getTechDocInfos();
}
