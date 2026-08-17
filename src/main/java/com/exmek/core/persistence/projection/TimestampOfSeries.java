package com.exmek.core.persistence.projection;

import java.util.Date;

public interface TimestampOfSeries {

	String getSeries();

	Date getTimestamp();
}