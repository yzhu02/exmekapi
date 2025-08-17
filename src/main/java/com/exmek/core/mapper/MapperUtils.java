package com.exmek.core.mapper;

import java.util.Date;

import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.persistence.entity.AbstractProductEntity;

public class MapperUtils {

	private MapperUtils() {
	}

	public static Boolean determineIsNew(AbstractProductEntity entity, AppConfigProvider appConfigProvider) {
		Date lastUpdatedTimestamp = entity.getUpdatedTimestamp();
		if (lastUpdatedTimestamp == null) {
			lastUpdatedTimestamp = entity.getCreatedTimestamp();
		}
		return determineIsNew(lastUpdatedTimestamp, appConfigProvider);
	}

	public static Boolean determineIsNew(Date lastUpdatedTimestamp, AppConfigProvider appConfigProvider) {
		if (lastUpdatedTimestamp == null) {
			return null;
		}
		Integer newProductAgingDays = appConfigProvider.getNewProductAgingDays();
		if (newProductAgingDays == null) {
			return null;
		}
		Date today = new Date();
		long diffInMs = today.getTime() - lastUpdatedTimestamp.getTime();
		return diffInMs / (1000 * 60 * 60 * 24) <= newProductAgingDays;
	}

}
