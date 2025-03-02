package com.exmek.core.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exmek.commons.utils.JsonMapperUtils;
import com.exmek.core.model.Company;
import com.exmek.core.persistence.entity.ConfigEntity;
import com.exmek.core.persistence.repository.ConfigRepository;
import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.annotation.PostConstruct;

@Component
public class AppConfigProvider {

	private static final Logger logger = LoggerFactory.getLogger(AppConfigProvider.class);

	public static final String CONFIG_NAME_COMPANY_EXMEK								= "company.exmek";
	
	public static final String CONFIG_NAME_SMTP_EXMEKSYS								= "smtp.exmeksys";
	
	public static final String CONFIG_NAME_EMAIL_INQUIRY_RECEIVERS						= "email.inquiryReceivers";
	
	public static final String CONFIG_NAME_EXTERNAL_LOOKUP_COUNTRY_SERVICE				= "external.lookupCountryService";
	
	public static final String CONFIG_NAME_CONSUMERS									= "consumers";
	
	public static final String CONFIG_NAME_CORS_ALLOWED_ORIGINS							= "cors.allowedOrigins";
	
	public static final String CONFIG_NAME_SERVER_ALLOW_URL_ENCODE_SLASH				= "server.allowUrlEncodedSlash";
	
	public static final String CONFIG_NAME_SEARCH_DC_MOTOR_METACRITERIA_FIELDS			= "search.dcMotor.metaCriteria.fields";
	
	public static final String CONFIG_NAME_SEARCH_STEPPER_MOTOR_METACRITERIA_FIELDS		= "search.stepperMotor.metaCriteria.fields";
	
	public static final String CONFIG_NAME_SEARCH_PLANETARY_GEARBOX_METACRITERIA_FIELDS	= "search.planetaryGearbox.metaCriteria.fields";
	
	public static final String CONFIG_NAME_SEARCH_BRAKE_METACRITERIA_FIELDS				= "search.brake.metaCriteria.fields";
	

	private static final String DEFAULT_VALUE_SMTP_EXMEKSYS_							= "{\"host\": \"smtp.gmail.com\", \"port\": 587, \"user\": \"exmeksys@gmail.com\", \"password\": \"mzuhdzrzhyeostbe\", \"properties\": {\"mail.transport.protocol\": \"smtp\", \"mail.smtp.auth\": \"true\", \"mail.smtp.starttls.enable\": \"true\"}}";
	private static final String DEFAULT_VALUE_EXTERNAL_LOOKUP_COUNTRY_SERVICE			= "{\"baseEndpoint\": \"https://api.country.is/\", \"countryPropertyName\": \"country\"}";

	@Autowired
	private ConfigRepository configRepository;

	private Map<String, String> configMap = new HashMap<>();

	@PostConstruct
	protected void initialize() {
		List<ConfigEntity> configEntities = this.configRepository.findAll();
		this.configMap = configEntities.stream()
				.collect(Collectors.toMap(ConfigEntity::getName, ConfigEntity::getValue));
	}

	public String getConfigValue(String configName, String defaultValue) {
		if (configMap == null) {
			logger.error("Unable to get config for name '{}' as the 'configMap' is null. ", configName);
			return defaultValue;
		}
		String value = configMap.get(configName);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}
	
	public Company getExmekCompany() {
		String exmekStr = getConfigValue(CONFIG_NAME_COMPANY_EXMEK, null);
		return JsonMapperUtils.readValue(exmekStr, new TypeReference<Company>() {});
	}

	public SmtpConf getSmtpExmekSysConf() {
		String confStr = getConfigValue(CONFIG_NAME_SMTP_EXMEKSYS, DEFAULT_VALUE_SMTP_EXMEKSYS_);
		return JsonMapperUtils.readValue(confStr, new TypeReference<SmtpConf>() {});
	}

	public ReceiverEmailConf getInquiryReceiverEmailConf() {
		String confStr = getConfigValue(CONFIG_NAME_EMAIL_INQUIRY_RECEIVERS, null);
		return JsonMapperUtils.readValue(confStr, new TypeReference<ReceiverEmailConf>() {});
	}

	public ExternalLookupCountryConf getExternalLookupCountryConf() {
		String confStr = getConfigValue(CONFIG_NAME_EXTERNAL_LOOKUP_COUNTRY_SERVICE, DEFAULT_VALUE_EXTERNAL_LOOKUP_COUNTRY_SERVICE);
		return JsonMapperUtils.readValue(confStr, new TypeReference<ExternalLookupCountryConf>() {});
	}

	public List<Consumer> getConsumers() {
		String confStr = getConfigValue(CONFIG_NAME_CONSUMERS, null);
		return JsonMapperUtils.readValue(confStr, new TypeReference<List<Consumer>>() {});
	}
	
	public List<String> getCorsAllowedOrigins() {
		String confStr = getConfigValue(CONFIG_NAME_CORS_ALLOWED_ORIGINS, null);
		return JsonMapperUtils.readValue(confStr, new TypeReference<List<String>>() {});
	}

	public Boolean getAllowUrlEncodedSlash() {
		String confStr = getConfigValue(CONFIG_NAME_SERVER_ALLOW_URL_ENCODE_SLASH, null);
		return BooleanUtils.toBooleanObject(confStr);
	}
	
	public List<String> getSearchDCMotorMetaCriteriaFields() {
		String confStr = getConfigValue(CONFIG_NAME_SEARCH_DC_MOTOR_METACRITERIA_FIELDS, null);
		return JsonMapperUtils.readValue(confStr, new TypeReference<List<String>>() {});
	}
	
	public List<String> getSearchStepperMotorMetaCriteriaFields() {
		String confStr = getConfigValue(CONFIG_NAME_SEARCH_STEPPER_MOTOR_METACRITERIA_FIELDS, null);
		return JsonMapperUtils.readValue(confStr, new TypeReference<List<String>>() {});
	}
	
	public List<String> getSearchPlanetaryGearboxMetaCriteriaFields() {
		String confStr = getConfigValue(CONFIG_NAME_SEARCH_PLANETARY_GEARBOX_METACRITERIA_FIELDS, null);
		return JsonMapperUtils.readValue(confStr, new TypeReference<List<String>>() {});
	}
	
	public List<String> getSearchBrakeMetaCriteriaFields() {
		String confStr = getConfigValue(CONFIG_NAME_SEARCH_BRAKE_METACRITERIA_FIELDS, null);
		return JsonMapperUtils.readValue(confStr, new TypeReference<List<String>>() {});
	}
}
