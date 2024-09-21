package com.exmek.core.persistence.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "MOTOR_CONFIG")
@Access(AccessType.FIELD)
public class MotorConfigEntity extends AbstractManagableEntity {

	@Column(name = "MODEL_REF")
	private String modelRef;

	@Column(name = "CONFIG_NAME")
	private String configName;

	@Column(name = "CONFIG_VALUE")
	private String configValue;

	public String getModelRef() {
		return modelRef;
	}

	public void setModelRef(String modelRef) {
		this.modelRef = modelRef;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

}
