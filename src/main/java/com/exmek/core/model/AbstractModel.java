package com.exmek.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public abstract class AbstractModel {

	private Long id;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Data
	@EqualsAndHashCode(callSuper = true)
	public static abstract class Namable extends AbstractModel {

		private String name;

	}
}
