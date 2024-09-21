package com.exmek.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.exmek.core.model.Brake;
import com.exmek.core.model.BrakeSeries;
import com.exmek.core.model.Company;
import com.exmek.core.model.DCMotor;
import com.exmek.core.model.GearboxSeries;
import com.exmek.core.model.MotorCategory;
import com.exmek.core.model.MotorSeries;
import com.exmek.core.model.News;
import com.exmek.core.model.PlanetaryGearbox;
import com.exmek.core.model.StepperMotor;
import com.exmek.core.rest.BasicInfoRestController;
import com.exmek.core.rest.BrakeRestController;
import com.exmek.core.rest.DCMotorRestController;
import com.exmek.core.rest.PageableListDataResponse;
import com.exmek.core.rest.PlanetaryGearboxRestController;
import com.exmek.core.rest.SearchMetaCriteriaResponse;
import com.exmek.core.rest.StepperMotorRestController;

/**
 * "motors_landing" view will be responsible for rendering following pages driven by page models:
 * 
	 * (1) Overall DC motors page
	 * (2) DC Motor type pages:
	 * 		BLDC motors page
	 * 		Brush motors page
	 * (3) DC Motor category pages:
	 * 		Internal Rotor BLDC Motors
	 * 		External Rotor BLDC Motors
	 * 		Frameless BLDC Motors
	 * 		Coreless BLDC Motors
	 * 		BLDC Servo Motors
	 * 		BLDC Motor with Gearbox
	 * 		Direct-drive Brushless Motors
	 * 
	 *		Permanent Magnet Brush Motors
	 *		Brush Motor with Gearbox
	 *
	 *		Integrated Motors
	 *
	 *		Solar Tracking Application
	 *		Material Handling Solution
	 * (5) DC Motor series page
	 * (6) Overall Stepper motors page
	 * (7) Stepper Motor category pages:
	 * 		Standard Torque Stepper Motors
	 * 		Flat Stepper Motors
	 * 		Stepper Motor with Control
	 * 		Linear Stepper Motors 
	 * (8) Stepper motor series page
 * 
 */
@Controller
public class WebController {

	public static final String QRY_PARAM_NAME_TYPE					= "type";
	
	
	public static final String PATH_PARAM_CATEGORY					= "category";
	
	public static final String PATH_PARAM_SERIES					= "series";
	
	public static final String PATH_PARAM_MODEL						= "model";
	
	public static final String PATH_PARAM_ID_OR_NAME				= "idOrName";
	

	public static final String VIEW_NAME_HOME						= "home";
	
	public static final String VIEW_NAME_MOTORS_LANDING				= "motors_landing";

	public static final String VIEW_NAME_MOTOR_DETAIL				= "motor_detail";
	
	public static final String VIEW_NAME_PLANETARY_GEARBOXES_LANDING= "planetary_gearboxes_landing";
	
	public static final String VIEW_NAME_PLANETARY_GEARBOX_DETAIL	= "planetary_gearbox_detail";
	
	public static final String VIEW_NAME_BRAKES_LANDING				= "brakes_landing";
		
	public static final String VIEW_NAME_BRAKE_DETAIL				= "brake_detail";
	
	public static final String VIEW_NAME_NEWS_LANDING				= "news_landing";
	
	public static final String VIEW_NAME_NEWS_DETAIL				= "news_detail";
	
	
	public static final String PAGEMODEL_NAME_EXMEK_COMPANY			= "exmekCompany";
	
	public static final String PAGEMODEL_NAME_TYPE					= "type";

	public static final String PAGEMODEL_NAME_SEARCH_META_CRITERIA	= "searchMetaCriteria";
	
	public static final String PAGEMODEL_NAME_CATEGORIES			= "categories";
	
	public static final String PAGEMODEL_NAME_CATEGORY				= "category";
	
	public static final String PAGEMODEL_NAME_SERIESES				= "serieses";
	
	public static final String PAGEMODEL_NAME_SERIES				= "series";
	
	public static final String PAGEMODEL_NAME_MOTOR					= "motor";
	
	public static final String PAGEMODEL_NAME_PLANETARY_GEARBOX		= "planetaryGearbox";
	
	public static final String PAGEMODEL_NAME_BRAKE					= "brake";
	
	public static final String PAGEMODEL_NAME_NEWS_LIST				= "newsList";
	
	public static final String PAGEMODEL_NAME_NEWS					= "news";


	//For now the Web layer and API layer are located in same deployment instance, hence for now it's local dependency.
	//It's designed in the way to be able to have Web layer and API in separate layers, hence in future it may make remote REST call from Web layer to API layer.
	@Autowired
	private DCMotorRestController dcMotorRestController;
	
	@Autowired
	private StepperMotorRestController stepperMotorRestController;

	@Autowired
	private PlanetaryGearboxRestController planetaryGearboxRestController;

	@Autowired
	private BrakeRestController brakeRestController;
	
	@Autowired
	private BasicInfoRestController basicInfoRestController;

	////////// Home Page //////////
	@GetMapping("/home")
	public String landHome(Model model) {
		Company exmekCompany = basicInfoRestController.getExmekCompany();
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		return VIEW_NAME_HOME;
	}
	////////// Home Page //////////
	
	
	////////// DC Motor Page //////////
	/**
	 * 
	 * @param type
	 * @param model
	 * @return
	 */
	@GetMapping("/products/dc_motors")
	public String landDCMotorsByType(@RequestParam(name = QRY_PARAM_NAME_TYPE, required = false) String type, Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		SearchMetaCriteriaResponse searchMetaCriteria = dcMotorRestController.getSearchMetaCriteria(type);
		List<MotorCategory> motorCategories = dcMotorRestController.getMotorCategories(type);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_TYPE, type);
		model.addAttribute(PAGEMODEL_NAME_CATEGORIES, motorCategories);
		model.addAttribute(PAGEMODEL_NAME_SEARCH_META_CRITERIA, searchMetaCriteria);
		return VIEW_NAME_MOTORS_LANDING;
	}

	@GetMapping("/products/dc_motors/categories/{" + PATH_PARAM_CATEGORY + "}")
	public String landDCMotorsByCategory(@PathVariable(PATH_PARAM_CATEGORY) MotorCategory.Category category, Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		SearchMetaCriteriaResponse searchMetaCriteria = dcMotorRestController.getSearchMetaCriteriaByCategory(category);
		MotorCategory motorCategory = dcMotorRestController.getMotorCategory(category);
		PageableListDataResponse<MotorSeries> motorSeriesesPage = dcMotorRestController.getMotorSeriesesByCategory(category, null, null);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_CATEGORY, motorCategory);
		model.addAttribute(PAGEMODEL_NAME_SERIESES, motorSeriesesPage.getData());
		model.addAttribute(PAGEMODEL_NAME_SEARCH_META_CRITERIA, searchMetaCriteria);
		return VIEW_NAME_MOTORS_LANDING;
	}
	
	@GetMapping("/products/dc_motors/serieses/{" + PATH_PARAM_SERIES + "}")
	public String landDCMotorsBySeries(@PathVariable(PATH_PARAM_SERIES) String series, Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		MotorSeries motorSeries = dcMotorRestController.getMotorSeries(series);
		SearchMetaCriteriaResponse searchMetaCriteria = dcMotorRestController.getSearchMetaCriteriaByCategoryBySeries(motorSeries.getCategory(), series);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_SERIES, motorSeries);
		model.addAttribute(PAGEMODEL_NAME_SEARCH_META_CRITERIA, searchMetaCriteria);
		return VIEW_NAME_MOTORS_LANDING;
	}

	@GetMapping("/products/dc_motors/{" + PATH_PARAM_MODEL + "}")
	public String landDCMotorDetail(@PathVariable(PATH_PARAM_MODEL) String motorModel, Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		DCMotor motor = dcMotorRestController.getByModel(motorModel);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_MOTOR, motor);
		return VIEW_NAME_MOTOR_DETAIL;
	}
	//////////DC Motor Page //////////

	
	
	//////////Stepper Motor Page //////////
	@GetMapping("/products/stepper_motors")
	public String landStepperMotorsByType(Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		SearchMetaCriteriaResponse searchMetaCriteria = stepperMotorRestController.getSearchMetaCriteria();
		List<MotorCategory> motorCategories = stepperMotorRestController.getMotorCategories();
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_CATEGORIES, motorCategories);
		model.addAttribute(PAGEMODEL_NAME_SEARCH_META_CRITERIA, searchMetaCriteria);
		return VIEW_NAME_MOTORS_LANDING;
	}

	@GetMapping("/products/stepper_motors/categories/{" + PATH_PARAM_CATEGORY + "}")
	public String landStepperMotorsByCategory(@PathVariable(PATH_PARAM_CATEGORY) MotorCategory.Category category, Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		SearchMetaCriteriaResponse searchMetaCriteria = stepperMotorRestController.getSearchMetaCriteriaByCategory(category);
		MotorCategory motorCategory = stepperMotorRestController.getMotorCategory(category);
		PageableListDataResponse<MotorSeries> motorSeriesesPage = stepperMotorRestController.getMotorSeriesesByCategory(category, null, null);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_CATEGORY, motorCategory);
		model.addAttribute(PAGEMODEL_NAME_SERIESES, motorSeriesesPage.getData());
		model.addAttribute(PAGEMODEL_NAME_SEARCH_META_CRITERIA, searchMetaCriteria);
		return VIEW_NAME_MOTORS_LANDING;
	}
	
	@GetMapping("/products/stepper_motors/serieses/{" + PATH_PARAM_SERIES + "}")
	public String landStepperMotorsBySeries(@PathVariable(PATH_PARAM_SERIES) String series, Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		MotorSeries motorSeries = stepperMotorRestController.getMotorSeries(series);
		SearchMetaCriteriaResponse searchMetaCriteria = stepperMotorRestController.getSearchMetaCriteriaByCategoryBySeries(motorSeries.getCategory(), series);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_SERIES, motorSeries);
		model.addAttribute(PAGEMODEL_NAME_SEARCH_META_CRITERIA, searchMetaCriteria);
		return VIEW_NAME_MOTORS_LANDING;
	}

	@GetMapping("/products/stepper_motors/{" + PATH_PARAM_MODEL + "}")
	public String landStepperMotorDetail(@PathVariable(PATH_PARAM_MODEL) String motorModel, Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		StepperMotor motor = stepperMotorRestController.getByModel(motorModel);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_MOTOR, motor);
		return VIEW_NAME_MOTOR_DETAIL;
	}
	//////////Stepper Motor Page //////////


	//////////Planetary Gearbox Page //////////
	@GetMapping("/products/planetary_gearboxes")
	public String landPlanetaryGearboxes(Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		SearchMetaCriteriaResponse searchMetaCriteria = planetaryGearboxRestController.getSearchMetaCriteria();
		PageableListDataResponse<GearboxSeries> gearboxSeriesesPage = planetaryGearboxRestController.getGearboxSerieses(null, null);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_SERIESES, gearboxSeriesesPage.getData());
		model.addAttribute(PAGEMODEL_NAME_SEARCH_META_CRITERIA, searchMetaCriteria);
		return VIEW_NAME_PLANETARY_GEARBOXES_LANDING;
	}
	
	@GetMapping("/products/planetary_gearboxes/serieses/{" + PATH_PARAM_SERIES + "}")
	public String landPlanetaryGearboxesBySeries(@PathVariable(PATH_PARAM_SERIES) String series, Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		GearboxSeries gearboxSeries = planetaryGearboxRestController.getGearboxSeries(series);
		SearchMetaCriteriaResponse searchMetaCriteria = planetaryGearboxRestController.getSearchMetaCriteriaBySeries(series);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_SERIES, gearboxSeries);
		model.addAttribute(PAGEMODEL_NAME_SEARCH_META_CRITERIA, searchMetaCriteria);
		return VIEW_NAME_PLANETARY_GEARBOXES_LANDING;
	}

	@GetMapping("/products/planetary_gearboxes/{" + PATH_PARAM_MODEL + "}")
	public String landPlanetaryGearboxDetail(@PathVariable(PATH_PARAM_MODEL) String gearboxModel, Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		PlanetaryGearbox planetaryGearbox = planetaryGearboxRestController.getByModel(gearboxModel);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_PLANETARY_GEARBOX, planetaryGearbox);
		return VIEW_NAME_PLANETARY_GEARBOX_DETAIL;
	}
	//////////Planetary Gearbox Page //////////
	

	//////////Brake Page //////////
	@GetMapping("/products/brakes")
	public String landBrakes(Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		SearchMetaCriteriaResponse searchMetaCriteria = brakeRestController.getSearchMetaCriteria();
		PageableListDataResponse<BrakeSeries> brakeSeriesesPage = brakeRestController.getBrakeSerieses(null, null);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_SERIESES, brakeSeriesesPage.getData());
		model.addAttribute(PAGEMODEL_NAME_SEARCH_META_CRITERIA, searchMetaCriteria);
		return VIEW_NAME_BRAKES_LANDING;
	}
	
	@GetMapping("/products/brakes/serieses/{" + PATH_PARAM_SERIES + "}")
	public String landBrakesBySeries(@PathVariable(PATH_PARAM_SERIES) String series, Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		BrakeSeries brakeSeries = brakeRestController.getBrakeSeries(series);
		SearchMetaCriteriaResponse searchMetaCriteria = brakeRestController.getSearchMetaCriteriaBySeries(series);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_SERIES, brakeSeries);
		model.addAttribute(PAGEMODEL_NAME_SEARCH_META_CRITERIA, searchMetaCriteria);
		return VIEW_NAME_BRAKES_LANDING;
	}

	@GetMapping("/products/brakes/{" + PATH_PARAM_MODEL + "}")
	public String landBrakeDetail(@PathVariable(PATH_PARAM_MODEL) String brakeModel, Model model) {

		Company exmekCompany = basicInfoRestController.getExmekCompany();
		Brake brake = brakeRestController.getByModel(brakeModel);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_BRAKE, brake);
		return VIEW_NAME_BRAKE_DETAIL;
	}
	//////////Brake Page //////////

	
	////////// News Page //////////
	@GetMapping("/news")
	public String landNewsList(Model model) {
		Company exmekCompany = basicInfoRestController.getExmekCompany();
		List<News> newsList = basicInfoRestController.getAllNews();
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_NEWS_LIST, newsList);
		return VIEW_NAME_NEWS_LANDING;
	}
	
	@GetMapping("/news/{" + PATH_PARAM_ID_OR_NAME + "}")
	public String landNewsDetail(@PathVariable(PATH_PARAM_ID_OR_NAME) String idOrTitle, Model model) {
		Company exmekCompany = basicInfoRestController.getExmekCompany();
		News news = basicInfoRestController.getNews(idOrTitle);
		model.addAttribute(PAGEMODEL_NAME_EXMEK_COMPANY, exmekCompany);
		model.addAttribute(PAGEMODEL_NAME_NEWS, news);
		return VIEW_NAME_NEWS_DETAIL;
	}
	////////// News Page //////////
}
