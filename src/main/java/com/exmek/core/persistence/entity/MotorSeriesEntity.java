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
@Table(name = "MOTOR_SERIES")
@Access(AccessType.FIELD)
public class MotorSeriesEntity extends AbstractSeriesEntity {

	@Column(name = "CATEGORY")
	@Enumerated(EnumType.STRING)
	private MotorCategory.Category category;

	public MotorCategory.Category getCategory() {
		return category;
	}

	public void setCategory(MotorCategory.Category category) {
		this.category = category;
	}

}
