package com.exmek.core.external;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.config.ExternalLookupCountryConf;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CountryLookupService {
	
	@Autowired
	private AppConfigProvider appConfigProvider;

	@Autowired
	private RestTemplate restTemplate;

	public String getCountryOrRegionCodeByIP(String ip) {
		// Example of public API to lookup country by IP (both support IPv4 and IPv6):
		// 		http://ip-api.com/json/156.93.246.30
		// 		https://api.country.is/2001:0000:130F:0000:0000:09C0:876A:130B
		ExternalLookupCountryConf lookupCountryConf = appConfigProvider.getExternalLookupCountryConf();
		String url = lookupCountryConf.getBaseEndpoint();
		if (!url.endsWith("/")) {
			url = url + "/";
		}
		url = url + ip;
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> responseEntity = null;
		try {
			responseEntity = restTemplate.getForEntity(url, Map.class);
		} catch (Exception ex) {
			log.error("Failed to call {} to get country by ip address ", url, ex);
			return null;
		}
		if (responseEntity == null) {
			log.warn("No response from the call to {} to get country by ip address ", url);
			return null;
		}
		@SuppressWarnings("unchecked")
		Map<String, String> response = responseEntity.getBody();
		if (response == null) {
			log.warn("No response body from the call to {} to get country by ip address ", url);
			return null;
		}
		String country;
		try {
			country = (String) PropertyUtils.getProperty(response, lookupCountryConf.getCountryPropertyName());
		} catch (Exception e) {
			log.error("Failed to extract 'country' from the response from the call to {} with property name {} ",
					url, lookupCountryConf.getCountryPropertyName());
			return null;
		}
		return country;
	}
}
