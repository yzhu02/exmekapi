package com.exmek.core.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.exmek.commons.expr.RelationalOperator;
import com.exmek.core.commons.model.MeasuredOptionsValue;
import com.exmek.core.commons.model.Range;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldMetaCriterion {
	
	private String type;
	private String fieldName;
	private String displayName;
	private Boolean isNumber;
	private String unitFieldName;
	private Map<?, Range<? extends Number>> minMaxByUnits;

	private List<RelationalOperator> supportedOperators;
	
	private MeasuredOptionsValue<? super Object, ? super Object> availableOptionsValue;
	
	public void addSupportedOperator(RelationalOperator... operators) {
		if (operators == null) {
			return;
		}
		if (this.supportedOperators == null) {
			this.supportedOperators = new ArrayList<>();
		}
		for (RelationalOperator op : supportedOperators) {
			this.supportedOperators.add(op);
		}
	}

//	public <V> void addAvailableValueOption(V... options) {
//		if (options == null) {
//			return;
//		}
//		if (this.availableOptionsValue == null) {
//			this.availableOptionsValue = new MeasuredOptionsValue<Object, Object>();
//		}
//		if (this.availableOptionsValue.getOptions() == null) {
//			this.availableOptionsValue.setOptions(new ArrayList<>());
//		}
//		for (V op : options) {
//			this.availableOptionsValue.getOptions().add(op);
//		}
//	}
}
