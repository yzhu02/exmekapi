package com.exmek.core.persistence.entity;

import com.exmek.core.model.MotorCategory;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractMotorCategoryEntity extends AbstractManagableEntity {

	public static final String FIELD_NAME_CATEGORY	= "category";
	
	public static final String FIELD_NAME_TYPE		= "type";

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	private MotorCategory.Type type;
	
	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TECHNICAL_DATA")
	private String technicalData;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public MotorCategory.Type getType() {
		return type;
	}

	public void setType(MotorCategory.Type type) {
		this.type = type;
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
