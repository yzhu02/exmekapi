package com.exmek.core.commons.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Range<N extends Number> {

	private N min;

	private N max;
}
