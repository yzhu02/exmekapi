package com.exmek.core.persistence.projection;

import java.util.Date;

public interface LastUpdatedTimestampPerSeries {

	String getSeries();

	Date getLastUpdated();
}