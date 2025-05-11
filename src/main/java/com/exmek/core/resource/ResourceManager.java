package com.exmek.core.resource;

import java.util.List;

public interface ResourceManager {

	String DIR_NAME_IMAGES				= "images";

	String DIR_NAME_MATERIALS			= "materials";
	
	String DIR_NAME_MOTOR					= "motor";
	String DIR_NAME_GEARBOX					= "gearbox";
	String DIR_NAME_BRAKE					= "brake";

	String DIR_NAME_MICHANICAL					= "mechanical";
	String DIR_NAME_3D							= "3d";
	String DIR_NAME_TECHDOC						= "techdoc";

	List<String> getMotorMechanicalImagePaths(String model, String series);

	List<String> getGearboxMechanicalImagePaths(String model, String series);

	List<String> getBrakeMechanicalImagePaths(String model, String series);
	

	List<String> getMotor3DDrawingPaths(String model, String series);
	
	List<String> getGearbox3DDrawingPaths(String model, String series);
	
	List<String> getBrake3DDrawingPaths(String model, String series);


	List<String> getMotorTechDocPaths(String model, String series);
	
	List<String> getGearboxTechDocPaths(String model, String series);
	
	List<String> getBrakeTechDocPaths(String model, String series);

	List<ResourceInfo> getTechDocInfos();
}
