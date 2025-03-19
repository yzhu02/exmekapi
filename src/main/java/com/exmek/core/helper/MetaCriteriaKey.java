package com.exmek.core.helper;

import com.exmek.core.model.MotorCategory;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class MetaCriteriaKey {

	private MotorCategory.Type type;
	
	private String category;
	
	private String series;
	
}
