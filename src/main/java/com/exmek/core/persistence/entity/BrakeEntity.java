package com.exmek.core.persistence.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "BRAKE")
@Access(AccessType.FIELD)
public class BrakeEntity extends AbstractBrakeEntity {
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERIES", referencedColumnName = "SERIES", nullable = false, insertable = false, updatable = false)
    private BrakeSeriesEntity productSeries;
	
	public BrakeSeriesEntity getProductSeries() {
		return productSeries;
	}

	public void setProductSeries(BrakeSeriesEntity productSeries) {
		this.productSeries = productSeries;
	}
}
