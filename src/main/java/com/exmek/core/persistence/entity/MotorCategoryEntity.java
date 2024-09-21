package com.exmek.core.persistence.entity;

import com.exmek.core.model.MotorCategory;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "MOTOR_CATEGORY")
@Access(AccessType.FIELD)
public class MotorCategoryEntity extends AbstractManagableEntity {

	public static final String FIELD_NAME_CATEGORY	= "category";

	@Column(name = "CATEGORY")
	@Enumerated(EnumType.STRING)
	private MotorCategory.Category category;

	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TECHNICAL_DATA")
	private String technicalData;

	public MotorCategory.Category getCategory() {
		return category;
	}

	public void setCategory(MotorCategory.Category category) {
		this.category = category;
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
