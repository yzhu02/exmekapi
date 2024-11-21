package com.exmek.core.config;

import lombok.Data;

@Data
public class ExternalLookupCountryConf {

	private String baseEndpoint;
	
	private String countryPropertyName;
}
