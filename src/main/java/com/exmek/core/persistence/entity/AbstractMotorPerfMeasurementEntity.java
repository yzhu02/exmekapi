package com.exmek.core.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractMotorPerfMeasurementEntity extends AbstractEntity {
	
	@Column(name = "TITLE")
	private String title;

	@Column(name = "VALUES_TABLE")
	private String valuesTable;

	@Column(name = "CONDITIONS")
	private String conditions;
	
	@Column(name = "SAFE_THRESHOLD")
	private String safeThreshold;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValuesTable() {
		return valuesTable;
	}

	public void setValuesTable(String valuesTable) {
		this.valuesTable = valuesTable;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getSafeThreshold() {
		return safeThreshold;
	}

	public void setSafeThreshold(String safeThreshold) {
		this.safeThreshold = safeThreshold;
	}

}
