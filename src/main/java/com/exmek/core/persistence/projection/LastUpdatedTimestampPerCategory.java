package com.exmek.core.persistence.projection;

import java.util.Date;

public interface LastUpdatedTimestampPerCategory {

	String getCategory();

	Date getLastUpdated();
}