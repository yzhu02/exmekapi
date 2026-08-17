package com.exmek.core.mapper;

import java.util.Date;

import com.exmek.core.config.AppConfigProvider;
import com.exmek.core.persistence.entity.AbstractProductEntity;

public class MapperUtils {

	private MapperUtils() {
	}

	public static Boolean determineIsNew(Date timestamp, AppConfigProvider appConfigProvider) {
		if (timestamp == null) {
			return null;
		}
		Integer newProductAgingDays = appConfigProvider.getNewProductAgingDays();
		if (newProductAgingDays == null) {
			return null;
		}
		Date today = new Date();
		long diffInMs = today.getTime() - timestamp.getTime();
		return diffInMs / (1000 * 60 * 60 * 24) <= newProductAgingDays;
	}

}
