package com.exmek.core.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractSeriesEntity extends AbstractManagableEntity {

	public static final String FIELD_NAME_SERIES	= "series";

	@Column(name = "SERIES")
	private String series;

	@Column(name = "DISPLAY_NAME")
	private String displayName;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TECHNICAL_DATA")
	private String technicalData;

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTechnicalData() {
		return technicalData;
	}

	public void setTechnicalData(String technicalData) {
		this.technicalData = technicalData;
	}

}
